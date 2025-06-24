const fs = require('fs');
const path = require('path');

function mergePackageJson(templatePath = 'package-template.json', depsPath = '../npm-dependencies.json', outputPath = 'package.json') {
  try {
    // 1. package-template.json 읽기
    const packageTemplate = JSON.parse(fs.readFileSync(templatePath, 'utf-8'));
  
    // 2. npm-dependencies.json 읽기
    const npmDeps = JSON.parse(fs.readFileSync(depsPath, 'utf-8'));
  
    // 3. package.json 삭제 (만약 존재한다면)
    if (fs.existsSync(outputPath)) {
      fs.unlinkSync(outputPath);
      console.log('Existing package.json deleted.');
    }

    // 4. package-template.json의 내용을 기본으로 사용하고, npm-dependencies.json의 dependencies를 병합
    const newPackageJson = {
      ...packageTemplate,
      dependencies: {
        ...packageTemplate.dependencies,
        ...npmDeps.dependencies, // 이 부분을 덮어쓰기보다 병합하는 방법으로 변경할 수도 있음
      }
    };
  
    // 5. 새로운 package.json 파일로 저장
    fs.writeFileSync(outputPath, JSON.stringify(newPackageJson, null, 2));
  
    console.log('New package.json has been created.');
  } catch (error) {
    console.error('Error occurred while merging package.json:', error);
  }
}

// 함수 호출
mergePackageJson();
