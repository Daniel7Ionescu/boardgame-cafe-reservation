package com.dan.boardgame_cafe.services.openai;

import com.dan.boardgame_cafe.exceptions.openai.InvalidUserPromptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenAIValidationServiceImpl implements OpenAIValidationService {
    @Override
    public void validateUserPrompt(String prompt) {
        if(prompt.isBlank()){
            throw new InvalidUserPromptException("A message is required");
        }
        if(prompt.length() < 10 || prompt.length() > 200){
            throw new InvalidUserPromptException("Invalid prompt length");
        }
    }
}
