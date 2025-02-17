package com.mursalin.ai_mail_reply.service.impl;

import com.mursalin.ai_mail_reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class ReplyServiceImpl implements ReplyService {

    @Value("${gemini.api.key}")
    private String gemini_api_key;

    @Value("${gemini.api.url}")
    private String gemini_api_url;

    private final WebClient webClient;

    public ReplyServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    @Override
    public String generateReply(String mail) {
        return "";
    }
}
