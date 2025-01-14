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
        List<ShoeCabinet> mockList = new ArrayList<>();
        Mockito.when(shoeCabinetRepository.findAll()).thenReturn(mockList);

        List<ShoeCabinet> result = shoeCabinetService.findAllShoeCabinets();

        Assertions.assertEquals(0, result.size(), "A visszaadott ShoeCabinets lista nem üres.");
    }

    @Test
    public void testFindShoeCabinetById() {

        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.5);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(5);

        Mockito.when(shoeCabinetRepository.findShoeCabinetById(shoeCabinet.getId())).thenReturn(shoeCabinet);

        ShoeCabinet foundShoeCabinet = shoeCabinetService.findShoeCabinetById(shoeCabinet.getId());

        Assertions.assertNotNull(foundShoeCabinet, "Nem található a ShoeCabinet id alapján.");
        Assertions.assertEquals(shoeCabinet.getId(), foundShoeCabinet.getId(), "A megtalált id nem egyezik.");
        Assertions.assertEquals(shoeCabinet.getMaterial(), foundShoeCabinet.getMaterial(), "A material nem egyezik.");
    }

    @Test
    public void testCreateShoeCabinet() {
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.0);
        shoeCabinet.setWidth(80.0);
        shoeCabinet.setDepth(35.0);
        shoeCabinet.setMaterial("Metal");
        shoeCabinet.setShelvesCount(4);

        Mockito.when(shoeCabinetRepository.saveAndFlush(shoeCabinet)).thenReturn(shoeCabinet);

        ShoeCabinet savedShoeCabinet = shoeCabinetService.create(shoeCabinet);

        Assertions.assertNotNull(savedShoeCabinet, "A ShoeCabinet nem lett elmentve.");
        Assertions.assertEquals(shoeCabinet.getHeight(), savedShoeCabinet.getHeight(), "A magasság nem egyezik.");
    }

    @Test
    public void testUpdateShoeCabinet() {
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(180.0);
        shoeCabinet.setWidth(80.0);
        shoeCabinet.setDepth(35.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(4);

        Mockito.when(shoeCabinetRepository.saveAndFlush(shoeCabinet)).thenReturn(shoeCabinet);

        ShoeCabinet updatedShoeCabinet = shoeCabinetService.update(shoeCabinet);

        Assertions.assertNotNull(updatedShoeCabinet, "A ShoeCabinet nem lett frissítve.");
        Assertions.assertEquals(shoeCabinet.getMaterial(), updatedShoeCabinet.getMaterial(), "A material nem egyezik.");
    }

    @Test
    public void testDeleteShoeCabinet() {
        ShoeCabinet shoeCabinet = new ShoeCabinet();
        shoeCabinet.setHeight(200.0);
        shoeCabinet.setWidth(90.0);
        shoeCabinet.setDepth(40.0);
        shoeCabinet.setMaterial("Wood");
        shoeCabinet.setShelvesCount(6);

        Mockito.doNothing().when(shoeCabinetRepository).deleteById(shoeCabinet.getId());

        shoeCabinetService.delete(shoeCabinet.getId());

        Mockito.verify(shoeCabinetRepository, Mockito.times(1)).deleteById(shoeCabinet.getId());
    }
}
