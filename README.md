# Auto Notion To Tistory (ANTT)

많은 개발자들이 블로그를 운영합니다. 티스토리, 벨로그, 네이버 블로그, 노션 등등
전 두 개의 블로그 노션과 티스토리를 운영하며 노션에 글을 쓰고 티스토리에 자동 업로드하는 방식으로 운영했습니다.

그러나 이것은 매우 소모적인 방식이고 개발자답지 못하다고 생각했습니다. 그래서 이것을 해결할 수 있는 Auto Notion To Tistory 줄여서 ANTT 를 기획하게 되었습니다.

# Stack
front-end : 크롬 확장 프로그램 / JavaScript

back-end : Spring Boot

using tool : Selenium, Tistory API

![image](https://github.com/ChoBeomHee/Tistory-Auto-Upload-from-Notion/assets/68563167/178101d0-c60a-4972-a1ca-5b6c4837a4a1)

크롬 확장 프로그램에 내장된 자바스크립트 코드에서 Spring Boot 서버에 HTTP 요청을 보내면 Spring Boot에서 노션 페이지를 크롤링하고 티스토리에 자동 업로드 해줍니다.

# 작동 예시
![녹화_2023_12_31_19_06_14_492](https://github.com/ChoBeomHee/Tistory-Auto-Upload-from-Notion/assets/68563167/87a19c06-89f9-485c-a045-1112db584aa3)


확장 프로그램에서 URL과 카테고리를 선택하면 자동으로 셀레니움이 실행되어 마우스는 움직이지 않지만 자동으로 업로드가 진행됩니다. 새롭게 열리는 창이 신경쓰이는 사람들을 위해, 작동 예시에서는 작동 과정을 보이기 위해 화면이 크게 작동됐지만 실제론 아주 작은 사이즈로 작동하도록 하였습니다.
