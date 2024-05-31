package com.pilleasychat.project.web.chatbot.config;

import com.pilleasychat.project.web.chatbot.service.CustomerSupportAgent;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.testcontainers.chromadb.ChromaDBContainer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@Configuration
public class LangChain4jConfig {

    private final ChromaDBContainer chroma = new ChromaDBContainer("chromadb/chroma:0.4.22");
    @Value("${spring.api.api-key}")
    private String apiKey;

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
//        chroma.start();
        return ChromaEmbeddingStore.builder()
                .baseUrl("http://localhost:8000/")
                .collectionName("my_collection")
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-3-large")
                .dimensions(512)
                .tokenizer(openAiTokenizer())
                .build();
    }

    @Bean
    public OpenAiTokenizer openAiTokenizer() {
        return new OpenAiTokenizer("gpt-3.5-turbo");
    }

    public List<String> splitText(String text) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + 2000, text.length());
            String chunk = text.substring(start, end);
            String[] separators = new String[]{"\n\n", "\n", "."};

            for (String separator : separators) {
                int separatorIndex = chunk.lastIndexOf(separator);
                if (separatorIndex != -1) {
                    end = start + separatorIndex + separator.length();
                    chunk = text.substring(start, end);
                    break;
                }
            }

            chunks.add(chunk);
            start = end - 0; // Overlapping not required as per your given code
        }
        return chunks;
    }


    @Bean
    CommandLineRunner ingestChroma(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel,
            ResourceLoader resourceLoader
    ) {
        return (args) -> {
            var resource = resourceLoader.getResource("classpath:pdf/api_xmldata_preprocess1.pdf");
            var file = resource.getFile();
            String textContent = extractTextFromPDF(file);
            //System.out.println(document);
            List<String> documents = splitDocuments(textContent);
            for (String d : documents) {
                List<String> chunks = splitText(d);
                //System.out.println(chunks);
                //System.out.println("\n");
                TextSegment segmentChroma = TextSegment.from(chunks.getFirst());
                Embedding embeddingChroma = embeddingModel.embed(segmentChroma).content();
                embeddingStore.add(embeddingChroma, segmentChroma);
            }
            System.out.println(embeddingStore);
        };
    }

    private String extractTextFromPDF(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private static List<String> splitDocuments(String text) {
        List<String> documents = new ArrayList<>();
        String[] lines = text.split("\n");

        StringBuilder currentDocument = new StringBuilder();

        for (String line : lines) {
            // "회사명은"으로 시작하는 문서 시작
            if (line.trim().startsWith("회사명은")) {
                if (currentDocument.length() > 0) {
                    documents.add(currentDocument.toString().trim());
                    currentDocument.setLength(0);
                }
            }
            currentDocument.append(line).append("\n");
        }

        // 마지막 문서 추가
        if (currentDocument.length() > 0) {
            documents.add(currentDocument.toString().trim());
        }

        return documents;
    }

    @Bean
    public ContentRetriever contentRetriever(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel
    ) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(1)
                .minScore(0.7)
                .build();
    }
    /*
    @Bean
    public ConversationalRetrievalChain conversationalRetrievalChain(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel
    ) {

        return ConversationalRetrievalChain.builder()
                .chatLanguageModel(chatLanguageModel)
                .contentRetriever(contentRetriever(embeddingStore, embeddingModel))
                .build();
    }

    @Bean
    public CustomerSupportAgent customerSupportAgent(
            ChatLanguageModel chatLanguageModel,
            OpenAiTokenizer openAiTokenizer,
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore
    ) {
        return AiServices.builder(CustomerSupportAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemoryProvider(chatId -> TokenWindowChatMemory.builder()
                        .id(chatId)
                        .maxTokens(500, openAiTokenizer)
                        .build())
                .contentRetriever(contentRetriever(embeddingStore, embeddingModel))
                .build();
    }


    @Bean
    CommandLineRunner docToEmbedding(
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore,
            OpenAiTokenizer openAiTokenizer,
            ResourceLoader loader
    ) {
        return args -> {
            var resource = loader.getResource("classpath:/pdf/api_xmldata_preprocess1.pdf");
            var doc = loadDocument(resource.getFile().toPath(), new TextDocumentParser());
            var splitter = DocumentSplitters.recursive(1000, 0, openAiTokenizer);
            var ingestor = EmbeddingStoreIngestor.builder()
                    .embeddingStore(embeddingStore)
                    .embeddingModel(embeddingModel)
                    .documentSplitter(splitter)
                    .build();

            ingestor.ingest(doc);
        };
    }
    */
}
