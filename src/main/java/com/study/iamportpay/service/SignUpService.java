package com.study.iamportpay.service;

import com.study.iamportpay.entity.Member;
import com.study.iamportpay.repository.MemberRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Builder
@Service
public class SignUpService implements UserDetailsService {
    // 멤버 레파지토리
    @Autowired
    MemberRepository memberRepository;

    // 회원가입
    public Member.rpJoinMember joinMember(Member.rqJoinMember rqJoinMember, PasswordEncoder passwordEncoder) { // 3. 파라미터로 컨트롤러에서 넘어온 DTO와 비밀번호 암호화 메소드를 받아온다.
        // 4. 넘어온 DTO를 Entity로 변환하면서 같이 넘어온 비밀번호 암호화 메소드를 파라미터로 넘겨준다.
        Member joinMember = rqJoinMember.toEntity(passwordEncoder);
        // 8. 변환된 Entity를 DB에 저장하고 저장한 값을 받아온다.
        Member member = memberRepository.save(joinMember);
        // 9. 조회된 Entity를 사용할 값들만 따로 빼서 만들어둔 DTO로 변환한다.
        Member.rpJoinMember rpJoinMember = new Member.rpJoinMember(member);
        // 10. 변환된 DTO를 반환한다.
        return rpJoinMember;
    }

    public Member.rpNickName memberNickname(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        Member.rpNickName rpNickName = new Member.rpNickName(member);
        return rpNickName;
    }

    // 로그인시 인증 방식 - Spring Security에서 DB로 변경한다.
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException { // 3. 파라미터로 컨트롤러에서 넘어온 아이디를 받아온다.
        // 4. 넘어온 아이디로 멤버를 조회하고 조회된 값을 받아온다.
        Member member = memberRepository.findByEmailId(emailId);
        // 5. 조회된 값이 있는지 체크한다.
        // 5-1. 조회된 값이 없는 경우
        if ( member == null ) {
            // 예외처리
            throw new UsernameNotFoundException(emailId);
        }
        // 5-2. 조회된 값이 있는 경우
        // 6. 조회된 멤버를 Spring Security에서 제공하는 User에 빌더를 통해 값을 넣어준뒤 SecurityConfig로 반환한다.
        return User.builder()
                .username(member.getEmailId())
                .password(member.getPwd())
                .roles(member.getRoleName().toString())
                .build();
    }
}
