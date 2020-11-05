package flightassistant.controllers;

import flightassistant.domain.Plane;
import flightassistant.repositories.PlaneRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<Plane> list() {
        return planeRepository.findAll();
    }

    @GetMapping("{id}")
    public Plane getOne(@PathVariable("id") Plane plane) {
        if (plane != null) {
            return plane;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Plane.class.getSimpleName() + " not found"
            );
        }
    }

    @PostMapping
    public Plane create(@RequestBody Plane plane) {
        return planeRepository.save(plane);
    }

    @PutMapping("{id}")
    public Plane update(@PathVariable("id") Plane planeFromDb, @RequestBody Plane plane) {
        try {
            BeanUtils.copyProperties(plane, planeFromDb, "id");
            return planeRepository.save(planeFromDb);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Plane.class.getSimpleName() + " not found"
            );
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Plane plane) {
        if (plane != null) {
            planeRepository.delete(plane);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, Plane.class.getSimpleName() + " not found"
            );
        }

    }


}
