package com.example.customer.customerservice.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
public class Customer implements Persistable<Long> {
    @Id
    @Column("id")
    private Long customerId;
    @Column("name")
    private String customerName;
    @Column("age")
    private Integer customerAge;

    @Transient
    @JsonIgnore
    private Boolean isInsert;

    @Override
    @JsonIgnore
    public Long getId() {
        return customerId;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return isInsert;
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
