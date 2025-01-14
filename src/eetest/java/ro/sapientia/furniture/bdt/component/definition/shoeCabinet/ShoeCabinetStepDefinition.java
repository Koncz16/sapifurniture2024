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
import org.springframework.web.reactive.function.client.WebClient;

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
@TestPropertySource(locations = "classpath:cotest.properties")
@ContextConfiguration
public class ShoeCabinetStepDefinition {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

       @Given("^that we have the following shoe cabinets:$")
    public void that_we_have_the_following_shoe_cabinets(final DataTable shoeCabinets) throws Throwable {
        for (final Map<String, String> data : shoeCabinets.asMaps(String.class, String.class)) {
            ShoeCabinet shoeCabinet = new ShoeCabinet();
            shoeCabinet.setHeight(Double.parseDouble(data.get("height")));
            shoeCabinet.setWidth(Double.parseDouble(data.get("width")));
            shoeCabinet.setDepth(Double.parseDouble(data.get("depth")));
            shoeCabinet.setMaterial(data.get("material"));
            shoeCabinet.setShelvesCount(Integer.parseInt(data.get("shelves_count")));
            entityManager.persist(shoeCabinet);
        }
        entityManager.flush();
    }

    @When("^I invoke the shoe cabinet all endpoint$")
    public void I_invoke_the_shoe_cabinet_all_endpoint() throws Throwable {
    }

    @Then("^I should get the height \"([^\"]*)\" for the position \"([^\"]*)\"$")
    public void I_should_get_result_in_shoe_cabinet_list(final String height, final String position) throws Throwable {
        WebClient webClient = WebClient.create();
        webClient.get().uri("/shoecabinet/all") // The endpoint being tested
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.toEntityList(ShoeCabinet.class)) // Converts the response to a list
                .flatMapIterable(entity -> entity.getBody()) // Works with each shoe cabinet item
                .elementAt(Integer.parseInt(position)) // Access the element at the specified position
                .doOnNext(sc -> {
                    assert sc != null;
                    assert sc.getHeight() == Double.parseDouble(height);
                });
    }

    @After
    public void cleanup() {
        entityManager.getEntityManager().createQuery("delete from shoe_cabinet ").executeUpdate();
        entityManager.flush();
    }
}
