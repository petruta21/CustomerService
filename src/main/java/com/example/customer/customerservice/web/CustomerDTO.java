package com.example.customer.customerservice.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Age is mandatory")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}