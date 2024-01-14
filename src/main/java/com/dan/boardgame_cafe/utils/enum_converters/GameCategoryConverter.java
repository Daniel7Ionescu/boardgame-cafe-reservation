package com.dan.boardgame_cafe.utils.enum_converters;

import com.dan.boardgame_cafe.utils.enums.GameCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class GameCategoryConverter implements AttributeConverter<GameCategory, String> {

    @Override
    public String convertToDatabaseColumn(GameCategory attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getGameCategoryLabel();
    }

    @Override
    public GameCategory convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(GameCategory.values())
                .filter(gameCategory -> gameCategory.getGameCategoryLabel().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}