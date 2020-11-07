package flightassistant.controllers;

import flightassistant.domain.MyOrder;
import flightassistant.repositories.MyOrderRepository;
import flightassistant.service.MyOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MyOrder>> getOrderList() {
        return new ResponseEntity<>(myOrderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MyOrder> getOrderById(@PathVariable("id") MyOrder myOrder) {
        if (myOrder == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MyOrder> createOrder(@RequestBody MyOrder myOrder) {
        if (myOrderService.create(myOrder) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<MyOrder> updateOrder(@PathVariable("id") MyOrder myOrderFromDb, @RequestBody MyOrder myOrder) {
        if (myOrderFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BeanUtils.copyProperties(myOrder, myOrderFromDb, "id");
        myOrderRepository.save(myOrderFromDb);
        return new ResponseEntity<>(myOrderFromDb, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MyOrder> deleteOrder(@PathVariable("id") MyOrder myOrder) {
        if (myOrder == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        myOrderRepository.delete(myOrder);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
