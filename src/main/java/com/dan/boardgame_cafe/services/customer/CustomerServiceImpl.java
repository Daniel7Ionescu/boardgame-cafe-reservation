package com.dan.boardgame_cafe.services.customer;

import com.dan.boardgame_cafe.models.dtos.CustomerDTO;
import com.dan.boardgame_cafe.models.entities.Customer;
import com.dan.boardgame_cafe.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CustomerServiceValidation customerServiceValidation;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, CustomerServiceValidation customerServiceValidation) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.customerServiceValidation = customerServiceValidation;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerServiceValidation.validateCustomerCreation(customerDTO);

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer {} : {} inserted", savedCustomer.getLastName(), savedCustomer.getId());

        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .toList();
    }

    @Override
    public Customer retrieveCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow();
    }
}
