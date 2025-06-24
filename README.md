# dependency-manager

## 프로젝트 소개

`dependency-manager`는 여러 프로그래밍 언어에서 안정적인 버전의 라이브러리들을 자동으로 다운로드하고 업로드하는 도구입니다. 이 도구는 `libraries.io`와 같은 서비스를 이용하여 각 언어별로 가장 널리 사용되고 안정적인 버전의 라이브러리를 쉽게 관리할 수 있도록 돕습니다.

지원하는 언어:
- Java
- Python
- NPM (Node.js)

## 프로젝트 구성

- **Language**: Java
- **Build**: Gradle

## 프로세스

1. **라이브러리 조회**: `libraries.io`에서 각 언어별로 가장 많이 사용되는 라이브러리 목록을 조회합니다.
    - Java: Maven
    - Python: PyPI
    - NPM: NPM

2. **안정적인 버전 조회**: 각 언어별로 가장 안정적인 버전의 라이브러리를 조회합니다.
    - Java: BOM (Bill of Materials)
    - Python: Pepy
    - NPM: NPM

3. **라이브러리 매핑**: 각 언어별로 라이브러리 이름과 버전을 매핑하여 다운로드 파일을 생성합니다. 이후, 다운로드 프로세스를 실행합니다.
    - Java: `maven-dependencies.json` → `gradle downloadAllDependencies`
    - Python: `python-dependencies.txt` → `pip install -r python-dependencies.txt -d <<다운받을경로>>`
    - NPM: `webpack.json` → `node merge-package.js` → `yarn install`

## 프로그램 사용 방법

1. [libraries.io](https://libraries.io/)에서 가입하여 API 키를 발급받습니다.
2. [pepy.tech](https://pepy.tech/)에 가입하여 API 키를 발급받습니다. (Python을 선택한 경우)
3. JDK 8을 설치합니다.
4. 디렉터리를 변경합니다.
    ```bash
    $ cd 설치폴더/app/build/libs
    ```
5. `dependency-manager` 실행:
    ```bash
    java -jar dependency-manager-all.jar
    ```
6. 원하는 프로세스에 대한 파라미터를 입력합니다 (예: 1, 2, 3).
7. CMD창 또는 리눅스 환경에서 다운로드 프로세스를 실행합니다.
    - Java: `gradle downloadAllDependencies`
    - NPM: `node merge-package.js > yarn install`
    - Python: `sh download-to-local.sh`

## 특이사항

1. **호환성 이슈**: Node.js 버전과 JDK 버전에 따라 호환되는 라이브러리가 달라서 에러가 발생할 수 있습니다. 이 경우, 적합한 버전으로 다시 시도해야 합니다.
2. **다운로드 경로**: 각 언어별로 다운로드할 경로를 지정할 수 있으며, 경로가 올바르게 설정되지 않으면 다운로드가 실패할 수 있습니다.
