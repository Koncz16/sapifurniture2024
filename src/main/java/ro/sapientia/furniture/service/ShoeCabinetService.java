package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.ShoeCabinet;
import ro.sapientia.furniture.repository.ShoeCabinetRepository;

import java.util.List;

@Service
public class ShoeCabinetService {
    private final ShoeCabinetRepository shoeCabinetRepository;

    public ShoeCabinetService(final ShoeCabinetRepository shoeCabinetRepository) {
        this.shoeCabinetRepository = shoeCabinetRepository;
    }

    public List<ShoeCabinet> findAllShoeCabinets() {
        return this.shoeCabinetRepository.findAll();
    }

    public ShoeCabinet findShoeCabinetById(final Long id) {
        return this.shoeCabinetRepository.findShoeCabinetById(id);
    }

    public ShoeCabinet create(ShoeCabinet shoeCabinet) {
        return this.shoeCabinetRepository.saveAndFlush(shoeCabinet);
    }

    public ShoeCabinet update(ShoeCabinet shoeCabinet) {
        return this.shoeCabinetRepository.saveAndFlush(shoeCabinet);
    }

    public void delete(Long id) {
        try {
            this.shoeCabinetRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
