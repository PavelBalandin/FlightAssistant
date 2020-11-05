package flightassistant.controllers;

import flightassistant.domain.City;
import flightassistant.repositories.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("city")
public class CityController {
    private final CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public List<City> list() {
        return cityRepository.findAll();
    }

    @GetMapping("{id}")
    public City getOne(@PathVariable("id") City city) {
        if (city != null) {
            return city;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, City.class.getSimpleName() + " not found"
            );
        }
    }

    @PostMapping
    public City create(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("{id}")
    public City update(
            @PathVariable("id") City cityFromDb,
            @RequestBody City city
    ) {
        try {
            BeanUtils.copyProperties(city, cityFromDb, "id");
            return cityRepository.save(cityFromDb);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, City.class.getSimpleName() + " not found"
            );
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") City city) {
        if (city != null) {
            cityRepository.delete(city);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, City.class.getSimpleName() + " not found"
            );
        }

    }
}
