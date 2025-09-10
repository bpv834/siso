# SISO

**시니어(Senior)** 전용 소개팅 앱입니다. 기존 데이팅 앱과 달리 시니어 이용자 경험(가독성, 접근성, 간단한 흐름 등)에 초점을 맞춘 모바일 서비스입니다. 기획부터 개발까지 참여한 개인/팀 프로젝트입니다.

---

## 목차
- 프로젝트 소개
- 주요 기능
- 기술 스택
- 아키텍처
- 설치 및 실행 방법



---

## 프로젝트 소개
SISO는 연령대가 높은 사용자를 대상으로 한 데이팅/매칭 앱입니다. 단순하고 직관적인 UI, 큰 글씨, 손쉬운 탐색 흐름을 제공하여 시니어 사용자도 부담 없이 이용할 수 있도록 설계되었습니다.

프로젝트 기간: **2025.08.06 ~ 2025.09.05**

역할: 기획 · 프론트엔드(안드로이드) 개발 · 일부 백엔드 인터페이스 연동

---

## 주요 기능
- 회원 가입 / 로그인 (간소화된 입력 UI)
- 프로필 관리 (사진 업로드, 간단 자기소개)
- 매칭/추천 알고리즘 기반 소개팅 목록
- 채팅(메시지), 전화 및 알림 기능

---

## 기술 스택
- Android: Kotlin, Jetpack Compose
- 아키텍처: MVVM, Clean Architecture, Multi-Module
- DI: Hilt
- 상태 관리: StateFlow, Coroutine
- 네트워킹: Retrofit
- 백엔드/인프라: (Swagger 기반 API), Firebase(선택적 알림/인증 연동 가능)
- 빌드: Gradle (Kotlin DSL)
- 테스트: JUnit, AndroidX Test
---

## 아키텍처
- Presentation (Compose UI, ViewModel)
- Domain (UseCase,Repository)
- Data (RepositoryImpl → Remote / Local)

(참고: RepositoryImpl에서 request,response 반환 → UseCase/서비스에서 Model 변환 방식으로 설계되어 테스트 용이성과 계층 분리를 확보했습니다.)

---

## 개발 규칙 & 코드 스타일
- Kotlin 코딩 컨벤션 준수
- ViewModel에서 비즈니스 로직 처리, UI는 Compose로만 표현
- Repository는 순수 데이터 접근만 담당 (비즈니스 로직 없음)
- 커밋 메시지: `feat`, `fix`, `docs`, `refactor` 등 Conventional Commits 스타일 권장

