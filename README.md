# dependency-manager

## 프로젝트 소개

A tool for automating the download and upload of stable or widely-used library versions across various programming languages like Python and Java.

파이썬, 자바 등 다양한 프로그래밍 언어에서 안정적인 버전 또는 널리 사용되는 라이브러리 버전을 자동으로 다운로드하고 업로드하는 도구.

## 프로젝트 구성
Language: Java

Build: Gradle

## 프로세스
1.libraries.io에서 가장 많이 사용되는 순으로 라이브러리 명을 조회 (Java: Maven / Python: Pypi / NPM: NPM)

2.각 언어별 가장 Stable한 버전을 조회 (Java: BOM / Python: Pepy / NPM: NPM)

3.각 언어별 라이브러리와 버전을 매핑한 결과를 파일로 생성 후 다운로드 프로세스 실행

+Java: maven-dependencies.json > build.gradle > gradle build

+Python: python-dependencies.txt > pip install -d

+NPM: webpack.json > yarn build

## 프로그램 사용방법
1.JDK8을 설치한다.

2.java -jar dependency-manager/build/libs/dependency-manager-all.jar 커맨드 실행

3.원하는 프로세스의 파라미터를 입력(1,2,3)
