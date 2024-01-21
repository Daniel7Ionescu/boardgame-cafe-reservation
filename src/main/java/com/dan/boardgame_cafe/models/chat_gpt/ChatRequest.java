package com.dan.boardgame_cafe.models.chat_gpt;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.dan.boardgame_cafe.utils.constants.BusinessConstants.CHATGPT_SYSTEM_INSTRUCTION;

@Data
public class ChatRequest {

    private String model;
    private List<Message> messages;

    public ChatRequest(String model, String prompt){
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", CHATGPT_SYSTEM_INSTRUCTION));
        this.messages.add(new Message("user", prompt));
    }
}
