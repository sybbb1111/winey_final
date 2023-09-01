package com.green.winey_final.common.config.mail;

import com.green.winey_final.common.config.mail.model.MailDto;
import com.green.winey_final.common.config.redis.RedisService;
import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import static com.green.winey_final.common.config.security.model.ProviderType.LOCAL;

@PropertySource("classpath:application.yaml")
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisService redisService;

    private final UserRepository userRep;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade facade;




    //인증번호 생성
    private final String ePw = createKey();

    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String to)throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : " + ePw);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject(" winey 회원가입 인증 코드: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"greenwiney@naver.com")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }



    // 인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    /*
        메일 발송
        sendSimpleMessage의 매개변수로 들어온 to는 인증번호를 받을 메일주소
        MimeMessage 객체 안에 내가 전송할 메일의 내용을 담아준다.
        bean으로 등록해둔 javaMailSender 객체를 사용하여 이메일 send
     */

    public String sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try{
            javaMailSender.send(message);
            redisService.setValuesWithTimeout(ePw, to, 120000);// 메일 발송, 2분 유효
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw; // 메일로 보냈던 인증 코드를 서버로 리턴
    }

    //메일로 발송된 인증 번호로 유효한 인증 번호인지 확인
    public String verifyEmail(String key) throws ChangeSetPersister.NotFoundException {
        String memberEmail = redisService.getValues(key);
        if (memberEmail == null) {
//            throw new ChangeSetPersister.NotFoundException();
            return "0";
        }
        redisService.deleteValues(key);

        return "1"; //ePw
    }






    //여기서부터 로컬회원 비밀번호 찾기(임시비밀번호로 업데이트, 이메일 발송)

    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
    public MailDto createMailAndChangePassword(String email) {
        String str = getTempPassword();
        MailDto dto =  new MailDto();

        dto.setEmail(email);
        dto.setTitle("winey 임시 비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. winey 임시 비밀번호 안내 관련 이메일입니다.\n" +
                "회원님의 임시 비밀번호는 " + str + " 입니다.\n" +
                "로그인 후에 비밀번호를 변경해주세요.");
        updatePassword(str, email);
        return dto;
    }

    //로컬회원 - 임시 비밀번호로 업데이트
    public void updatePassword(String str, String email) {
        String pw = str;
        Long userId = userRep.findByProviderTypeAndEmail(LOCAL, email).getUserId();

        UserEntity userEntity = userRep.findById(userId).get();
        userEntity.setUpw(passwordEncoder.encode(pw));

        userRep.save(userEntity);


    }



    //랜덤함수로 임시 비밀번호 만들기
    public String getTempPassword() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        String str = "";

        //문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for(int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;

    }

    //메일보내기
    public void findPwMailSend(MailDto mailDto){
        log.info("임시 비밀번호 전송 완료");
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailDto.getEmail());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("greenwiney@naver.com");
        message.setReplyTo("greenwiney@naver.com");
        log.info("message" + message);
        javaMailSender.send(message);

    }
    //비밀번호 변경
    public void updatePassWord(String memberPassword) {
        Long userId = facade.getLoginUser().getUserId();
        UserEntity userEntity = userRep.findById(userId).get();
        userEntity.setUpw(passwordEncoder.encode(memberPassword));

        userRep.save(userEntity);
    }










}