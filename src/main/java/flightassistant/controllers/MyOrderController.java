package flightassistant.controllers;

import flightassistant.domain.City;
import flightassistant.domain.MyOrder;
import flightassistant.repositories.MyOrderRepository;
import flightassistant.service.MyOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("order")
public class MyOrderController {
    private final MyOrderRepository myOrderRepository;
    private final MyOrderService myOrderService;

    @Autowired
    public MyOrderController(MyOrderRepository myOrderRepository, MyOrderService myOrderService) {
        this.myOrderRepository = myOrderRepository;
        this.myOrderService = myOrderService;
    }

    @GetMapping
    public List<MyOrder> list() {
        return myOrderRepository.findAll();
    }

    @GetMapping("{id}")
    public MyOrder getOne(@PathVariable("id") MyOrder myOrder) {
        if (myOrder != null) {
            return myOrder;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MyOrder.class.getSimpleName() + " not found"
            );
        }
    }

    @PostMapping
    public MyOrder create(@RequestBody MyOrder myOrder) {

        return myOrderService.create(myOrder);
    }

    @PutMapping("{id}")
    public MyOrder update(@PathVariable("id") MyOrder myOrderFromDb, @RequestBody MyOrder myOrder) {
        try {
            BeanUtils.copyProperties(myOrder, myOrderFromDb, "id");
            return myOrderRepository.save(myOrderFromDb);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MyOrder.class.getSimpleName() + " not found"
            );
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") MyOrder myOrder) {
        if (myOrder != null) {
            myOrderRepository.delete(myOrder);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MyOrder.class.getSimpleName() + " not found"
            );
        }

    }

}
