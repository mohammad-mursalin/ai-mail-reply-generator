package com.mursalin.ai_mail_reply.service;

import com.mursalin.ai_mail_reply.dto.MailRequest;

public interface ReplyService {
    String generateReply(MailRequest mailRequest);
}
