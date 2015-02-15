package org.pmp.budgeto.domain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pmp.budgeto.common.controller.DefaultControllerAdvice;
import org.pmp.budgeto.test.config.WebITConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebITConfig.class, AccountConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
public class AccountControllerIT {

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {

        accountHelper.init();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void findAll() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/account").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='account1')].name").value("account1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='account1')].note").value("a first account"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='account2')].name").value("account2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='account2')].note").value("a second account"));
    }

    @Test
    public void findNull() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/account/accountXXXX").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Entity not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("notfound"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("account with name accountXXXX not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionType").value("DomainNotFoundException"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validationErros", Matchers.hasSize(0)));
    }

    @Test
    public void find() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/account/account1").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("account1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.note").value("a first account"));
    }

    @Test
    public void add() throws Exception {

        Account object = new Account().setName("mynew account").setNote("the note on account");

        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/account").contentType(DefaultControllerAdvice.JSON_CONTENT_TYPE).content(mapper.writeValueAsString(object)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/account").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='mynew account')].name").value("mynew account"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='mynew account')].note").value("the note on account"));
    }

    @Test
    public void operations() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/account/account3/operations").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.label=='operation 1')].label").value("operation 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.label=='operation 1')].date").value("2015-02-26T00:00:00.000Z"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.label=='ope2')].label").value("ope2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.label=='ope2')].date").value("2015-02-27T00:00:00.000Z"));
    }

}
