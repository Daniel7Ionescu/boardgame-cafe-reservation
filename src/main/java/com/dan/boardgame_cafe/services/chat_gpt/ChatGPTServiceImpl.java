package com.dan.boardgame_cafe.services.chat_gpt;

import com.dan.boardgame_cafe.models.chat_gpt.ChatRequest;
import com.dan.boardgame_cafe.models.chat_gpt.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${api.openai.url}")
    private String openaiURL;
    @Value("${api.openai.model}")
    private String openaiModel;
    private final WebClient webClient;

    public ChatGPTServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String webClientChatWithAI(String prompt) {
        log.info("Human just asked: " + prompt);
        ChatRequest chatRequest = new ChatRequest(openaiModel, prompt);
        ChatResponse chatResponse = webClient.post()
                .body(Mono.just(chatRequest), ChatRequest.class)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        if(chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()){
            log.info("No response from chatGPT");
            return "No result";
        }
        log.info("Successful response from chatGPT");

        return chatResponse.getChoices().get(0).getMessage().getContent();
    }
}