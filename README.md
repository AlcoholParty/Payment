# Payment
## 아임포트 결제 API
## 결제 API를 이용해서 실제 카카오페이로 테스트 결제까지 가능하게 기능을 구현
### 사용된 데이터베이스
### MySQL - soju

	CREATE DATABASE soju;
	USE soju;

### 사용된 테이블 : Member, Pay, Store
	
	CREATE TABLE Member(
		emailId VARCHAR(50) PRIMARY KEY,
		pwd VARCHAR(255) NOT NULL,
		name VARCHAR(10) NOT NULL,
		nickName VARCHAR(20) NOT NULL,
		birthDay DATE NOT NULL,
		gender VARCHAR(1) NOT NULL,
		phoneNumber VARCHAR(15) NOT NULL,
		address VARCHAR(100) NOT NULL,
		roleName VARCHAR(100) NOT NULL
	);
	
	CREATE TABLE Pay(
		uod VARCHAR(200) PRIMARY KEY,
		PGName VARCHAR(50) NOT NULL,
		payMethod VARCAHR(100) NOT NULL,
		itemNmae VARCHAR(50) NOT NULL,
		price INT NOT NULL,
		buyerEmail VARCHAR(50) NOT NULL,
		buyerName VARCHAR(10) NOT NULL,
		buyerTel VARCAHR(15) NOT NULL,
		buyerAddress VARCHAR(100) NOT NULL,
		buyerPostNumber VARCHAR(50)
	);
	
	CREATE TABLE Store(
		storeIdx INT PRIMARY KEY,
		goods VARCHAR(50),
		category VARCHAR(50),
		price INT NOT NULL,
		introduce VARCAHR(500),
		stock INT NOT NULL,
		goodsLike INT,
		itemName VARCHAR(50) NOT NULL
	);

## 수정 작업
### 데이터베이스
### 추가된 테이블 : Pay
	CREATE TABLE Pay(
		impUid VARCHAR(200) PRIMARY KEY,
		merchantUid VARCHAR(200),
		PGname VARCHAR(50), 
		payMethod VARCHAR(300),
		itemName VARCHAR(50),
		price INT,
		buyerEmail VARCHAR(50),
		buyerName VARCHAR(20),
		buyerTel VARCHAR(20),
		buyerAddress VARCHAR(50),
		buyerPostNum VARCHAR(50)
	);

### 변경된 내용
### 결제 이후 컨트롤러로 데이터를 전송
### 이후 컨트롤러에서 받은 정보들을 데이터베이스에 저장
	
