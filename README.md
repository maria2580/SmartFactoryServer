# Smart Factory Server

## 🌟 소개

**Smart Factory Server**는 스마트 팩토리 모니터링 시스템을 위한 백엔드 애플리케이션입니다. 사용자 관리, 센서 정의, 센서 데이터 수집 및 조회, 알람 설정, 그리고 알람 발생 시 실시간 푸시 알림 기능을 제공합니다. "Factory Agent"와 같은 데이터 수집 에이전트 및 모니터링 클라이언트와 연동되도록 설계되었습니다.

본 서버는 Spring Boot, Spring Data JPA, Firebase Admin SDK 등의 기술을 기반으로 구축되었습니다.

## 🏅 주요 성과
<div>
<div style="display: inline"><image src="https://github.com/user-attachments/assets/61a08bdf-ebcb-4d5c-82b4-84b23467a586" width="400">
      <p>AIoT 스마트 팩토리 시스템 연구를 위한 테스트베드 구축</p>
   </div> 
      <div style="display: inline"><image src="https://github.com/user-attachments/assets/08b7f39e-1905-497b-a0a5-b4b10aea42be" width="400">
      <p>2022 한국정보기술학회 대학생 논문 경진대회 동상 수상</p>
   </div> 
</div>
## ✨ 주요 기능

* **사용자 관리:**
    * 회원가입 및 로그인 인증 (`/sign_up`, `/login`)
    * 사용자 ID 기반 검색 (`/User_list/{partOfID}`)
* **센서 정의 관리 (사용자별):**
    * 사용자가 모니터링할 센서의 정보(이름, 데이터 수집 명령어)를 CRUD(`POST, GET, PATCH, DELETE /sensors`) 할 수 있도록 지원합니다.
* **센서 데이터 수집 및 조회:**
    * Factory Agent로부터 주기적으로 센서 값들을 수신하여 시계열 데이터로 저장합니다 (`POST /sensors_value`).
    * 저장된 센서 데이터 조회 기능:
        * 특정 사용자의 전체 이력 (`GET /sensors_value/{ID}/all`)
        * 특정 사용자의 기간별 이력 (`GET /sensors_value/{ID}?from=...&to=...`)
        * 특정 사용자의 가장 최근 센서 값 세트 (`GET /sensors_value/{ID}/resent_one`)
* **알람 관리 (사용자별, 센서별):**
    * 각 센서에 대해 사용자가 알람 발생 조건(최소/최대 임계값)을 설정하고 관리(CRUD)할 수 있습니다 (`/alarm` 엔드포인트).
* **실시간 알람 모니터링 및 푸시 알림:**
    * `SensorObserverThread`가 백그라운드에서 주기적으로 최신 센서 값을 사용자가 설정한 알람 조건과 비교합니다.
    * 알람 조건 충족 시, Firebase Cloud Messaging(FCM)을 통해 해당 사용자에게 실시간 푸시 알림을 전송합니다.
* **팔로우 기능:**
    * 사용자 간 팔로우/팔로워 관계를 맺고 관리할 수 있습니다. (`/followership` 엔드포인트)
    * 팔로우 요청 및 수락/거부 기능을 지원합니다.
* **푸시 토큰 관리:**
    * 사용자별 FCM 푸시 토큰을 등록하고 관리합니다 (`POST /pushToken/{ID}`).

## 🛠️ 기술 스택

* **언어:** Java
* **프레임워크:** Spring Boot
    * Spring Web: RESTful API 개발
    * Spring Data JPA: 데이터베이스 연동 및 ORM
    * Spring AOP: `@Async`를 통한 비동기 처리
* **데이터베이스:** 관계형 데이터베이스 (MySQL, PostgreSQL, H2 등 - `application.properties` 설정에 따름)
* **푸시 알림:** Firebase Admin SDK (Firebase Cloud Messaging)
* **라이브러리:**
    * Lombok: 보일러플레이트 코드 감소
    * `org.json`: JSON 문자열 파싱 (센서 값 저장/조회 시)
* **빌드 도구:** Gradle 또는 Maven

## 📄 API 엔드포인트

### User Authentication & Management (`User_Controller`)

* **회원가입:** `POST /sign_up`
    * Request Body: `SignUpVO` (`{ "ID": "string", "PW": "string" }`)
    * Response: String (결과 상태 코드 - `LoginStatus.PERMITTED` 또는 `LoginStatus.EXIST_ID`)
* **로그인:** `POST /login`
    * Request Body: `LoginVO` (`{ "ID": "string", "PW": "string", "env": int }`)
    * Response: String (결과 상태 코드 - `LoginStatus.PERMITTED` 또는 `LoginStatus.DENIED`)
* **사용자 ID 부분 검색:** `GET /User_list/{partOfID}`
    * Response: `List<String>` (매칭되는 사용자 ID 목록)
* **사용자 인덱스로 ID 조회:** `GET /user/{index}`
    * Response: String (사용자 ID)

### Sensor Definition (`SensorController`)

* **새 센서 등록:** `POST /sensors`
    * Request Body: `SensorDTO` (`{ "name": "string", "command": "string", "user_id": "string" }`)
    * Response: void
* **사용자의 모든 센서 조회:** `GET /sensors/{ID}`
    * Path Variable: `ID` (사용자 ID)
    * Response: `List<SensorDAO>`
* **센서 정보 업데이트:** `PATCH /sensors`
    * Request Body: `Sensor` (`{ "index": long, "name": "string", "command": "string" }`)
    * Response: void
* **센서 삭제:** `DELETE /sensors/{index}`
    * Path Variable: `index` (센서의 고유 인덱스)
    * Response: void

### Sensor Values (`Sensor_value_Controller`)

* **센서 값 전송/저장:** `POST /sensors_value`
    * Request Body: `SensorValueDTO` (`{ "sensorValues": [{"name":"string", "value":"string"}, ...], "ID": "string" }`)
    * Response: String (empty)
* **최근 센서 값 조회 (사용자별):** `GET /sensors_value/{ID}/resent_one`
    * Path Variable: `ID` (사용자 ID)
    * Response: `SensorValue[]`
* **기간별 센서 값 조회 (사용자별):** `GET /sensors_value/{ID}`
    * Path Variable: `ID` (사용자 ID)
    * Query Parameters: `from` (String, "yyyyMMddHHmmss"), `to` (String, "yyyyMMddHHmmss")
    * Response: `SensorValue[][]` (시간 순서대로 각 시점의 센서 값 배열)
* **모든 센서 값 이력 조회 (사용자별):** `GET /sensors_value/{ID}/all`
    * Path Variable: `ID` (사용자 ID)
    * Response: `SensorValue[][]`

### Alarm Management (`Alarm_Controller`)

* **알람 설정 추가:** `POST /alarm/{ID}`
    * Path Variable: `ID` (사용자 ID)
    * Request Body: `AlarmDTO` (`{ "sensorIndex": long, "minimum": double, "maximum": double }`)
    * Response: String (empty)
* **사용자의 알람 설정 목록 조회:** `GET /alarm/{ID}`
    * Path Variable: `ID` (사용자 ID)
    * Response: `List<AlarmDTO>`
* **알람 기준치 수정:** `PATCH /alarm`
    * Request Body: `AlarmDTO` (`{ "index": long, "minimum": double, "maximum": double }`)
    * Response: String (empty)
* **알람 설정 삭제:** `DELETE /alarm/{index}`
    * Path Variable: `index` (알람 설정의 고유 인덱스)
    * Response: String (empty)

### Followership (`FollowerShipController`)

* **팔로우 요청:** `POST /followership`
    * Request Body: `FollowshipVO` (`{ "myID": "string", "targetID": "string" }`)
    * Response: String (empty)
* **내가 팔로우하는 목록 조회:** `GET /followership/{ID}/follow`
    * Path Variable: `ID` (나의 사용자 ID)
    * Response: `List<FollowerShipDTO>`
* **나를 팔로우하는 목록 조회:** `GET /followership/{ID}/follower`
    * Path Variable: `ID` (나의 사용자 ID)
    * Response: `List<FollowerShipDTO>`
* **팔로우 상태 변경 (수락/비활성화):** `PATCH /followership/{index}`
    * Path Variable: `index` (FollowerShip 레코드의 인덱스)
    * Query Parameter: `enable` (boolean)
    * Response: String (empty)
* **팔로우 관계 삭제:** `DELETE /followership/{ID}` (주: Path Variable이 `{ID}`로 되어 있으나, 일반적으로 팔로우 관계의 고유 인덱스를 사용해야 합니다. 코드 확인 필요)
    * Path Variable: `ID` (FollowerShip 레코드의 인덱스)
    * Response: String (empty)

### Push Token (`PushTokenController`)

* **푸시 토큰 등록/수정:** `POST /pushToken/{ID}`
    * Path Variable: `ID` (사용자 ID)
    * Request Body: String (FCM 푸시 토큰)
    * Response: String (empty)

### Test (`Test_Controller`)

* **센서 관찰 스레드 시작 / 테스트 FCM 발송:** `GET /observer/{index}`
    * Path Variable: `index` ("0" 또는 "1")
    * Response: String

## 📁 프로젝트 구조

* **`com.primitive.SmartFactoryServer`**: 루트 패키지
    * `SmartFactoryServerApplication.java`: Spring Boot 메인 애플리케이션 클래스.
    * `SensorObserverThread.java`: 백그라운드에서 센서 값을 모니터링하고 알람 조건 시 FCM을 발송하는 비동기 스레드.
    * `FCMessage.java`: Firebase Cloud Messaging 전송 로직 구현.
    * `APIKEY.java` (외부 파일 필요): FCM 서버 키를 관리하는 클래스.
    * **`Controller`**: API HTTP 요청을 처리하는 컨트롤러.
    * **`DAO`**: 데이터 접근 객체 (JPA Entities 및 Repositories).
        * `BaseTimeEntity.java`: 생성/수정 시간을 자동 관리하는 추상 엔티티.
        * `users`, `Sensors`, `SensorValues`, `Alarms`, `FollowerShips` (각각 Entity와 Repository 포함).
    * **`DTO`**: 데이터 전송 객체 (클라이언트와 서버 간 데이터 교환용).
    * **`VO`**: 값 객체 (주로 요청 바디를 매핑하는 데 사용).

## ⚙️ 설정 및 실행

### 사전 준비 사항

1.  **JDK**: Java Development Kit (Spring Boot 버전에 맞는 버전 권장, 예: JDK 11 또는 17).
2.  **데이터베이스 서버**: MySQL, PostgreSQL 등 관계형 데이터베이스. (또는 개발용 H2).
3.  **Firebase 프로젝트 설정**:
    * Firebase 콘솔에서 프로젝트 생성.
    * 프로젝트 설정 > 서비스 계정 > 새 비공개 키 생성 (JSON 파일 다운로드).
    * 다운로드한 서비스 계정 키 파일을 서버 환경에 배치하고, `GoogleCredentials.getApplicationDefault()`가 해당 파일을 찾을 수 있도록 환경 변수 `GOOGLE_APPLICATION_CREDENTIALS`를 설정하거나, 코드 내에서 직접 경로를 지정해야 합니다.
4.  **FCM 서버 키**:
    * `com.primitive.SmartFactoryServer.APIKEY.java` 파일을 생성하고, `getAPIKEY()` 메소드가 FCM 서버 키를 반환하도록 구현해야 합니다.
        ```java
        // 예시: com/primitive/SmartFactoryServer/APIKEY.java
        package com.primitive.SmartFactoryServer;

        public class APIKEY {
            public static String getAPIKEY() {
                return "YOUR_FCM_SERVER_KEY_HERE";
            }
        }
        ```

### 설정

1.  **데이터베이스 연결 (`src/main/resources/application.properties` 또는 `application.yml`):**
    ```properties
    # 예시 (MySQL)
    spring.datasource.url=jdbc:mysql://localhost:3306/smartfactory_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
    spring.datasource.username=db_user
    spring.datasource.password=db_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.jpa.hibernate.ddl-auto=update # 개발: update, 운영: validate 또는 none
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    ```
2.  **Firebase Admin SDK 초기화:**
    * `FCMessage.java`의 `init()` 메소드는 `GoogleCredentials.getApplicationDefault()`를 사용합니다. 환경 변수 설정이 중요합니다.
    * `FirebaseOptions.setDatabaseUrl()`에 사용된 URL (`https://smartfactory-25ac3.firebaseio.com/`)이 실제 Firebase 프로젝트와 일치하는지 확인합니다. (주: 일반적으로 Admin SDK는 Realtime Database URL이 필수는 아니며, Messaging에 주로 사용됩니다.)

### 실행

1.  **빌드:**
    * Gradle: `./gradlew build`
    * Maven: `./mvnw package`
2.  **실행:**
    * 빌드된 JAR 파일 실행: `java -jar build/libs/SmartFactoryServer-0.0.1-SNAPSHOT.jar` (파일명은 실제 빌드 결과에 따라 다를 수 있음)
    * 또는 IDE(IntelliJ, Eclipse STS 등)에서 직접 Spring Boot 애플리케이션 실행.

### 최초 실행 시

* `Test_Controller`의 `GET /observer/0` 엔드포인트를 호출하여 `SensorObserverThread`를 시작해야 실시간 알람 모니터링이 활성화됩니다. (코드 상으로는 `init` 플래그가 있어 한 번만 실행되도록 되어 있음).

## 🔑 주요 컴포넌트 상세

### `SensorObserverThread`

* `@Async` 어노테이션으로 비동기적으로 실행됩니다.
* 15초 간격으로 모든 사용자의 알람 설정을 확인합니다.
* 각 사용자의 최신 센서 값을 가져와 설정된 알람 범위(min/max)를 벗어나는지 검사합니다.
* 범위를 벗어난 경우, `FCMessage.send()`를 호출하여 해당 사용자에게 푸시 알림을 보냅니다.
* 알람 검사 작업은 내부적으로 `ThreadPoolExecutor`를 사용하여 병렬 처리될 수 있도록 구성되어 있습니다.

### `FCMessage`

* Firebase Admin SDK를 사용하여 FCM 메시지를 발송합니다.
* `init()`: FirebaseApp을 초기화합니다. 애플리케이션 시작 시 또는 첫 메시지 발송 시 호출됩니다.
* `send()`: 지정된 토큰(사용자 기기 토큰)으로 제목과 메시지 내용을 담은 푸시 알림을 보냅니다.

### 데이터 저장 방식 (센서 값)

* `SensorValueDAO`의 `sensorValues` 필드는 `SensorValueDTO`의 `valueToString()` 메소드를 통해 생성된 JSON 문자열 형태로 여러 센서의 값을 한 번에 저장합니다. (예: `"[{\"name\":\"temp\", \"value\":\"25\"}, {\"name\":\"humid\", \"value\":\"60\"}]"`)
* 데이터 조회 시(`Sensor_value_Controller`), 이 JSON 문자열을 다시 `org.json.JSONArray` 및 `org.json.JSONObject`를 사용하여 파싱하여 `SensorValue[]` 객체로 변환합니다.

