package com.example.customer.customerservice.service;

import com.example.customer.customerservice.persistence.Customer;
import com.example.customer.customerservice.persistence.CustomerPagingRepository;
import com.example.customer.customerservice.web.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {
    private final CustomerPagingRepository customerRepository;

    public CustomerService(CustomerPagingRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> list() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).map(CustomerService::convertToCustomerDTO).collect(Collectors.toList());
    }

    public CustomerDTO save(CustomerDTO customerDto) {
        return convertToCustomerDTO(customerRepository.save(convertToCustomer(customerDto)));
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerDTO> findByLastName(String name, Pageable pageable) {
        return customerRepository.findByCustomerName(name, pageable).stream().map(CustomerService::convertToCustomerDTO).collect(Collectors.toList());
    }

    public CustomerDTO saveOrUpdate(CustomerDTO customerDto) {
        return convertToCustomerDTO(customerRepository.save(convertToCustomer(customerDto)));
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(CustomerService::convertToCustomerDTO).orElse(null);
    }

    public CustomerDTO update(CustomerDTO customerDto, Long id) {
        Customer customer = new Customer();
        customer.setCustomerName(customerDto.getName());
        customer.setCustomerAge(customerDto.getAge());
        customer.setCustomerId(id);
        return convertToCustomerDTO(customerRepository.save(customer));
    }

    private Customer convertToCustomer(CustomerDTO customerDto) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDto.getId());
        customer.setCustomerName(customerDto.getName());
        customer.setCustomerAge(customerDto.getAge());
        return customer;
    }

    private static CustomerDTO convertToCustomerDTO(Customer c) {
        CustomerDTO result = new CustomerDTO();
        result.setId(c.getId());
        result.setName(c.getCustomerName());
        result.setAge(c.getCustomerAge());
        return result;
    }

    public List<CustomerDTO> listPaginated(Pageable pageable) {
        Page<Customer> pagedResult = customerRepository.findAll(pageable);
        return pagedResult.toList().stream().map(CustomerService::convertToCustomerDTO).collect(Collectors.toList());
    }
}
