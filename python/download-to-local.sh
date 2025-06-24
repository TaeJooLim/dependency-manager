#!/bin/bash

# 다운로드할 디렉토리 지정
DOWNLOAD_DIR="downloads"

# 라이브러리 목록 파일
LIBRARY_LIST="../python-dependencies.txt"

# 파일에서 한 줄씩 읽어 pip download 실행
while IFS= read -r line
do
    # 공백 및 특수문자 제거 후, 빈 줄이면 건너뛰기
    if [ -n "$line" ]; then
        echo "Downloading: $line"
        pip download "$line" --dest "$DOWNLOAD_DIR"
    fi
done < "$LIBRARY_LIST"