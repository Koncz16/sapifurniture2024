package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.ShoeCabinet;
import ro.sapientia.furniture.service.ShoeCabinetService;

import org.springframework.http.MediaType;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShoeCabinetController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ShoeCabinetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoeCabinetService shoeCabinetService;

    @Test
    public void testFindAllShoeCabinets() throws Exception {
        // Létrehozunk egy példányt a ShoeCabinet modelből
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.5);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(5);

        // Mockoljuk a szolgáltatás metódust, hogy visszaadja a listát
        when(shoeCabinetService.findAllShoeCabinets()).thenReturn(List.of(shoeCabinet));

        // Meghívjuk a GET kérés tesztelését
        this.mockMvc.perform(get("/shoe-cabinet/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(180.5)))
                .andExpect(jsonPath("$[0].material", is("Wood")));
    }
}
