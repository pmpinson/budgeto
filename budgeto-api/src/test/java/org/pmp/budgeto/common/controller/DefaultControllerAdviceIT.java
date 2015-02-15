package org.pmp.budgeto.common.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainNotFoundException;
import org.pmp.budgeto.common.domain.DomainValidationError;
import org.pmp.budgeto.common.domain.DomainValidationException;
import org.pmp.budgeto.domain.account.Account;
import org.pmp.budgeto.test.config.WebITConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebITConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
public class DefaultControllerAdviceIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void handleException() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/defaultControllerAdviceTestController/exception").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unknown Error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("unknown"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("msg of null objet"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionType").value("NullPointerException"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros", Matchers.hasSize(0)));
    }

    @Test
    public void handleDomainException() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/defaultControllerAdviceTestController/domainException").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Domain Error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("server"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("msg of domain error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionType").value("DomainException"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros", Matchers.hasSize(0)));
    }

    @Test
    public void handleDomainValidationException() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/defaultControllerAdviceTestController/domainValidationException").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("validation"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("msg of domain validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionType").value("DomainValidationException"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].field", Matchers.equalTo("firstField")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].errors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].errors", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].errors", Matchers.contains("may not be empty", "may have 2 values")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[1].field", Matchers.equalTo("secondField")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[1].errors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[1].errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[1].errors", Matchers.contains("may not be null")));
    }

    @Test
    public void handleDomainConflictException() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/defaultControllerAdviceTestController/domainConflictException").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Conflict error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("conflict"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("msg of domain conflict error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionType").value("DomainConflictException"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].field", Matchers.equalTo("firstField")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].errors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros[0].errors", Matchers.contains("a conflict")));
    }

    @Test
    public void handleDomainNoFoundException() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/defaultControllerAdviceTestController/domainNotFoundException").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Entity not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("notfound"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("msg of not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionType").value("DomainNotFoundException"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros", Matchers.hasSize(0)));
    }

    @RestController
    @RequestMapping(value = "defaultControllerAdviceTestController")
    public static class DefaultControllerAdviceTestController {

        @RequestMapping(value = "exception", method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public List<Account> exception() throws Exception {
            throw new NullPointerException("msg of null objet");
        }

        @RequestMapping(value = "domainException", method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public List<Account> domainException() throws Exception {
            throw new DomainException("msg of domain error");
        }

        @RequestMapping(value = "domainValidationException", method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public List<Account> domainValidationException() throws Exception {
            throw new DomainValidationException("msg of domain validation error", new DomainValidationError("firstField", "may not be empty", "may have 2 values"), new DomainValidationError("secondField", "may not be null"));
        }

        @RequestMapping(value = "domainConflictException", method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public List<Account> domainConflictException() throws Exception {
            throw new DomainConflictException("msg of domain conflict error", new DomainValidationError("firstField", "a conflict"));
        }

        @RequestMapping(value = "domainNotFoundException", method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public List<Account> domainNotFoundException() throws Exception {
            throw new DomainNotFoundException("msg of not found");
        }

    }

}
