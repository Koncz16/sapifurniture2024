package ro.sapientia.furniture.bdt.component.definition.shoeCabinet;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.model.ShoeCabinet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:contest.properties")
@ContextConfiguration
public class ShoeCabinetStepDefinition {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    @Given("that we have the following shoe cabinets:")
    public void that_we_have_the_following_shoe_cabinets(io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> shoeCabinet : dataTable.asMaps(String.class, String.class)) {
            ShoeCabinet shoeCabinetEntity = new ShoeCabinet();
            shoeCabinetEntity.setMaterial(shoeCabinet.get("material"));
            shoeCabinetEntity.setHeight(Double.parseDouble(shoeCabinet.get("height"))); // Módosított típus (double)
            shoeCabinetEntity.setWidth(Double.parseDouble(shoeCabinet.get("width")));   // Módosított típus (double)
            shoeCabinetEntity.setDepth(Double.parseDouble(shoeCabinet.get("depth")));   // Módosított típus (double)
            shoeCabinetEntity.setShelvesCount(Integer.parseInt(shoeCabinet.get("shelvesCount"))); // Hozzáadott shelvesCount mező
            entityManager.persist(shoeCabinetEntity);
        }
        entityManager.flush();
    }
    

    @When("^I invoke the shoe cabinet all endpoint$")
    public void I_invoke_the_shoe_cabinet_all_endpoint() throws Throwable {
        mockMvc.perform(get("/shoe-cabinet/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Then("I should get the shelvesCount {string} for the position {string}")
    public void i_should_get_the_shelves_count_for_the_position(String shelvesCount, String position) throws Exception {
        mockMvc.perform(get("/shoe-cabinet/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[" + position + "].shelvesCount", is(Integer.parseInt(shelvesCount))));
    }
    

    @Then("^I should get the dimensions (\\d+)x(\\d+)x(\\d+) for the position \"([^\"]*)\"$")
    public void I_should_get_the_dimensions_for_the_position(final int height, final int width, final int depth, final String position) throws Throwable {
        mockMvc.perform(get("/shoe-cabinet/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[" + position + "].height", is(height)))
                .andExpect(jsonPath("$[" + position + "].width", is(width)))
                .andExpect(jsonPath("$[" + position + "].depth", is(depth)));
    }

    @After
    public void cleanup() {
        entityManager.getEntityManager().createQuery("delete from shoe_cabinet ").executeUpdate();
        entityManager.flush();
    }
}
