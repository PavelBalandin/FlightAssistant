package flightassistant.service;

import flightassistant.domain.Flight;
import flightassistant.domain.MyOrder;
import flightassistant.domain.Pilot;
import flightassistant.repositories.FlightRepository;
import flightassistant.repositories.MyOrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final MyOrderRepository myOrderRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    public FlightService(FlightRepository flightRepository, MyOrderRepository myOrderRepository) {
        this.flightRepository = flightRepository;
        this.myOrderRepository = myOrderRepository;
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

    public void notifyClient(Flight flight) {
        List<MyOrder> listOrder = myOrderRepository.findAllByFlight(flight);
        for (MyOrder order:listOrder) {
            String massage = "Dear " + order.getName() + "\n" +
                             "Your flight was canceled due to unforeseen circumstances \n" +
                             "Our apologize.";
            mailSender.send(order.getAddress(), "FlyUp", massage);
        }

    }

}
