package com.example.customer.customerservice.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerPagingRepository extends PagingAndSortingRepository<Customer, Long> {

    List<Customer> findByCustomerName(String customerName, Pageable pageable);
}
