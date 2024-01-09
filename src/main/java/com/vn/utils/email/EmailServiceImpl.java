package com.vn.utils.email;

import com.vn.dto.view.ClaimEmailDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.hostpage}")
    private String hostPage;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendHtmlEmail(ClaimEmailDTO claimEmailDTO, List<String> toList, String receiver, String mailTemplate, String url,String content) {
        try {

            Context context = getContext(claimEmailDTO,receiver,url,content);

            String text = templateEngine.process(mailTemplate,context);

            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(fromEmail);
            helper.setSubject(createSubject(claimEmailDTO));
            helper.setTo(toList.toArray(new String[0]));
            helper.setText(text,true);
            emailSender.send(message);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }


    private Context getContext(ClaimEmailDTO claimEmailDTO, String receiver, String url,String content) {
        String staffName = claimEmailDTO.getStaffName();
        Integer staffId = claimEmailDTO.getStaffId();
        String projectName = claimEmailDTO.getProjectName();

        Context context = new Context();
        context.setVariable("receiver", receiver);
        context.setVariable("staffName",staffName);
        context.setVariable("staffId",staffId);
        context.setVariable("projectName",projectName);
        context.setVariable("content", content);
        context.setVariable("url",hostPage+ url);
        return context;
    }

    //    Method to create subject for email
    private String createSubject(ClaimEmailDTO claimEmailDTO){
        return "Claim Request for "+claimEmailDTO.getProjectName()+" "+claimEmailDTO.getStaffName()+"-"+claimEmailDTO.getStaffId();
    }

    private MimeMessage getMimeMessage(){
        return emailSender.createMimeMessage();
    }
}
