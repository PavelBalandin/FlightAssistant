package flightassistant.controllers;

import flightassistant.domain.MyOrder;
import flightassistant.repositories.MyOrderRepository;
import flightassistant.service.MyOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return myOrder;
    }

    @PostMapping
    public MyOrder create(@RequestBody MyOrder myOrder) {

        return myOrderService.create(myOrder);
    }

    @PutMapping("{id}")
    public MyOrder update(@PathVariable("id") MyOrder myOrderFromDb, @RequestBody MyOrder myOrder) {
        BeanUtils.copyProperties(myOrder, myOrderFromDb, "id");
        return myOrderRepository.save(myOrderFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") MyOrder myOrder) {
        myOrderRepository.delete(myOrder);

    }

}
