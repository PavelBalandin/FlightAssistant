package flightassistant.repositories;

import flightassistant.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findFirstById(Long id);

    @Query(
            value = "Select count(f.\"id\") from public.\"flight\" f, public.\"pilot\" p, public.\"flight_pilot\" fp where f.\"id\" = fp.\"flight_id\" and p.\"id\" = fp.\"pilot_id\" and p.id = ? and f.\"date_time\" > current_date - interval '7 days'",
            nativeQuery = true)
    Long countFlyPilotForSevenDays(Long id);
}
