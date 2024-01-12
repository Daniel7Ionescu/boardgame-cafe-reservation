package com.dan.boardgame_cafe.services.customer;

import com.dan.boardgame_cafe.models.dtos.CustomerDTO;

public interface CustomerServiceValidation {

    void validateCustomerCreation(CustomerDTO customerDTO);
}
