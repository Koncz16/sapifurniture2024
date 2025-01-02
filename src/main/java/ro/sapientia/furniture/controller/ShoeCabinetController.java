package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.ShoeCabinet;
import ro.sapientia.furniture.service.ShoeCabinetService;

import java.util.List;

@RestController
@RequestMapping("/shoe-cabinet")
public class ShoeCabinetController {
    private final ShoeCabinetService shoeCabinetService;

    public ShoeCabinetController(final ShoeCabinetService shoeCabinetService) {
        this.shoeCabinetService = shoeCabinetService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoeCabinet>> getAllShoeCabinets() {
        final List<ShoeCabinet> shoeCabinets = shoeCabinetService.findAllShoeCabinets();
        return new ResponseEntity<>(shoeCabinets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoeCabinet> getShoeCabinetById(@PathVariable("id") Long id) {
        final ShoeCabinet shoeCabinet = shoeCabinetService.findShoeCabinetById(id);
        return shoeCabinet != null ? new ResponseEntity<>(shoeCabinet, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<ShoeCabinet> addShoeCabinet(@RequestBody ShoeCabinet shoeCabinet) {
        final ShoeCabinet persistentShoeCabinet = shoeCabinetService.create(shoeCabinet);
        return new ResponseEntity<>(persistentShoeCabinet, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ShoeCabinet> updateShoeCabinet(@RequestBody ShoeCabinet shoeCabinet) {
        final ShoeCabinet persistentShoeCabinet = shoeCabinetService.update(shoeCabinet);
        return new ResponseEntity<>(persistentShoeCabinet, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShoeCabinetById(@PathVariable("id") Long id) {
        shoeCabinetService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
