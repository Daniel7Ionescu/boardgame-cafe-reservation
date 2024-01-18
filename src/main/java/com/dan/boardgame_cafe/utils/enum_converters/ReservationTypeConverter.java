package com.dan.boardgame_cafe.utils.enum_converters;

import com.dan.boardgame_cafe.utils.enums.ReservationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReservationTypeConverter implements AttributeConverter<ReservationType, String> {
    @Override
    public String convertToDatabaseColumn(ReservationType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getReservationTypeLabel();
    }

    @Override
    public ReservationType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(ReservationType.values())
                .filter(reservationType -> reservationType.getReservationTypeLabel().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
