package flightassistant.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import flightassistant.domain.Pilot;
import flightassistant.domain.Views;
import flightassistant.repositories.PilotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<Pilot> list() {
        return pilotRepository.findAll();
    }

    @GetMapping("{id}")
    public Pilot getOne(@PathVariable("id") Pilot pilot) {
        if (pilot != null) {
            return pilot;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Pilot.class.getSimpleName() + " not found"
            );
        }
    }

    @PostMapping
    @JsonView(Views.IdProperty.class)
    public Pilot create(@RequestBody Pilot pilot) {
        return pilotRepository.save(pilot);
    }

    @PutMapping("{id}")
    @JsonView(Views.IdProperty.class)
    public Pilot update(@PathVariable("id") Pilot pilotFromDb, @RequestBody Pilot pilot) {
        try {
            BeanUtils.copyProperties(pilot, pilotFromDb, "id");
            return pilotRepository.save(pilotFromDb);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Pilot.class.getSimpleName() + " not found"
            );
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Pilot pilot) {
        if (pilot != null) {
            pilotRepository.delete(pilot);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Pilot.class.getSimpleName() + " not found"
            );
        }

    }
}
