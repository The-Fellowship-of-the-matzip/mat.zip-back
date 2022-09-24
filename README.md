<p align="center">
    <img src="https://socialify.git.ci/The-Fellowship-of-the-matzip/mat.zip-back/image?description=1&logo=https%3A%2F%2Favatars.githubusercontent.com%2Fu%2F103999400%3Fs%3D200%26v%3D4&stargazers=1&theme=Dark">
</p>


<div align="center">
우테코 크루들이 이야기하는 잠실, 선릉 캠퍼스 맛집 모음집!😋
</div>
<br>
<div align="center">

[![Application](http://img.shields.io/badge/Application-blue?style=flat-square&logo=googlechrome&logoColor=white&link=https://https://gongcheck.day//)](https://matzip.today) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=The-Fellowship-of-the-matzip_mat.zip-back&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=The-Fellowship-of-the-matzip_mat.zip-back)

</div>

## 🔨 기술 스택
### application
- java 11
- spring framework (spring boot, spring mvc)
- spring data jpa
- oauth
- h2
- mysql
- vaadin
- rest assured
- rest docs

### build
- gradle

### ci
- github action

### infra
- aws ec2
- aws rds
- nginx

## 🖋 multi module
![Mind Ma@2x](https://user-images.githubusercontent.com/69106910/192093358-9078672e-09ee-4368-a42f-76f43c81b53a.png)
서버간의 의존성 분리를 위한 멀티모듈 설계

### [matzip-app-external-app](https://github.com/The-Fellowship-of-the-matzip/mat.zip-back/tree/main/matzip-app-external-api)
- 어플리케이션 모듈 계층
- 외부에서 사용해야하는 어플리케이션 정의 (사용자 api)

### [matzip-app-internal-app](https://github.com/The-Fellowship-of-the-matzip/mat.zip-back/tree/main/matzip-app-internal-api)
- 어플리케이션 모듈 계층
- 내부에서 사용해야하는 어플리케이션 정의 (어드민)

### matzip-secret-submodule
- 내부 모듈 계층
- 전체 시스템에서 사용되는 외부 노출이 되지 않는 데이터 정의

### [matzip-core](https://github.com/The-Fellowship-of-the-matzip/mat.zip-back/tree/main/matzip-core)
- 도메인 모듈 계층
- 시스템 중심 domain 및 repository 정의

### [matzip-support](https://github.com/The-Fellowship-of-the-matzip/mat.zip-back/tree/main/matzip-support)
- 공통 모듈 계층
- 전체 모듈이 공통적으로 사용하는 Util, custom exception 정의

## 🧑‍💻 팀원
<div align="center">
    
| [오찌](https://github.com/Ohzzi) | [오리](https://github.com/jinyoungchoi95) | [후니](https://github.com/jayjaehunchoi) |
| :-: | :-: | :-: |
| ![](https://github.com/Ohzzi.png?size=200) | ![](https://github.com/jinyoungchoi95.png?size=200) | ![](https://github.com/jayjaehunchoi.png?size=200) |
</div>
