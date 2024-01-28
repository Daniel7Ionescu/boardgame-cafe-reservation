package com.dan.boardgame_cafe.utils.constants;

import lombok.Getter;

@Getter
public class BusinessConstants {

    private BusinessConstants(){}
    //will try to keep them in a separate txt/json file
    public static final Integer MINIMUM_AGE = 16;
    public static final Integer MAXIMUM_AGE = 90;
    public static final String OPENING_HOURS = "10:00";
    public static final String CLOSING_HOURS = "23:00";
    public static final Long MINIMUM_SESSION_MINUTES = 179L;

    public static final String VALID_NAME_REGEX = "^[a-zA-Z]+$";
    public static final String VALID_GAME_NAME_REGEX = "^[a-zA-Z0-9 ]+$";
    public static final String VALID_DATE_FORMAT = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

    public static final String CHATGPT_SYSTEM_INSTRUCTION = "Please answer using up to 100 tokens.";
}
