package com.dan.boardgame_cafe.utils.enum_converters;

import com.dan.boardgame_cafe.utils.enums.GameSessionType;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class GameSessionTypeConverter implements AttributeConverter<GameSessionType, String> {
    @Override
    public String convertToDatabaseColumn(GameSessionType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getGameSessionTypeLabel();
    }

    @Override
    public GameSessionType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(GameSessionType.values())
                .filter(gameSessionType -> gameSessionType.getGameSessionTypeLabel().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
