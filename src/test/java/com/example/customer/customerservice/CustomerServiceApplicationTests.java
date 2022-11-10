package com.example.customer.customerservice;

import com.example.customer.customerservice.persistence.CustomerRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration.properties")
@AutoConfigureMockMvc
class CustomerServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_create_one_customer() throws Exception {

        final File jsonFile = new ClassPathResource("json/new_user.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        ResultActions resultActions = this.mockMvc.perform(post("/customers/customer")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print());

        Long newId = Long.parseLong(resultActions.andReturn().getResponse().getContentAsString());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", newId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Marta"))
                .andExpect(jsonPath("$.age").value("99"))
                .andExpect(jsonPath("$.id").value(newId));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_not_create_one_customer_empty_fields() throws Exception {

        final File jsonFile = new ClassPathResource("json/new_user_empty.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/customers/customer")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_not_create_one_customer_empty_field_name() throws Exception {

        final File jsonFile = new ClassPathResource("json/new_user_empty_name.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/customers/customer")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_not_create_one_customer_empty_field_age() throws Exception {

        final File jsonFile = new ClassPathResource("json/new_user_empty_age.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/customers/customer")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void list_customers_thenStatus200() throws Exception {

        final File jsonFile = new ClassPathResource("json/customers_list.json").getFile();
        final String listCustomers = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(get("/customers/list")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(listCustomers))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_delete_one_customer_by_id() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/customers/{id}", 3))
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_update_one_customer_by_id() throws Exception {

        final File jsonFile = new ClassPathResource("json/update_customer.json").getFile();
        final String userToUpdate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Denis"))
                .andExpect(jsonPath("$.age").value("38"))
                .andExpect(jsonPath("$.id").value("1"));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(userToUpdate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Katia"))
                .andExpect(jsonPath("$.age").value("83"));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_not_update_one_customer_by_id_empty_fields() throws Exception {

        final File jsonFile = new ClassPathResource("json/update_customer_empty.json").getFile();
        final String userToUpdate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Denis"))
                .andExpect(jsonPath("$.age").value("38"))
                .andExpect(jsonPath("$.id").value("1"));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(userToUpdate))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_not_update_one_customer_by_id_empty_field_name() throws Exception {

        final File jsonFile = new ClassPathResource("json/update_customer_empty_name.json").getFile();
        final String userToUpdate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Denis"))
                .andExpect(jsonPath("$.age").value("38"))
                .andExpect(jsonPath("$.id").value("1"));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(userToUpdate))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_not_update_one_customer_by_id_empty_field_age() throws Exception {

        final File jsonFile = new ClassPathResource("json/update_customer_empty_age.json").getFile();
        final String userToUpdate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Denis"))
                .andExpect(jsonPath("$.age").value("38"))
                .andExpect(jsonPath("$.id").value("1"));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(userToUpdate))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}






