package com.dan.boardgame_cafe.services.customer;

import com.dan.boardgame_cafe.exceptions.customer.CustomerBelowMinimumAgeException;
import com.dan.boardgame_cafe.exceptions.customer.CustomerEmailAlreadyExistsException;
import com.dan.boardgame_cafe.models.dtos.CustomerDTO;
import com.dan.boardgame_cafe.models.entities.Customer;
import com.dan.boardgame_cafe.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Component
public class CustomerServiceValidationImpl implements CustomerServiceValidation {

    private final CustomerRepository customerRepository;
    private final Integer MINIMUM_AGE = 14;

    public CustomerServiceValidationImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void validateCustomerCreation(CustomerDTO customerDTO) {
        LocalDate dateNow = LocalDate.now();
        if (Period.between(customerDTO.getDob(), dateNow).getYears() < MINIMUM_AGE) {
            throw new CustomerBelowMinimumAgeException("You are not old enough to register");
        }

        Customer customer = customerRepository.findByEmail(customerDTO.getEmail());
        if(customer != null){
            throw new CustomerEmailAlreadyExistsException("A customer with this email already exists");
        }
    }
}
