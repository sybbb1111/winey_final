package com.green.winey_final.common.config.mail;

import com.green.winey_final.common.config.mail.model.MailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이메일 인증, 비밀번호 찾기")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class AccountController {
    private final EmailService emailService;

    @PostMapping("/mailConfirm")
    @ResponseBody
    @Operation(summary = "이메일로 인증코드 발송", description = "발송 성공 시 Response body에 인증코드가 뜹니당")
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }

    @PostMapping("/codeConfirm")
    @ResponseBody
    @Operation(summary = "이메일 인증번호 확인", description = "인증번호 일치 시 1, 불일치 시 0 리턴, 2분간만 유효")
    public String codeConfirm(@RequestParam String key) throws Exception{
        return emailService.verifyEmail(key);
    }

    @Transactional
    @PostMapping("/findpw")
    @Operation(summary = "비밀번호 찾기", description = "임시 비밀번호를 사용자 이메일로 발송")
    public String findPw(@RequestParam("memberEmail") String email) {
        MailDto dto = emailService.createMailAndChangePassword(email);
        emailService.findPwMailSend(dto);
        return "임시비밀번호 발송완료";

    }

    @PutMapping("/putpw")
    @ResponseBody
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경을 원할 시, 또는 임시 비밀번호로 로그인 이후 사용자가 다시 본인이 원하는 비밀번호로 변경할 때")
    public ResponseEntity<Integer> updatePassWord(String memberPassword){

        emailService.updatePassWord(memberPassword);

        return ResponseEntity.ok(1);

    }



}
