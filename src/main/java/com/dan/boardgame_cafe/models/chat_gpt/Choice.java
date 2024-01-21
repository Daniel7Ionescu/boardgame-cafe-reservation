package com.dan.boardgame_cafe.models.chat_gpt;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Choice {

    private int index;
    private Message message;
}
