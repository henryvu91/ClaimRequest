package com.vn.utils.email;

import com.vn.dto.view.ClaimEmailDTO;

import java.util.List;

public interface EmailService {
    String EMAIL_TEMP = "/layout/email/emailTemplate";

void sendHtmlEmail(ClaimEmailDTO claimEmailDTO, List<String> to, String receiver, String mailTemplate, String url,String content);
}
