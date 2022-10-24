package com.example.customer.customerservice.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("select * from customer where name = :lName")
    List<Customer> findByLastName(@Param("lName") String name);

}
