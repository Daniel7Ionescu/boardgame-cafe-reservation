package com.dan.boardgame_cafe.models.chat_gpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatResponse {

    private List<Choice> choices;
}
