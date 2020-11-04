package flightassistant.controllers;

import flightassistant.domain.Plane;
import flightassistant.repositories.PlaneRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Plane> list() {
        return planeRepository.findAll();
    }

    @GetMapping("{id}")
    public Plane getOne(@PathVariable("id") Plane plane) {
        return plane;
    }

    @PostMapping
    public Plane create(@RequestBody Plane plane) {
        return planeRepository.save(plane);
    }

    @PutMapping("{id}")
    public Plane update(@PathVariable("id") Plane planeFromDb, @RequestBody Plane plane) {
        BeanUtils.copyProperties(plane, planeFromDb, "id");
        return planeRepository.save(planeFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Plane plane) {
        planeRepository.delete(plane);

    }


}
