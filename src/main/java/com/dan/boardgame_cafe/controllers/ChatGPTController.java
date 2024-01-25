package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.services.chat_gpt.ChatGPTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gpt")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping
    public ResponseEntity<String> askAIOverlord(@RequestBody String prompt){
//        return ResponseEntity.ok(chatGPTService.webClientChatWithAI(prompt));
        return ResponseEntity.ok(chatGPTService.restTemplateChatWithAi(prompt));
    }
}
