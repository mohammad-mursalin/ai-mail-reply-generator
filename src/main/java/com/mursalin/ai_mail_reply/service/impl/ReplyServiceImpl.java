package com.mursalin.ai_mail_reply.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mursalin.ai_mail_reply.dto.MailRequest;
import com.mursalin.ai_mail_reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
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
    public String generateReply(MailRequest mailRequest) {

        String prompt = generatePrompt(mailRequest).toString();

        Map<String, Object> requestBody =
                Map.of("content", new Object[] {
                    Map.of("parts", new Object[] {
                        Map.of("text", mailRequest.getMailBody())
                    })
                });

        String response = webClient.post()
                .uri(gemini_api_url + gemini_api_key)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return extractReplyFromResponse(response);
    }

    private String extractReplyFromResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);
            return node.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "exception occurred during extracting reply : " + e.getMessage();
        }
    }

    private StringBuilder generatePrompt(MailRequest mailRequest) {

        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email reply for hte following email content. Please don't generate a subject line. Use tone ");

        if(mailRequest.getTone() == null)
            prompt.append("professional");
        else
            prompt.append(mailRequest.getTone());

        prompt.append("original email : ").append(mailRequest.getMailBody());

        return prompt;
    }
}
