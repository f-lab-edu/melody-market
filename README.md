# melody-market
멜론티켓 서비스를 예시로한, 음악과 관련된 공연 티켓팅 서비스입니다

### 프로젝트 목표
* 객체지향 원리를 최대한 적용하여 유지보수 및 확장에 용이한 코드 구현하고자 합니다.
* 프로젝트 진행 과정에서 코드리뷰를 통해 코드의 정확성과 품질 향상시킵니다.
* 적재적소에 맞는 기술을 사용하여 더 나은 성능을 가지도록 구현하고자 합니다.
---
### 프로젝트 구조도
![melody-market구조도-1차 001](https://github.com/f-lab-edu/melody-market/assets/82919411/3089dc7a-ccf0-4a7b-a11a-9533e7d9f3ef)
---
### 문제 해결 사례
* 회원가입의 유저ID 중복 체크를 서로 다른 유저가 동시에 요청한다면 생기는 문제
  * 해당 프로젝트에 중복 체크 동시 실행 시 모두 성공하는 문제가 발생했습니다.
  * 다른 플랫폼은? 컬리, 줌 인터넷에서도 실행해 보았으나 중복 체크는 성공 -> 회원가입 완료 버튼 시 에러 발생합니다
  * [이 문제를 해결하기 위해 이렇게 이런 방법을 사용했습니다.](https://github.com/f-lab-edu/melody-market/wiki/SBI)
