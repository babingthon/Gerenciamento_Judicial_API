package com.judicial.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.judicial.management.dto.AuthRequestDto;
import com.judicial.management.dto.LegalProcessDto;
import com.judicial.management.model.LegalProcess;
import com.judicial.management.model.enums.LegalProcessEnum;
import com.judicial.management.repository.LegalProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LegalProcessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        AuthRequestDto authRequest = new AuthRequestDto("admin", "password");
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        this.jwtToken = objectMapper.readTree(responseString).get("token").asText();
    }

    @Test
    @DisplayName("Should return status 400 when trying to create process with duplicate number")
    void Test_1() throws Exception {

        String numberExists = "0000001-11.2025.1.11.1111";
        LegalProcess processExisting = new LegalProcess();
        processExisting.setCaseNumber(numberExists);
        processExisting.setCourt("1º vara Cível");
        processExisting.setDistrict("Natal");
        processExisting.setSubject("Teste");
        processExisting.setStatus(LegalProcessEnum.ACTIVE);
        legalProcessRepository.save(processExisting);

        var requsetDto = new LegalProcessDto(
                numberExists,
                "2º vara",
                "Parnamirim",
                "Outros Testes",
                LegalProcessEnum.ACTIVE);

        mockMvc.perform(post("/api/v1/process")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requsetDto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message", is("There is already a registered process with the number: " + numberExists)));
    }

    @Test
    @DisplayName("Should return status 400 when trying to create process with invalid number format")
    void Test_2() throws Exception {

        var requestDto = new LegalProcessDto(
                "12345",
                "3º vara",
                "Natal",
                "Teste de formato",
                LegalProcessEnum.ACTIVE
        );

        mockMvc.perform(post("/api/v1/process")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("caseNumber: Invalid legalProcess number format."));
    }
}
