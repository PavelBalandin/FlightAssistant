package flightassistant.service;

import flightassistant.domain.Flight;
import flightassistant.domain.MyOrder;
import flightassistant.repositories.FlightRepository;
import flightassistant.repositories.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyOrderService {
    private final MyOrderRepository myOrderRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public MyOrderService(MyOrderRepository myOrderRepository, FlightRepository flightRepository) {
        this.myOrderRepository = myOrderRepository;
        this.flightRepository = flightRepository;
    }

    public MyOrder create(MyOrder myOrder) {
        Flight flight = flightRepository.findFirstById(myOrder.getFlight().getId());
        if (myOrderRepository.findAllByFlight(myOrder.getFlight()).size() < flight.getNumberOfSeats()) {
            myOrderRepository.save(myOrder);
            return myOrder;
        } else {
            return null;
        }
    }

}
