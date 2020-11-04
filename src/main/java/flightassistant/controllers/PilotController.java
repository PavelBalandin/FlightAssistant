package flightassistant.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import flightassistant.domain.Pilot;
import flightassistant.domain.Views;
import flightassistant.repositories.PilotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @JsonView(Views.IdProperty.class)
    public List<Pilot> list() {
        return pilotRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.IdProperty.class)
    public Pilot getOne(@PathVariable("id") Pilot pilot) {
        return pilot;
    }

    @PostMapping
    @JsonView(Views.IdProperty.class)
    public Pilot create(@RequestBody Pilot pilot) {
        return pilotRepository.save(pilot);
    }

    @PutMapping("{id}")
    @JsonView(Views.IdProperty.class)
    public Pilot update(@PathVariable("id") Pilot pilotFromDb, @RequestBody Pilot pilot) {
        BeanUtils.copyProperties(pilot, pilotFromDb, "id");
        return pilotRepository.save(pilotFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Pilot pilot) {
        pilotRepository.delete(pilot);

    }
}
