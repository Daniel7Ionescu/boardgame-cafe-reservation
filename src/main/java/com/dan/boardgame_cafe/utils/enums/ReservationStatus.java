package com.dan.boardgame_cafe.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    SERVICED("Serviced"),
    REJECTED("Rejected"),
    NO_SHOW("No-show");

    private final String reservationStatusLabel;

    ReservationStatus(String reservationStatusLabel) {
        this.reservationStatusLabel = reservationStatusLabel;
    }
}
