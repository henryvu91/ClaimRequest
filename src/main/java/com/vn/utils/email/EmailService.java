package com.vn.utils.email;

import com.vn.dto.view.ClaimEmailDTO;

import java.util.List;

public interface EmailService {
    public static final String EMAIL_TEMP = "/layout/email/emailTemplate";
//    public static final String APPROVED_CLAIM_EMAIL_TEMP = "/layout/email/approvedClaimEmail";
//    public static final String RETURNED_CLAIM_EMAIL_TEMP = "/layout/email/returnedClaimEmail";
//    public static final String REJECTED_CLAIM_EMAIL_TEMP = "/layout/email/rejectedClaimEmail";
//    public static final String APPROVED_CLAIM_EMAIL_TO_CLAIMER_TEMP = "/layout/email/approvedClaimEmailToClaimer";
void sendHtmlEmail(ClaimEmailDTO claimEmailDTO, List<String> to, String receiver, String mailTemplate, String url,String content);
}
