package flightassistant.repositories;

import flightassistant.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = "Select c.*, count(c.id)\n" +
            "from public.\"flight\" f, public.\"city\" c\n" +
            "where f.finish_point_id = c.id\n" +
            "group by 1\n" +
            "Order By count(f.finish_point_id) DESC;", nativeQuery = true)
    List<City> findPopularCity();
}
