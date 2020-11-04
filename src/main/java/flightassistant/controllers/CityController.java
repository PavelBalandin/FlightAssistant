package flightassistant.controllers;

import flightassistant.domain.City;
import flightassistant.repositories.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return city;
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
        BeanUtils.copyProperties(city, cityFromDb, "id");
        return cityRepository.save(cityFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") City city) {
        cityRepository.delete(city);

    }
}
