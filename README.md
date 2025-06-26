# dependency-manager

## 프로젝트 소개

`dependency-manager`는 **Java**, **Python**, **NPM** 등 다양한 언어 및 패키지 시스템에서 가장 많이 사용되고 안정적인 라이브러리를 자동으로 조회하고 로컬에 다운로드할 수 있도록 도와주는 도구입니다.

이 도구는 [libraries.io](https://libraries.io/)와 같은 오픈소스 메타데이터 서비스를 활용하여, 언어별로 인기 있고 검증된 라이브러리 목록을 가져온 후, 실제 바이너리를 로컬에 저장합니다. 또한 필요에 따라 **Nexus**와 같은 사설 저장소로 업로드하는 기능을 통해 조직 내 패키지 관리를 쉽게 할 수 있도록 지원합니다.

지원하는 언어 및 패키지 시스템:
- Java (Maven 기반)
- Python (PyPI)
- NPM (Node.js)

---

## 프로젝트 구성

- **Framework**: Spring Boot  
- **Language**: Java  
- **Build Tool**: Gradle  

`dependency-manager`는 Spring Boot 기반으로 개발되었으며, 각 언어별로 다음과 같은 방식으로 라이브러리를 관리합니다:

- **Java (Maven)**  
  - `maven-dependencies.json` 생성  
  - Gradle 태스크(`downloadAllDependencies`)를 통해 로컬에 다운로드

- **Python (PyPI)**  
  - `python-dependencies.json` 생성  
  - `python/download-to-local.sh` 스크립트를 통해 라이브러리 다운로드  
  - (선택사항) `python/upload-to-maven.sh` 스크립트를 실행해 Nexus에 업로드 가능

- **NPM (Node.js)**  
  - `npm-dependencies.json` 생성  
  - `merge-package.js` 실행으로 `package.json` 생성 후 `yarn install` 수행

---

## 프로세스

1. **라이브러리 조회**  
   `libraries.io` API를 이용하여 각 언어별 가장 인기 있는 라이브러리를 조회합니다.

2. **안정적인 버전 선택**  
   - Java: BOM(Bill of Materials) 기준  
   - Python: [pepy.tech](https://pepy.tech)의 다운로드 수 기준  
   - NPM: 안정화된 최신 릴리즈 기준

3. **의존성 매핑 파일 생성**  
   - Java: `maven-dependencies.json`  
   - Python: `python-dependencies.json`  
   - NPM: `npm-dependencies.json` → `merge-package.js` 실행 → `package.json` 생성

4. **라이브러리 다운로드 및 업로드**  
   각 언어에 맞는 방식으로 실제 패키지를 로컬에 다운로드하고, 필요시 업로드할 수 있습니다.

---

## 프로그램 사용 방법

1. [libraries.io](https://libraries.io/)에 가입하여 API 키를 발급받습니다.  
2. [pepy.tech](https://pepy.tech/)에 가입하여 API 키를 발급받습니다. (Python 사용 시)  
3. **JDK 17** 이상을 설치합니다.  
4. 프로젝트 디렉터리로 이동합니다:  
   ```bash
   cd 설치폴더/app/build/libs
5. dependency-manager를 실행하고 프로세스를 선택합니다:
   실행 후, 명령어 입력으로 원하는 프로세스(예: 1, 2, 3)를 선택합니다.
   ```bash
   java -jar dependencymanager.jar
6. 선택한 언어에 따라 다음 명령어 중 하나를 실행하여 라이브러리를 다운로드하거나 업로드합니다:
   Java
   ```bash
   gradle downloadAllDependencies

   Python 다운로드
   ```bash
   sh python/download-to-local.sh

   Python Nexus업로드 (선택사항)
   ```bash
   sh python/upload-to-maven.sh

   NPM
   ```bash
   node merge-package.js
   yarn install
