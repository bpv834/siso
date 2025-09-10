# SISO

**시니어(Senior)** 전용 소개팅 앱입니다. 기존 데이팅 앱과 달리 시니어 이용자 경험(가독성, 접근성, 간단한 흐름 등)에 초점을 맞춘 모바일 서비스입니다. 기획부터 개발까지 참여한 개인/팀 프로젝트입니다.

---

## 목차
- 프로젝트 소개
- 주요 기능
- 기술 스택
- 아키텍처
- 설치 및 실행 방법
- 주요 디렉터리 구조
- 개발 규칙 & 코드 스타일
- 배포 & 릴리즈
- 기여 방법
- 라이선스
- 연락처

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
- 채팅(메시지) 및 알림 기능
- 위치 기반 필터(옵션)
- 관리자/운영용 간단 모니터링(로그/통계) —(선택)

---

## 기술 스택
- Android: Kotlin, Jetpack Compose
- 아키텍처: MVVM, Clean Architecture
- DI: Hilt
- 상태 관리: StateFlow, Coroutine
- 네트워킹: Retrofit
- 백엔드/인프라: (Swagger 기반 API), Firebase(선택적 알림/인증 연동 가능)
- 빌드: Gradle (Kotlin DSL)
- 테스트: JUnit, AndroidX Test

---

## 아키텍처
- Presentation (Compose UI, ViewModel)
- Domain (UseCase)
- Data (Repository → Remote / Local, DTO/VO)
- Repository 계층은 VO 반환, UseCase/Service 계층에서 Model로 변환하여 사용합니다.

(참고: Repository에서 VO 반환 → UseCase/서비스에서 Model 변환 방식으로 설계되어 테스트 용이성과 계층 분리를 확보했습니다.)

---

## 설치 및 실행 방법 (개발 환경)
1. 레포지토리 클론
```bash
git clone https://github.com/<your-org-or-username>/siso-android.git
cd siso-android
```

2. Android Studio 열기  
   - Android Studio Arctic Fox 이상 권장
   - JDK 11 권장

3. 환경 변수 / 비밀값 설정  
   - `local.properties` 또는 Secret Manager에 API 키, Firebase 설정 추가  
   (예: `API_BASE_URL`, `FIREBASE_JSON` 등 — 프로젝트에 따라 파일/설정 다름)

4. 빌드 & 실행
```bash
./gradlew assembleDebug
# 또는 Android Studio에서 Run
```

5. 디버깅
- 디바이스(실물 또는 에뮬레이터) 연결 후 실행
- 로그: `Logcat` 확인

---

## 주요 디렉터리 구조 (예)
```
app/
├─ src/
│  ├─ main/
│  │  ├─ java/com/yourorg/siso
│  │  │  ├─ ui/                # Compose 화면
│  │  │  ├─ presentation/      # ViewModel
│  │  │  ├─ domain/            # UseCases, Models
│  │  │  ├─ data/              # Repository, API
│  │  │  └─ di/                # Hilt 모듈
│  │  └─ res/
│  └─ test/
└─ build.gradle.kts
```

---

## 개발 규칙 & 코드 스타일
- Kotlin 코딩 컨벤션 준수
- ViewModel에서 비즈니스 로직 처리, UI는 Compose로만 표현
- Repository는 순수 데이터 접근만 담당 (비즈니스 로직 없음)
- 커밋 메시지: `feat`, `fix`, `docs`, `refactor` 등 Conventional Commits 스타일 권장
- PR 템플릿 사용 (간단: 변경 목적, 변경 사항, 테스트 방법)

---

## 배포 & 릴리즈
- 내부 테스트: Firebase App Distribution 또는 Internal Track
- 프로덕션: Play Store (릴리즈 키스토어 관리 주의)
- 버전 관리: semantic versioning 사용 권장 (ex: v1.0.0)

---

## 기여 방법
1. Issue 생성 또는 기존 Issue 확인
2. 기능 브랜치 생성: `git checkout -b feat/<short-description>`
3. PR 생성: 변경사항 설명, 스크린샷(있다면), 테스트 방법 포함
4. 코드 리뷰 후 merge

---

## 라이선스
(프로젝트에 맞게 선택) 예: `MIT License`  
라이선스를 여기에 추가하세요.

---

## 연락처
- 개발자: hj c (GitHub: `bpv834`)  
- 프로젝트 관련 문의는 이슈 또는 PR 코멘트로 남겨주세요.
