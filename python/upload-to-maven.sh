#!/bin/bash

# 변수 설정
NEXUS_URL="http://localhost:8081/repository/pypi/"
REPO_USERNAME="admin"  # Nexus에서 사용하는 사용자명
REPO_PASSWORD="Q!w2e3r4t5"  # Nexus에서 사용하는 비밀번호
DIST_DIR="./downloads"  # 빌드된 패키지가 위치한 디렉토리 (기본적으로 dist/에 생성됨)

# 1. dist 디렉토리에 패키지가 있는지 확인
if [ ! -d "$DIST_DIR" ] || [ -z "$(ls -A $DIST_DIR)" ]; then
          echo "No packages found in $DIST_DIR. Please build the package first."
            exit 1
fi

# 2. Nexus PyPI 리포지토리로 패키지 업로드
echo "Uploading packages to Nexus PyPI repository..."

twine upload --repository-url $NEXUS_URL -u $REPO_USERNAME -p $REPO_PASSWORD $DIST_DIR/*

# 3. 업로드 결과 확인
if [ $? -eq 0 ]; then
          echo "Packages uploaded successfully."
  else
            echo "Package upload failed."
              exit 1
fi