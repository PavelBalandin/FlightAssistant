package flightassistant.repositories;

import flightassistant.domain.Flight;
import flightassistant.domain.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {
    List<MyOrder> findAllByFlight(Flight flight);
}
