package com.example.customer.customerservice.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "customer")
public class Customer implements Persistable<Long> {
    @Id
    @Column("id")
    private Long customerId;
    @Column("name")
    private String customerName;
    @Column("age")
    private Integer customerAge;

    @Override
    @JsonIgnore
    public Long getId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return customerId == null;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(Integer customerAge) {
        this.customerAge = customerAge;
    }
}
