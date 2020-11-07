package flightassistant.controllers;

import flightassistant.domain.City;
import flightassistant.repositories.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<City>> getCityList() {
        return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getCityById(@PathVariable("id") City city) {
        if (city == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        cityRepository.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<City> updateCity(@PathVariable("id") City cityFromDb, @RequestBody City city) {
        if (cityFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BeanUtils.copyProperties(city, cityFromDb, "id");
        cityRepository.save(cityFromDb);
        return new ResponseEntity<>(cityFromDb, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<City> deleteCity(@PathVariable("id") City city) {
        if (city == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        cityRepository.delete(city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
