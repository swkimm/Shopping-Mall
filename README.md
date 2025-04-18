# 🏈 터치다운 – 미식축구 용품 쇼핑몰

미식축구 용품을 판매하는 SPA 기반 쇼핑몰 웹 애플리케이션입니다.  
Vue.js와 Spring Boot를 활용한 풀스택 개발 개인 프로젝트로,  
상품 조회부터 장바구니, 주문 및 관리자 상품 관리 기능까지  
쇼핑몰 운영에 필요한 기본 기능들을 모두 구현하였습니다.

---

## 🔧 사용 기술

| 영역 | 기술 |
|------|------|
| Frontend | Vue.js, Vue Router, Axios, Tailwind CSS |
| Backend | Spring Boot, Java, MyBatis |
| DB | MySQL |
| Build Tool | Gradle |
| 기타 | RESTful API 설계, SPA 구성 |

---

## 📌 주요 기능

### 사용자 기능
- ✅ 상품 목록 및 상세 조회
- ✅ 장바구니 담기 및 삭제
- ✅ 주문서 작성 및 결제 흐름 구현 (시뮬레이션)
- ✅ 반응형 UI 구성

### 관리자 기능
- ✅ 로그인 기반 관리자 인증
- ✅ 상품 등록 / 수정 / 삭제 기능
- ✅ 관리자 전용 상품 목록 관리 화면

---

## 🛠 프로젝트 구조


<pre>
Shopping-Mall/
├── frontend/              # Vue 기반 프론트엔드
│   └── src/
│       ├── components/    # 공통 UI 컴포넌트
│       ├── views/         # 사용자 & 관리자 페이지
│       └── router/        # Vue Router 설정
├── src/                   # 백엔드 Java (Spring Boot)
│   ├── controller/
│   ├── service/
│   ├── mapper/
│   └── domain/            # Entity
├── build.gradle
└── README.md
</pre>



---

## 💡 프로젝트 기획 배경

기존 국내 미식축구 용품점은 대부분 웹사이트가 노후화되어 있으며,  
결제 방식도 계좌이체에 국한되는 등 사용 편의성이 크게 떨어졌습니다.  
이에 따라 최신 쇼핑몰의 트렌드를 반영한 미식축구 용품 전문몰을  
직접 기획하고 구현하는 개인 프로젝트로 개발하게 되었습니다.
