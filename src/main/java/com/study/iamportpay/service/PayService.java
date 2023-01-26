package com.study.iamportpay.service;

import com.study.iamportpay.entity.Member;
import com.study.iamportpay.repository.MemberRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Builder
public class PayService {
    @Autowired
    MemberRepository memberRepository;
    //멤버 객체 정보 조회
    public Member.rpMemberAll findAll(String emailId){
        Member member = memberRepository.findByEmailId(emailId);
        Member.rpMemberAll rpMemberAll = new Member.rpMemberAll(member);
        return rpMemberAll;
    }

    //모든 상품 리스트로 끌어오기



}