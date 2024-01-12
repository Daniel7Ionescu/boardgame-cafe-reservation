package com.dan.boardgame_cafe.utils.enum_converters;

import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReservationStatusConverter implements AttributeConverter<ReservationStatus, String> {
    @Override
    public String convertToDatabaseColumn(ReservationStatus attribute) {
        if(attribute == null){
            return  null;
        }

        return attribute.getReservationStatusLabel();
    }

    @Override
    public ReservationStatus convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }

        return Stream.of(ReservationStatus.values())
                .filter(s -> s.getReservationStatusLabel().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}