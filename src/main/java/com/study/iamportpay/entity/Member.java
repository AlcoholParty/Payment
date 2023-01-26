package com.study.iamportpay.entity;


import com.study.iamportpay.constant.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter // getter 어노테이션
@Setter // setter 어노테이션
@NoArgsConstructor // 파라미터가 없는 기본 생성자 어노테이션
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 어노테이션
@Builder // 빌더 어노테이션 - 빌더를 통해 해당 객체의 필드 값을 재생성 한다.
@ToString // 객체를 불러올때 주솟값이 아닌 String 타입으로 변경해주는 어노테이션
@Entity(name="Member") // Entity 어노테이션 - 괄호안에는 테이블명과 똑같이 작성한다.
public class Member {
    @Id // 기본키 어노테이션 - 기본키 설정 (PRIMARY KEY)
    @Column(length = 50) // 컬럼 어노테이션 - 기본키 제외 나머지 컬럼 설정 - 기본키랑 같이 쓰이면 기본키의 설정값들을 잡아줄 수 있다.
    private String emailId;

    @Column(length = 255, nullable = false)
    private String pwd;

    @Column(length = 10, nullable = false)
    private String name;

    // length = 길이, unique = (기본값)false:유니크 해제 / true:유니크 설정, nullable = (기본값)true:눌값 허용 / false:눌값 불가
    @Column(length = 20, unique = true, nullable = false)
    private String nickname;

    @Column(length = 10, nullable = false)
    private String birthday;

    @Column(length = 6, nullable = false)
    private String gender;

    @Column(length = 15, unique = true, nullable = false)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String studyType;

    @Column(length = 10, nullable = false)
    private String platform;

    @Column(length = 100, nullable = false)
    @Enumerated(EnumType.STRING) // Enum을 가져와 String 타입으로 사용한다.
    private Role roleName;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DTO 구역

    // 회원가입 Request DTO
    @Getter // getter 어노테이션
    @Setter // setter 어노테이션
    @NoArgsConstructor // 파라미터가 없는 기본 생성자 어노테이션
    @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 어노테이션
    @Builder // 빌더 사용 어노테이션
    @ToString // 객체를 불러올때 주솟값이 아닌 String 타입으로 변경해주는 어노테이션
    public static class rqJoinMember {
        private String emailId;
        private String pwd;
        private String name;
        private String nickname;
        private String birthday;
        private String gender;
        private String phoneNumber;
        private String address;
        private String studyType;
        private String platform;
        // Spring Security 권한 설정
        private Role roleName;

        // DTO를 Entity로 변환 (빌더 방식)
        public Member toEntity(PasswordEncoder passwordEncoder) { // 5. 파라미터로 서비스에서 넘어온 비밀번호 암호화 메소드를 받아온다.
            // 6. 비밀번호 암호화
            String enPassword = passwordEncoder.encode(pwd);
            // 7. 변환된 Entity를 반환한다.
            return Member.builder()
                    .emailId(emailId)
                    .pwd(enPassword) // 암호화된 비밀번호 저장
                    .name(name)
                    .nickname(nickname)
                    .birthday(birthday)
                    .gender(gender)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .studyType(studyType)
                    .platform(platform)
                    .roleName(Role.USER) // Spring Security 권한에 USER로 설정
                    .build();
        }
    }

    // 회원가입 Response DTO
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rpJoinMember {
        private String nickname;
        private Role roleName;

        // Entity를 DTO로 변환 (생성자 방식)
        public rpJoinMember(Member member) {
            this.nickname = member.getNickname();
            this.roleName = member.getRoleName();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rqNickname {
        private String emailId;

        // DTO를 Entity로 변환 (빌더 방식)
        public Member toEntity() {
            return Member.builder()
                    .emailId(emailId)
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rpNickName {
        private String nickname;

        // Entity를 DTO로 변환 (생성자 방식)
        public rpNickName(Member member) {
            this.nickname = member.getNickname();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rpMemberAll{
        private String emailId;
        private String name;
        private String birthday;
        private String phoneNumber;
        private String address;

        public rpMemberAll(Member member){
            this.emailId = member.getEmailId();
            this.name = member.getName();
            this.phoneNumber = member.getPhoneNumber();
            this.address = member.getAddress();
            this.birthday = member.getBirthday();
        }

    }
}
