package com.dan.boardgame_cafe.utils.enum_converters;

import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class GameSessionStatusConverter implements AttributeConverter<GameSessionStatus, String> {
    @Override
    public String convertToDatabaseColumn(GameSessionStatus attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getSessionStatusLabel();
    }

    @Override
    public GameSessionStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(GameSessionStatus.values())
                .filter(gameSessionStatus -> gameSessionStatus.getSessionStatusLabel().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}