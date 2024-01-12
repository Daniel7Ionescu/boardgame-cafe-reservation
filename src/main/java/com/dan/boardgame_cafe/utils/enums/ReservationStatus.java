package com.dan.boardgame_cafe.utils.enums;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    PENDING("Pending"),
    APPROVED("Accepted"),
    REJECTED("Rejected"),
    COULD_NOT_SERVICE("Could not service");

    private final String reservationStatusLabel;

    ReservationStatus(String reservationStatusLabel) {
        this.reservationStatusLabel = reservationStatusLabel;
    }
}
