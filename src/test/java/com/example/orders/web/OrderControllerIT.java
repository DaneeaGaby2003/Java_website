package com.example.orders.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // desactiva filtros de Security para estas pruebas
@ActiveProfiles("test")
class OrderControllerIT {

    @Autowired
    MockMvc mvc;

    @Test
    void create_and_list() throws Exception {
        String body = """
      {"customerEmail":"demo@meli.com","amount":199.99,"currency":"MXN",
       "items":[{"sku":"SKU-1","quantity":1}]}
      """;

        mvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        mvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(content().string(not("")));
    }

    @Test
    void create_fails_validation() throws Exception {
        String invalid = """
      {"customerEmail":"","amount":0,"currency":"","items":[]}
      """;

        mvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }
}
