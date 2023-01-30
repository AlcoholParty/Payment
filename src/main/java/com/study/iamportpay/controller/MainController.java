package com.study.iamportpay.controller;


import com.study.iamportpay.entity.Member;
import com.study.iamportpay.entity.Pay;
import com.study.iamportpay.entity.PayRequest;
import com.study.iamportpay.entity.Store;
import com.study.iamportpay.repository.MemberRepository;
import com.study.iamportpay.repository.PayRepository;
import com.study.iamportpay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    PayService payService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PayRepository payRepository;

    //결제가 완료 되었을때 들어갈 정보들
    private Member.rpMemberAll payMember;
    private Store payStore;
    private Pay resPay;

    // 메인 페이지
    @GetMapping(value = "/")
    public String main() {
        return "Main";
    }

    // 로그인 페이지
    @GetMapping("/loginform") // required = false - 해당 필드가 URL 파라미터에 존재하지 않아도 에러가 발생하지 않는다.
    public String loginform(@RequestParam(value = "error", required = false) String error, // URL 파라미터로 넘어오는 에러 체크값이 있을 경우 받는다.
                            @RequestParam(value = "errorMsg", required = false) String errorMsg, // URL 파라미터로 넘어오는 에러 메세지가 있을 경우 받는다.
                            Model model) {
        // 에러 체크값을 모델로 바인딩 한다.
        model.addAttribute("error", error);
        // 에러 메세지를 모델로 바인딩 한다.
        model.addAttribute("errorMsg", errorMsg);
        return "SignUp/LoginForm";
    }

    //결제 페이지(어떤거 살지 고르는 페이지)
//    @GetMapping("/payform")
//    public String payPage(Model model, Principal principal){
//        String name = principal.getName();
//        Member.rpMemberAll member = payService.findAll(name);
//        //로그인이 되어있는 정보에서 모든 정보를 끌어옴
//        //이후 모델에 넣고 값을 던져줌
//        model.addAttribute("member", member);
//        return "Pay/PayForm";
//    }

    @GetMapping("/payform")
    public String payform(){
        //결제 항목들을 model객체에 넣어서 리스트로 넘기면 될듯
        return "Pay/PayForm";
    }

    //결제를 하는 페이지
    @GetMapping("/pay")
    public String pay(Model model, Store store, Principal principal, Pay pay){
        String name = principal.getName();
        Member.rpMemberAll member = payService.findAll(name);
        //로그인이 되어있는 정보에서 모든 정보를 끌어옴
        //이후 모델에 넣고 값을 던져줌
        model.addAttribute("member", member);
        //스토어 객체를 스토어에 있는 모든 정보를 끌고와야댐
        Store realStore = store;
        model.addAttribute("store", realStore);
        model.addAttribute("pay", pay.getPGName());
        return "Pay/Pay";
    }

    //결제 완료 정보를 얻어올 엔드포인트
    @RequestMapping(value = {"/pay/complete"}, method = {RequestMethod.POST})
    @ResponseBody
    public String payComplete(PayRequest payRequest, Member member, Store store, Pay pay){
        //payRequestVO가 값이 비어있다면 no
        //값이 비어있지않다면 yes를 반환한다.
        String res = "no";
        System.out.println(payRequest.getImp_uid().toString());
        Member.rpMemberAll resMember = payService.findAll(member.getEmailId());
        //나중에 db에 여러가지 정보들이 들어갔을때 불러오기
        Store resStore = store;
        if(payRequest.getImp_uid() != null && payRequest.getMerchant_uid() != null){
            //값이 잘 들어와있다면 resMember, resStore를
            //payMember, payStore에 저장
            payMember = resMember;
            payStore = resStore;
            //pay로 받아온 pay클래스안에 정보들을 저장
            pay.setPayMethod("card");
            pay.setImpUid(payRequest.getImp_uid());
            pay.setMerchantUid(payRequest.getMerchant_uid());
            pay.setPrice(payStore.getPrice());
            pay.setBuyerName(payMember.getName());
            pay.setBuyerEmail(payMember.getEmailId());
            pay.setBuyerAddress(payMember.getAddress());
            pay.setBuyerTel(payMember.getPhoneNumber());
            pay.setItemName(payStore.getItemName());
            //실제 정보를 resPay 객체로 저장하고 그 정보들을 db에 저장
            resPay = pay;
            //만약 resPay 객체안에 정보들이 들어가지않으면 결제실패 라고 넘겨주기;
            if(resPay == null){
                res = "no";
            }
            payRepository.save(resPay);
            res = "yes";
        }
        return res;
    }

    //결제 완료 확인후 값을 db에 저장
    @GetMapping(value = "/payclear")
    public String payClear(){
        //db에 결제정보를 저장한다
        System.out.println("결제 정보 저장 완료");
        return "Pay/PayForm";
    }


}
