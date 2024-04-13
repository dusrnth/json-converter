# JSON Converter

JSON Converter는 JSON 데이터를 CSV 또는 다양한 포맷 형식으로 변환하는 Spring Boot 애플리케이션입니다.

**지원 현황:**
- [x] CSV
- [ ] Excel
- [ ] ...

## 실행 준비 사항

- Java 17 이상
- Docker
- Gradle

## 애플리케이션 실행

1. 프로젝트 클론
   ```
   git clone https://github.com/yourusername/json-converter.git
   cd json-converter
   ```

2. 스크립트에 실행 권한을 부여
   ```
   chmod +x run.sh
   ```

3. 스크립트를 실행
   ```
   ./run.sh
   ```

4. 메뉴에서 원하는 작업 선택
   - 1: 시작
   - 2: 재시작
   - 3: 중지
   - 4: 상태 확인
   - 9: 전체 Clean 빌드 및 시작

5. 애플리케이션 사용:
   웹 브라우저에서 `http://localhost:8080`에 접속하여 JSON Converter 애플리케이션을 사용할 수 있습니다.

## 기능 및 설계

### 요구사항

1. 기능적 요구사항:
   - JSON 데이터를 CSV로 변환
   - 변환된 CSV 데이터 다운로드 기능 제공
   - 웹 애플리케이션 형태로 제공

2. 비기능적 요구사항:
   - 사용자 친화적인 인터페이스
   - 대용량 JSON 데이터 안정적 처리
   - 오류 발생 시 적절한 피드백 제공

### 설계

1. 아키텍처:
   - 클라이언트-서버 아키텍처 사용
   - 클라이언트: 웹 브라우저에서 실행, 사용자 인터페이스 담당
   - 서버: JSON 데이터 받아 CSV로 변환, 변환된 데이터 클라이언트에 전달

2. 사용자 인터페이스:
   - 메인 화면: JSON 데이터 입력 텍스트 영역, 변환 버튼
   - 변환 버튼 클릭 시 서버로 JSON 데이터 전송, 변환된 CSV 데이터 다운로드
   - 오류 발생 시 알림 메시지 표시

3. 서버:
   - JSON 데이터를 CSV로 변환하는 API 엔드포인트 제공
   - JSONParser로 받은 JSON 데이터 파싱
   - JsonToCsvConverter로 파싱된 데이터 CSV 형식으로 변환
   - 변환된 CSV 데이터 응답으로 반환, 다운로드 가능하도록 처리

4. 데이터 흐름:
   - 사용자 JSON 데이터 입력 및 변환 버튼 클릭
   - 클라이언트 입력된 JSON 데이터 서버 API 엔드포인트로 전송
   - 서버 JSONParser로 JSON 데이터 파싱
   - 파싱된 데이터 JsonToCsvConverter로 CSV 형식 변환
   - 변환된 CSV 데이터 응답으로 클라이언트에 전달
   - 클라이언트 받은 CSV 데이터 다운로드 가능한 형태로 사용자에게 제공

5. 오류 처리:
   - JSON 데이터 파싱 오류 시 적절한 오류 메시지 사용자에게 표시
   - 서버 내부 오류 시 사용자에게 알림 메시지 표시, 오류 로그 기록

### 참고

- [JSON (JavaScript Object Notation)](https://www.json.org/json-ko.html)
- [JSON에 대해 알아보기 - Mozilla ](https://developer.mozilla.org/ko/docs/Learn/JavaScript/Objects/JSON)
- [JSON-java 라이브러리 - Github](https://github.com/stleary/JSON-java)
