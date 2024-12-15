package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.ShoeCabinet;

public interface ShoeCabinetRepository extends JpaRepository<ShoeCabinet, Long> {
    ShoeCabinet findShoeCabinetById(Long id);
}
