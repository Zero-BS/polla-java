package org.zerobs.polla.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.Random.class)
class UserTest {
    private static final String BASE_URL = "/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @Value("Bearer ${bearer.token}")
    private String bearerToken;

    @Test
    void test_get_UserNotCreated_404() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken));

        mockMvc.perform(get(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_add_emptyBody_400() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken));

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_add_emptyUsername_400() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken));

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_add_emptyYearOfBirth_400() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken));

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"GreenDemogoblin_4678\",\n" +
                        "    \"gender\": \"FEMALE\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_add_necessaryFieldsGiven_201() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken));

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"GreenDemogoblin_4678\",\n" +
                        "    \"year_of_birth\": 1995,\n" +
                        "    \"gender\": \"FEMALE\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }
}