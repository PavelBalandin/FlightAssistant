package flightassistant.controllers;

import flightassistant.domain.Flight;
import flightassistant.repositories.FlightRepository;
import flightassistant.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("flight")
public class FlightController {
    private final FlightRepository flightRepository;
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightRepository flightRepository, FlightService flightService) {
        this.flightRepository = flightRepository;
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getFlightList() {
        return new ResponseEntity<>(flightRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") Flight flight) {
        if (flight == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        if (flightService.create(flight) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable("id") Flight flightFromDb, @RequestBody Flight flight) {
        if (flightFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BeanUtils.copyProperties(flight, flightFromDb, "id");
        flightRepository.save(flightFromDb);
        return new ResponseEntity<>(flightFromDb, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable("id") Flight flight) {
        if (flight == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        flightRepository.delete(flight);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
