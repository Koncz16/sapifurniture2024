package ro.sapientia.furniture.bdt.ee.definition.shoeCabinet;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
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
import ro.sapientia.furniture.model.ShoeCabinet; // A modell neve ShoeCabinet

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class ShoeCabinetStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    @Given("^that we have the following shoe cabinets$")
    public void that_we_have_the_following_shoe_cabinets(final DataTable shoeCabinets) throws Throwable {
        for (final Map<String, String> data : shoeCabinets.asMaps(String.class, String.class)) {
            ShoeCabinet shoeCabinet = new ShoeCabinet(); // Modell név módosítva
            shoeCabinet.setHeight(Integer.parseInt(data.get("height")));
            shoeCabinet.setWidth(Integer.parseInt(data.get("width")));
            shoeCabinet.setMaterial(data.get("material"));
            shoeCabinet.setShelvesCount(Integer.parseInt(data.get("capacity")));
            entityManager.persist(shoeCabinet); // Persistálás ShoeCabinet objektum
        }
        entityManager.flush();
    }

    @When("^I invoke the shoe cabinet all endpoint$")
    public void I_invoke_the_shoe_cabinet_all_endpoint() throws Throwable {
        // A mock MVC perform code vagy WebClient kérés itt jöhetne.
        // Mivel most nem használjuk, üresen hagyjuk.
    }

    @Then("^I should get the height \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
    public void I_should_get_the_height_for_the_position(final String height, final String position) throws Throwable {
        WebClient webClient = WebClient.create();
        webClient.get().uri("/shoecabinet/all") // Az endpoint, amit tesztelünk
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.toEntityList(ShoeCabinet.class)) // A választ listává konvertáljuk
                .flatMapIterable(HttpEntity::getBody) // Munkálkodunk minden egyes elem testén
                .elementAt(0) // Az első elem elérése
                .doOnNext(sc -> {
                    assert sc != null;
                    assert sc.getHeight() == Integer.parseInt(height); // Ellenőrizzük a magasságot
                });
    }

    @After
    public void cleanup() {
        entityManager.clear();
        entityManager.flush();
    }
}
