package com.dan.boardgame_cafe.services.customer;

import com.dan.boardgame_cafe.models.dtos.CustomerDTO;
import com.dan.boardgame_cafe.models.entities.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers();
    Customer retrieveCustomerById(Long id);
}
