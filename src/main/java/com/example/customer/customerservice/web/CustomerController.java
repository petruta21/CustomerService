package com.example.customer.customerservice.web;

import com.example.customer.customerservice.service.CustomerService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) {
        CustomerDTO result = customerService.getCustomerById(id);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        } else {
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    @PostMapping("/customer")
    private Long create(@RequestBody CustomerDTO customer) {
        return customerService.saveOrUpdate(customer).getId();
    }

    @PutMapping("/{id}")
    private CustomerDTO update(@RequestBody CustomerDTO customer, @PathVariable("id") Long id) {
        return customerService.update(customer, id);
    }
}


