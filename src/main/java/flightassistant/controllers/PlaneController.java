package flightassistant.controllers;

import flightassistant.domain.Plane;
import flightassistant.repositories.PlaneRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("plane")
public class PlaneController {
    private final PlaneRepository planeRepository;

    @Autowired
    public PlaneController(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Plane>> getPlaneList() {
        return new ResponseEntity<>(planeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Plane> getPlaneById(@PathVariable("id") Plane plane) {
        if (plane == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(plane, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Plane> createPlane(@RequestBody Plane plane) {
        planeRepository.save(plane);
        return new ResponseEntity<>(plane, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Plane> updatePlane(@PathVariable("id") Plane planeFromDb, @RequestBody Plane plane) {
        if (planeFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BeanUtils.copyProperties(plane, planeFromDb, "id");
        planeRepository.save(planeFromDb);
        return new ResponseEntity<>(planeFromDb, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Plane> deletePlane(@PathVariable("id") Plane plane) {
        if (plane == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        planeRepository.delete(plane);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
