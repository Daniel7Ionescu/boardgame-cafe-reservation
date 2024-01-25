package com.dan.boardgame_cafe.services.chat_gpt;

import com.dan.boardgame_cafe.exceptions.openai.InvalidUserPromptException;
import com.dan.boardgame_cafe.models.chat_gpt.ChatRequest;
import com.dan.boardgame_cafe.models.chat_gpt.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${api.openai.url}")
    private String openaiURL;
    @Value("${api.openai.model}")
    private String openaiModel;
    private final ChatGPTValidationService chatGPTValidationService;
    private final RestTemplate chatgptRestTemplate;

    public ChatGPTServiceImpl(ChatGPTValidationService chatGPTValidationService, RestTemplate chatgptRestTemplate) {
        this.chatGPTValidationService = chatGPTValidationService;
        this.chatgptRestTemplate = chatgptRestTemplate;
    }

    /**
     * Using Rest Template posts chatRequest and maps the return to chatResponse     *
     * @param prompt the question from the user
     * @return the content as String, taken from chatResponse
     * @throws RestClientException     if all attempts to get a response fails
     * @throws ResourceAccessException if all attempts are exhausted and RestClientException is thrown
     */
    @Override
    @Retryable(maxAttempts = 2, retryFor = {RestClientException.class},
            noRetryFor = {InvalidUserPromptException.class},
            backoff = @Backoff(delay = 2000, multiplier = 2))
    public String restTemplateChatWithAi(String prompt) {
        chatGPTValidationService.validateUserPrompt(prompt);
        log.info("User just asked: " + prompt);
        ChatRequest chatRequest = new ChatRequest(openaiModel, prompt);
        ChatResponse chatResponse = chatgptRestTemplate.postForObject(openaiURL, chatRequest, ChatResponse.class);

        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            log.info("No response from chatGPT");
            return "No result";
        }

        return chatResponse.getChoices().get(0).getMessage().getContent();
    }
}