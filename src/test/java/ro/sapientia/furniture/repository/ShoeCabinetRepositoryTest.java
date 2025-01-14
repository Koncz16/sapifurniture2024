package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.ShoeCabinet;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class ShoeCabinetRepositoryTest {

    @Autowired
    private ShoeCabinetRepository shoeCabinetRepository;

    @Test
    public void testFindAllShoeCabinets() {
        List<ShoeCabinet> result = shoeCabinetRepository.findAll();
        Assertions.assertEquals(0, result.size(), "A ShoeCabinet táblában alapértelmezés szerint nem lehet adat.");
    }

    @Test
    public void testFindShoeCabinetById() {
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.5);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(5);

        ShoeCabinet savedShoeCabinet = shoeCabinetRepository.save(shoeCabinet);

        ShoeCabinet foundShoeCabinet = shoeCabinetRepository.findShoeCabinetById(savedShoeCabinet.getId());

        Assertions.assertNotNull(foundShoeCabinet, "Nem található a ShoeCabinet id alapján.");
        Assertions.assertEquals(savedShoeCabinet.getId(), foundShoeCabinet.getId(), "A megtalált id nem egyezik.");
        Assertions.assertEquals(savedShoeCabinet.getHeight(), foundShoeCabinet.getHeight(), "A magasság nem egyezik.");
    }

    @Test
    public void testCreateShoeCabinet() {
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.0);
        shoeCabinet.setWidth(80.0);
        shoeCabinet.setDepth(35.0);
        shoeCabinet.setMaterial("Metal");
        shoeCabinet.setShelvesCount(4);

        ShoeCabinet savedShoeCabinet = shoeCabinetRepository.save(shoeCabinet);

        Assertions.assertNotNull(savedShoeCabinet.getId(), "A ShoeCabinet nem lett elmentve, nincs ID.");
        Assertions.assertEquals(180.0, savedShoeCabinet.getHeight(), "A magasság nem egyezik.");
    }

    @Test
    public void testDeleteShoeCabinet() {
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(200.0);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(6);

        ShoeCabinet savedShoeCabinet = shoeCabinetRepository.save(shoeCabinet);

        shoeCabinetRepository.deleteById(savedShoeCabinet.getId());

        ShoeCabinet deletedShoeCabinet = shoeCabinetRepository.findShoeCabinetById(savedShoeCabinet.getId());
        Assertions.assertNull(deletedShoeCabinet, "A ShoeCabinet törlés után még mindig elérhető.");
    }
}
