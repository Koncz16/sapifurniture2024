package ro.sapientia.furniture.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ro.sapientia.furniture.model.ShoeCabinet;
import ro.sapientia.furniture.repository.ShoeCabinetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoeCabinetServiceTest {

    @Mock
    private ShoeCabinetRepository shoeCabinetRepository;

    @InjectMocks
    private ShoeCabinetService shoeCabinetService;

    public ShoeCabinetServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllShoeCabinets() {
        // Mockoljuk a repository-t, hogy visszaadja az üres listát
        List<ShoeCabinet> mockList = new ArrayList<>();
        Mockito.when(shoeCabinetRepository.findAll()).thenReturn(mockList);

        // Meghívjuk a szerviz metódust
        List<ShoeCabinet> result = shoeCabinetService.findAllShoeCabinets();

        // Ellenőrizzük, hogy a visszaadott lista üres
        Assertions.assertEquals(0, result.size(), "A visszaadott ShoeCabinets lista nem üres.");
    }

    @Test
    public void testFindShoeCabinetById() {
        // Létrehozunk egy új ShoeCabinet-et
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.5);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(5);

        // Mockoljuk a repository-t, hogy az adott id-hoz visszaadja a létrehozott objektumot
        Mockito.when(shoeCabinetRepository.findShoeCabinetById(shoeCabinet.getId())).thenReturn(shoeCabinet);

        // Meghívjuk a szerviz metódust
        ShoeCabinet foundShoeCabinet = shoeCabinetService.findShoeCabinetById(shoeCabinet.getId());

        // Ellenőrizzük, hogy a megtalált objektum megegyezik a mentett objektummal
        Assertions.assertNotNull(foundShoeCabinet, "Nem található a ShoeCabinet id alapján.");
        Assertions.assertEquals(shoeCabinet.getId(), foundShoeCabinet.getId(), "A megtalált id nem egyezik.");
        Assertions.assertEquals(shoeCabinet.getMaterial(), foundShoeCabinet.getMaterial(), "A material nem egyezik.");
    }

    @Test
    public void testCreateShoeCabinet() {
        // Létrehozunk egy új ShoeCabinet-et
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.0);
        shoeCabinet.setWidth(80.0);
        shoeCabinet.setDepth(35.0);
        shoeCabinet.setMaterial("Metal");
        shoeCabinet.setShelvesCount(4);

        // Mockoljuk a repository-t, hogy visszaadja az elmentett objektumot
        Mockito.when(shoeCabinetRepository.saveAndFlush(shoeCabinet)).thenReturn(shoeCabinet);

        // Meghívjuk a szerviz metódust
        ShoeCabinet savedShoeCabinet = shoeCabinetService.create(shoeCabinet);

        // Ellenőrizzük, hogy a mentett objektum nem null
        Assertions.assertNotNull(savedShoeCabinet, "A ShoeCabinet nem lett elmentve.");
        Assertions.assertEquals(shoeCabinet.getHeight(), savedShoeCabinet.getHeight(), "A magasság nem egyezik.");
    }

    @Test
    public void testUpdateShoeCabinet() {
        // Létrehozunk egy meglévő ShoeCabinet-et
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.0);
        shoeCabinet.setWidth(80.0);
        shoeCabinet.setDepth(35.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(4);

        // Mockoljuk a repository-t, hogy visszaadja a frissített objektumot
        Mockito.when(shoeCabinetRepository.saveAndFlush(shoeCabinet)).thenReturn(shoeCabinet);

        // Meghívjuk a szerviz metódust
        ShoeCabinet updatedShoeCabinet = shoeCabinetService.update(shoeCabinet);

        // Ellenőrizzük, hogy a frissített objektum nem null
        Assertions.assertNotNull(updatedShoeCabinet, "A ShoeCabinet nem lett frissítve.");
        Assertions.assertEquals(shoeCabinet.getMaterial(), updatedShoeCabinet.getMaterial(), "A material nem egyezik.");
    }

    @Test
    public void testDeleteShoeCabinet() {
        // Létrehozunk egy új ShoeCabinet-et
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(200.0);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(6);

        // Mockoljuk a repository-t, hogy az adott id-t törli
        Mockito.doNothing().when(shoeCabinetRepository).deleteById(shoeCabinet.getId());

        // Meghívjuk a szerviz metódust a törléshez
        shoeCabinetService.delete(shoeCabinet.getId());

        // Ellenőrizzük, hogy a törlés sikeres volt (mivel a mock nem dob hibát)
        Mockito.verify(shoeCabinetRepository, Mockito.times(1)).deleteById(shoeCabinet.getId());
    }
}
