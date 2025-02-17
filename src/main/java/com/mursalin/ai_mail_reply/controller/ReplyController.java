package com.mursalin.ai_mail_reply.controller;

import com.mursalin.ai_mail_reply.dto.MailRequest;
import com.mursalin.ai_mail_reply.service.ReplyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai-reply")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public String generateReply(@RequestBody MailRequest mailRequest) {
        return replyService.generateReply(mailRequest);
    }
}
