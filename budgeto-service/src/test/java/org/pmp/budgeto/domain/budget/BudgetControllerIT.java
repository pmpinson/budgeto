package org.pmp.budgeto.domain.budget;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pmp.budgeto.test.config.WebITConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@ContextConfiguration(classes = {WebITConfig.class, BudgetConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
public class BudgetControllerIT {

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {

        budgetHelper.init();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void findAll() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/budget").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='budget1')].name").value("budget1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='budget1')].note").value("a note on first"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='budget2')].name").value("budget2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='budget2')].note").value("a note on second"));
    }

    @Test
    public void add() throws Exception {

        Budget object = new Budget().setName("mynew").setNote("the note");

        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/budget").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(object)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/budget").accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='mynew')].name").value("mynew"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.name=='mynew')].note").value("the note"));
    }

}
