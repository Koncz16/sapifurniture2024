package ro.sapientia.furniture.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.ShoeCabinet;
import ro.sapientia.furniture.repository.ShoeCabinetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShoeCabinetComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoeCabinetRepository shoeCabinetRepository;

    @Test
    public void testFindAllShoeCabinets() throws Exception {
        // Létrehozunk egy új ShoeCabinet objektumot és mentjük el
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.5);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(5);

        // Mentjük el az objektumot
        shoeCabinetRepository.save(shoeCabinet);

        // Lekérdezzük a mentett ShoeCabinet-et a MockMvc segítségével
        this.mockMvc.perform(get("/shoe-cabinet/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(180.5)))
                .andExpect(jsonPath("$[0].material", is("Wood")));
    }
}
