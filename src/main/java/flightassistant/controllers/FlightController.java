package flightassistant.controllers;

import flightassistant.domain.Flight;
import flightassistant.repositories.FlightRepository;
import flightassistant.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("flight")
public class FlightController {
    private final FlightRepository flightRepository;
    private final FlightService flightService;

    public FlightController(FlightRepository flightRepository, FlightService flightService) {
        this.flightRepository = flightRepository;
        this.flightService = flightService;
    }

    @Autowired

    @GetMapping
    public List<Flight> list() {
        return flightRepository.findAll();
    }

    @GetMapping("{id}")
    public Flight getOne(@PathVariable("id") Flight flight) {
        if (flight != null) {
            return flight;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Flight.class.getSimpleName() + " not found"
            );
        }
    }

    @PostMapping
    public Flight create(@RequestBody Flight flight) {

        return flightService.create(flight);
    }

    @PutMapping("{id}")
    public Flight update(@PathVariable("id") Flight flightFromDb, @RequestBody Flight flight) {
        try {
            BeanUtils.copyProperties(flight, flightFromDb, "id");
            return flightRepository.save(flightFromDb);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Flight.class.getSimpleName() + " not found"
            );
        }
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Flight flight) {
        if (flight != null) {
            flightRepository.delete(flight);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Flight.class.getSimpleName() + " not found"
            );
        }

    }
}
