package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.CustomerDTO;
import com.dan.boardgame_cafe.services.customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        return ResponseEntity.ok(customerService.createCustomer(customerDTO));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
