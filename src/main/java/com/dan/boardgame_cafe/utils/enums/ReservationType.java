package com.dan.boardgame_cafe.utils.enums;

import lombok.Getter;

@Getter
public enum ReservationType {
    STANDARD("Standard"),
    JOIN_EVENT("Join Event"),
    CREATE_EVENT("Create Event");

    private final String reservationTypeLabel;

    ReservationType(String reservationTypeLabel) {
        this.reservationTypeLabel = reservationTypeLabel;
    }
}
