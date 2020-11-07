package flightassistant.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import flightassistant.domain.Pilot;
import flightassistant.domain.Views;
import flightassistant.repositories.PilotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pilot")
public class PilotController {
    private final PilotRepository pilotRepository;

    @Autowired
    public PilotController(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }

    @GetMapping
    public ResponseEntity<List<Pilot>> getPilotList() {
        return new ResponseEntity<>(pilotRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Pilot> getPilotById(@PathVariable("id") Pilot pilot) {
        if (pilot == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(pilot, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.IdProperty.class)
    public ResponseEntity<Pilot> createPilot(@RequestBody Pilot pilot) {
        pilotRepository.save(pilot);
        return new ResponseEntity<>(pilot, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @JsonView(Views.IdProperty.class)
    public ResponseEntity<Pilot> updatePilot(@PathVariable("id") Pilot pilotFromDb, @RequestBody Pilot pilot) {
        if (pilotFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BeanUtils.copyProperties(pilot, pilotFromDb, "id");
        pilotRepository.save(pilotFromDb);
        return new ResponseEntity<>(pilotFromDb, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Pilot> deletePilot(@PathVariable("id") Pilot pilot) {
        if (pilot == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        pilotRepository.delete(pilot);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
