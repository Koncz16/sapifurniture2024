package ro.sapientia.furniture.bdt.component.definition;

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

@CucumberContextConfiguration
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

    @Given("^that we have the following shoe cabinets$")
    public void that_we_have_the_following_shoe_cabinets(final DataTable shoeCabinets) throws Throwable {
        for (Map<String, String> shoeCabinet : shoeCabinets.asMaps(String.class, String.class)) {
            ShoeCabinet shoeCabinetEntity = new ShoeCabinet();
            shoeCabinetEntity.setMaterial(shoeCabinet.get("material"));
            shoeCabinetEntity.setHeight(Integer.parseInt(shoeCabinet.get("height")));
            shoeCabinetEntity.setWidth(Integer.parseInt(shoeCabinet.get("width")));
            shoeCabinetEntity.setDepth(Integer.parseInt(shoeCabinet.get("depth")));
            entityManager.persist(shoeCabinetEntity);
        }
        entityManager.flush();
    }

    @When("^I invoke the shoe cabinet all endpoint$")
    public void I_invoke_the_shoe_cabinet_all_endpoint() throws Throwable {
        mockMvc.perform(get("/shoecabinet/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Then("^I should get the material \"([^\"]*)\" for the position \"([^\"]*)\"$")
    public void I_should_get_the_material_for_the_position(final String material, final String position) throws Throwable {
        mockMvc.perform(get("/shoecabinet/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[" + position + "].material", is(material)));
    }

    @Then("^I should get the dimensions (\\d+)x(\\d+)x(\\d+) for the position \"([^\"]*)\"$")
    public void I_should_get_the_dimensions_for_the_position(final int height, final int width, final int depth, final String position) throws Throwable {
        mockMvc.perform(get("/shoecabinet/all")
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
