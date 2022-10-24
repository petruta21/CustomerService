package com.example.customer.customerservice.web;

import com.example.customer.customerservice.service.CustomerService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public Iterable<CustomerDTO> list() {
        return customerService.list();
    }

   @GetMapping("/findByLastName")
   public List<CustomerDTO> findByLastName(@Param("name") String name) {
       return customerService.findByLastName(name);
   }
}


