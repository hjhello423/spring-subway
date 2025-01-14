# jwp-subway-path

# jwp-cart

# 1단계 - 지하철 정보 관리 기능

## 미션 PR 링크

* https://github.com/next-step/spring-subway/pull/1

## 미션 소개

- 이번 미션은 지하철 노선도라는 복잡한 요구사항을 가진 웹 애플리케이션을 구현하는 것 입니다.
- 제공되는 뼈대코드(간단한 지하철 역과 노선 CRUD)를 바탕으로 노선에 역을 등록하고 제거하는 기능을 객체지향적으로 설계하고 구현하는 것을 목표로 합니다.
- 비즈니스 규칙을 검증하는 테스트를 각각 구현합니다. 테스트를 이용하여 요구사항을 만족하는지를 확인합니다.
- 작성한 테스트를 통해 애플리케이션을 실행시키고 브라우저를 띄워서 확인하지 않아도 정상 동작 여부를 확인할 수 있습니다.

## 요구사항

API 요구사항

- API 설계는 HTTP 명세와 리소스 특성에 맞도록 직접 설계합니다. 비즈니스 규칙을 반드시 지켜서 구현합니다.
- 노선 조회, 노선 목록 조회 API의 응답에는 노선에 포함된 역을 순서대로 배치된 정보가 포함되어야 합니다.

### API 기능 목록

- [x] 노선에 역 등록 API 신규 구현
    - [x] 지하철 노선에 구간을 등록하는 기능을 구현
    - [x] 새로운 구간의 상행역은 해당 노선에 등록되어있는 하행 종점역이어야 한다.
    - [x] 노선에 이미 등록되어있는 역은 새로운 구간의 하행역이 될 수 없다.
    - [x] 새로운 구간 등록 시 위 조건에 부합하지 않는 경우 에러 처리한다.
- [x] 노선에 역 제거 API 신규 구현
    - [x] 지하철 노선에 구간을 제거하는 기능 구현
    - [x] 지하철 노선에 등록된 역 중 하행 종점역만 제거할 수 있다. 즉, 마지막 구간만 제거할 수 있다.
    - [x] 새로운 구간 제거시 위 조건에 부합하지 않는 경우 에러 처리한다.
- [x] 노선 조회 API 수정
- [x] 노선 목록 조회 API 수정

### 프로그래밍 요구사항

- 미션 구현 순서를 지키며 구현합니다.
- 결과적으로 API 레벨의 통합 테스트와 세부 요구사항을 검증하는 단위 테스트가 작성됩니다.

### 미션 구현 순서

- API 레벨의 통합 테스트를 구현하여 내가 구현할 API의 큰 그림을 그리기
- 비즈니스 규칙을 만족하는 설계를 진행하기
- 설계한 요구사항을 검증할 수 있는 작은 단위의 테스트를 작성하기
- 작은 단위의 테스트를 만족시키는 코드를 구현하기

## 비즈니스 규칙

### 용어 설명

- 기본 뼈대코드에서 제공되고 있는 지하철 역과 노선이 있습니다.
- 지하철 (상행 방향)역과 (하행 방향)역 사이의 연결 정보(길이 등)를 구간이라고 합니다.
- 지하철 구간의 모음으로 구간에 포함된 지하철 역의 연결 정보를 노선이라고 합니다.
- 노선의 방향은 상행과 하행이 있습니다.
- 상행 방향의 첫번째 역은 상행 종점이라고 합니다.
- 하행 방향의 첫번째 역은 하행 종점으라고 합니다.

---
