# dependency-manager

여러 언어의 라이브러리를 한 번에 다운로드하고 관리하는 오픈소스 도구

---

## 📖 소개

`dependency-manager`는 Java, Python, NPM에서 가장 널리 사용되고 안정적인 라이브러리들을 자동으로 조회하고, 로컬에 다운로드하며, 필요 시 Nexus와 같은 저장소로 업로드할 수 있도록 도와주는 도구입니다.

---

## 🤔 왜 만들었나요?

DevOps 업무를 하다 보면 폐쇄망 환경에서는 외부 저장소(jcenter, mavenCentral 등)에서 직접 라이브러리를 다운로드할 수 없습니다. 이 경우, 필요한 라이브러리를 일일이 수동으로 다운로드하고, 내부 보안 정책에 따라 검토 및 승인을 거쳐 사설 저장소(Nexus 등)에 업로드해야 합니다.

자주 쓰이는 라이브러리라도 매번 수작업으로 처리하는 일이 반복되다 보니 비효율적이고 번거로웠고,  
이 과정을 자동화해줄 수 있는 도구가 필요해 `dependency-manager`를 개발하게 되었습니다.

---

## 🧩 지원 언어

- Java (Maven)
- Python (PyPI)
- NPM (Node.js)

각 언어에 맞는 포맷의 의존성 리스트를 생성하고, 실제 패키지를 로컬에 다운로드하거나 저장소에 업로드할 수 있습니다.

---

## ⚙️ 주요 기능

- `libraries.io` API를 이용한 인기 라이브러리 자동 조회
- 안정적인 최신 버전 자동 선택
  - Java: BOM
  - Python: Pepy
  - NPM: 최신 릴리즈
- 각 언어별 의존성 파일 생성
  - `maven-dependencies.json`
  - `python-dependencies.json`
  - `npm-dependencies.json`
- 라이브러리 다운로드
- (Python만) Nexus 저장소 업로드 지원 (선택사항)

---

## 🚀 설치 및 실행 방법

1. [libraries.io](https://libraries.io/)에 가입하여 API 키를 발급받습니다.  
2. [pepy.tech](https://pepy.tech/)에 가입하여 API 키를 발급받습니다. (Python 사용 시)  
3. 발급받은 키를 `config.cfg`에 입력합니다.  
4. JDK 17 이상을 설치합니다.  
5. 프로젝트 디렉터리로 이동합니다:  
   ```bash
   cd 설치폴더```
6.dependency-manager를 실행하고 프로세스를 선택합니다:
   ```bash
   java -jar dependencymanager.jar
   Hello World!
   실행할 클래스를 선택하세요 (1: JavaLib, 2: NpmLib, 3: PythonLib):```
7.선택한 언어에 따라 아래 명령어를 실행합니다:
   ###JAVA
   ```bash
   cd java
   gradle downloadAllDependencies```

   ###NPM
   ```bash
   cd npm
   node merge-package.js
   yarn install```

   ###Python 다운로드
   ```bash
   cd python
   sh download-to-local.sh```

   ###Python 업로드(선택사항)
   ```bash
   cd python
   sh upload-to-maven.sh```

## 📌 저장소

🔗 [GitHub 바로가기](https://github.com/TaeJooLim/dependency-manager/tree/develop)

---

## ⚠️ 특이사항

1. **호환성 이슈**  
   Node.js 및 JDK 버전에 따라 일부 라이브러리의 호환성 문제가 발생할 수 있습니다. 문제가 발생할 경우, 해당 언어의 런타임 버전을 변경한 후 다시 시도해야 합니다.

2. **pepy API 호출 제한**  
   [`pepy.tech`](https://pepy.tech)는 분당 6회 이상의 API 호출 시 `429 Too Many Requests` 오류를 반환합니다. 이 경우, `config.cfg` 파일 내 `thread.sleep` 값을 조정하여 호출 간격을 늘릴 수 있습니다.

3. **다운로드 경로 설정**  
   각 언어별 라이브러리 다운로드 경로는 사용자 설정에 따라 지정할 수 있으며, 경로가 올바르게 설정되지 않으면 다운로드가 실패할 수 있습니다.


