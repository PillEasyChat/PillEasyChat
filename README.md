# 💊 PillEasyChat (필이지챗)

> 처방 오류를 줄이기 위해 의약품을 쉽게 설명해주는 AI 챗봇 서비스

2022 환자안전보고학습시스템에 따르면 약물 사고 중 처방오류 비율이 가장 높으며, 그 중 용법용량 오류가 가장 많은 비율을 차지합니다.
PillEasyChat은 어려운 의학용어로 인해 의약품 정보를 이해하기 힘든 일반인들에게 **개인 맞춤형 의약품 정보**를 쉽게 제공하는 챗봇 서비스입니다.

<br>

## 📌 목차

- [프로젝트 소개](#-프로젝트-소개)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [핵심 구현 - RAG 프로세스](#-핵심-구현---rag-프로세스)
- [팀원 및 역할](#-팀원-및-역할)

<br>

## 🧩 프로젝트 소개

### 개발 배경

| 기존 서비스 | 한계점 |
|---|---|
| 의약품 안전사용 서비스(DUR) | 의사/약사 전용, 일반인 사용 불가 |
| 건강보험심사평가원 서비스 | 단순 투약 내역 조회만 가능 |

기존 서비스들은 **의료 전문인 전용**이거나 단순 조회에 그쳐, 일반인이 자신의 상황에 맞는 의약품 정보를 쉽게 얻기 어렵습니다.

### 목표

- 개인 상황에 따른 **맞춤형 의약품 정보** 제공 (용법용량, 투약방법 등)
- 어려운 의학용어를 **일반인이 이해하기 쉬운 언어**로 설명
- **처방오류 예방**에 기여

<br>

## ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| **AI 챗봇** | LangChain RAG + OpenAI API 기반 의약품 맞춤 설명 |
| **소셜 로그인** | 카카오, 구글 OAuth2 로그인 지원 |
| **대화 기록 저장** | 요청/응답 모두 DB에 저장하여 마이페이지에서 조회 가능 |
| **마이페이지** | 이전 대화기록 및 유저 정보 관리 |
| **랜딩페이지** | 서비스 소개 및 사용법 안내 (모바일 최적화) |

<br>

## 🛠️ 기술 스택

### Backend
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)
![ChromaDB](https://img.shields.io/badge/ChromaDB-FF6B35?style=flat)

### AI / ML
![OpenAI](https://img.shields.io/badge/OpenAI_API-412991?style=flat&logo=openai&logoColor=white)
![LangChain](https://img.shields.io/badge/LangChain-1C3C3C?style=flat)

### Infrastructure
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![GCP](https://img.shields.io/badge/GCP-4285F4?style=flat&logo=googlecloud&logoColor=white)

### Auth
![Kakao](https://img.shields.io/badge/Kakao_OAuth-FFCD00?style=flat&logo=kakao&logoColor=black)
![Google](https://img.shields.io/badge/Google_OAuth-4285F4?style=flat&logo=google&logoColor=white)

<br>

## 🏗️ 시스템 아키텍처

```
[사용자]
   │
   ▼
[Spring Boot 서버 - GCP VM]
   │
   ├─── [PostgreSQL]          유저 정보, 채팅 기록 저장
   │       └── Docker 컨테이너
   │
   ├─── [ChromaDB]            의약품 벡터 데이터 저장
   │       └── Docker 컨테이너
   │
   └─── [OpenAI API]          ChatGPT API 연동
           └── LangChain RAG 프로세스

배포: GCP VM + Docker 컨테이너
```

### DB 선택 이유

```
PostgreSQL 선택 이유:
→ 대량의 채팅 기록 처리에 용이
→ Docker 기반 배포 환경에 적합
→ 기존 계획(MariaDB)에서 변경

ChromaDB 선택 이유:
→ RAG 프로세스에서 벡터DB 필수
→ 대규모 텍스트 의미 기반 검색 지원
→ 의미 유사도 기반 검색 기능 활용
```

<br>

## 🤖 핵심 구현 - RAG 프로세스

의약품 정보를 정확하게 제공하기 위해 LangChain RAG(Retrieval-Augmented Generation) 프로세스를 활용했습니다.

```
[공공 데이터]
식품의약품안전처_의약품개요 정보
         │
         ▼
[임베딩 (Embedding)]
자연어를 벡터로 변환
         │
         ▼
[ChromaDB 저장]
벡터 데이터셋 저장
         │
         ▼
[사용자 질문 입력]
         │
         ▼
[의미 유사도 기반 검색]
ChromaDB에서 관련 Context 검색
         │
         ▼
[LangChain RetrievalQAWithSourcesChain]
검색된 Context 기반 답변 생성 프롬프트 구성
         │
         ▼
[OpenAI ChatGPT API]
최종 답변 생성 (쉬운 용어로 가공)
         │
         ▼
[답변 제공 + DB 저장]
```

**핵심 구현 포인트:**
- `RetrievalQAWithSourcesChain` 활용으로 대량 문서에서 관련 Context 검색
- 사용자 특성과 쉬운 용어로 답변하도록 프롬프트 가공
- 요청/응답 모두 PostgreSQL에 저장하여 대화 기록 유지

<br>

## 🔐 인증 구현

Spring Security 없이 **인터셉터 방식**으로 로그인을 직접 구현했습니다.

```
소셜 로그인 흐름:
카카오/구글 OAuth2 API → 추가 필드 수집(인터셉터) → 세션 관리

직접 구현 이유:
→ Spring Security 없이 인증 로직을 직접 이해하기 위한 학습 목적
→ 비밀번호 직접 암호화 처리
→ 추후 Spring Security 적용으로 개선 예정
```

<br>

## 💾 주요 백엔드 구현

```
회원가입 / 소셜 로그인 (카카오, 구글 OAuth2)
├── 인터셉터 기반 로그인 처리
├── 비밀번호 직접 암호화
└── 추가 필드 수집 (인터셉터 활용)

채팅 기록 저장
├── 요청/응답 모두 PostgreSQL에 저장
└── 사용자 이메일 기반 대화 기록 조회

마이페이지 API
├── 유저 정보 조회/수정
└── 이전 대화기록 조회

Docker + GCP 배포
├── 로컬 jar 빌드 → Docker 이미지 생성
├── Docker Hub 원격 저장소 관리
└── GCP SSH 터미널에서 컨테이너 실행
```

<br>

## 🚀 배포

Docker + Google Cloud Platform(GCP)을 활용한 컨테이너 기반 배포

```bash
# 1. 로컬에서 jar 파일 빌드
./gradlew build

# 2. Dockerfile로 컨테이너 생성 및 Docker Hub 푸시
docker build -t pilleasy-chat .
docker push pilleasy-chat

# 3. GCP SSH 터미널에서 컨테이너 실행
docker pull pilleasy-chat          # 앱 컨테이너
docker pull postgres               # PostgreSQL 컨테이너
docker pull chromadb/chroma        # ChromaDB 컨테이너
```

**GCP 선택 이유:** SSH를 통한 터미널 접근이 용이하고 빠르게 성장하는 클라우드 플랫폼

<br>

## 👥 팀원 및 역할

| 이름 | 역할 |
|------|------|
| 김현수 (팀장) | 백엔드 개발 (회원가입/로그인, 마이페이지, 대화기록 저장 등), Docker 컨테이너화, GCP 배포 |
| 신효식 | 백엔드 개발 (LangChain RAG 파이프라인 구현, OpenAI API 연동) |
| 보꾸옥안 | 프론트엔드 개발 (회원가입, 마이페이지) |
| 최정인 | 프론트엔드 개발 (회원가입, 마이페이지) |
| 함병훈 | 프론트엔드 개발 (챗봇 서비스 페이지) |
| 허연규 | 프론트엔드 개발 (랜딩페이지, 모바일 최적화) |

<br>

## 📝 프로젝트 성과

- 2024학년도 1학기 심화캡스톤디자인 수행 (2024.03 ~ 2024.06)
- 산학협력 기업: **코이헬스케어** (전문가 멘토링)
- 논문 1건 제출

<br>

## 🔍 한계점 및 개선 방향

```
현재 한계점:
├── 법적 제약으로 의약품 추천 기능 불가 (의료진만 가능)
├── 학습 데이터 부족으로 한정된 의약품만 답변 가능
└── Spring Security 미적용 (인터셉터 방식으로 구현)

개선 방향:
├── Spring Security 적용으로 보안 강화
├── 의약품 데이터 확충
└── CI/CD 파이프라인 구축
```
