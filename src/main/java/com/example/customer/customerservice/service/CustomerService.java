package com.example.customer.customerservice.service;

import com.example.customer.customerservice.persistence.Customer;
import com.example.customer.customerservice.persistence.CustomerRepository;
import com.example.customer.customerservice.web.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> list() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).map(CustomerService::convertToCustomerDTO).collect(Collectors.toList());
      /*  List<CustomerDTO> resultList = new ArrayList<>();
        for (Customer c: customerRepository.findAll()) {
            CustomerDTO result = new CustomerDTO();
            result.setId(c.getId());
            result.setName(c.getCustomerName());
            result.setAge(c.getCustomerAge());
            resultList.add(result);
        }
        return resultList;*/
    }


    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<CustomerDTO> findByLastName(String name) {
        return customerRepository.findByLastName(name).stream().map(CustomerService::convertToCustomerDTO).collect(Collectors.toList());
    }

    private static CustomerDTO convertToCustomerDTO(Customer c) {
        CustomerDTO result = new CustomerDTO();
        result.setId(c.getId());
        result.setName(c.getCustomerName());
        result.setAge(c.getCustomerAge());
        return result;
    }
}
