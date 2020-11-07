package flightassistant.service;

import flightassistant.domain.Flight;
import flightassistant.domain.Pilot;
import flightassistant.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight create(Flight flight) {
        Set<Pilot> pilots = flight.getPilots();
        boolean flag = true;
        for (Pilot pilot : pilots) {
            if (flightRepository.countFlyPilotForSevenDays(pilot.getId()) >= 4) {
                flag = false;
            }
        }

        if (flag) {
            flight.setDateTime(LocalDateTime.now());
            flightRepository.save(flight);
            return flight;
        } else {
            return null;
        }
    }
}
