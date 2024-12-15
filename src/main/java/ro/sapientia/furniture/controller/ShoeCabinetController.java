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

    @GetMapping("/find/{id}")
    public ResponseEntity<ShoeCabinet> getShoeCabinetById(@PathVariable("id") Long id) {
        final ShoeCabinet shoeCabinet = shoeCabinetService.findShoeCabinetById(id);
        return new ResponseEntity<>(shoeCabinet, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ShoeCabinet> addShoeCabinet(@RequestBody ShoeCabinet shoeCabinet) {
        final ShoeCabinet persistentShoeCabinet = shoeCabinetService.create(shoeCabinet);
        return new ResponseEntity<>(persistentShoeCabinet, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ShoeCabinet> updateShoeCabinet(@RequestBody ShoeCabinet shoeCabinet) {
        final ShoeCabinet persistentShoeCabinet = shoeCabinetService.update(shoeCabinet);
        return new ResponseEntity<>(persistentShoeCabinet, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteShoeCabinetById(@PathVariable("id") Long id) {
        shoeCabinetService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
