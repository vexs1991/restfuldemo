package orders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/order", produces = "application/json")
public class OrdersController {
    private static final String template = "Hello, %s!";

/*    @RequestMapping("/greeting")
    public Order greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return Order.newOrder(String.format(template, name), 100, true);

    }*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrders() {
        //Order.newOrder("bla", 100, true); //temp test
        return new ResponseEntity<>(Order.getOrders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = Order.newOrder(order.getContent(), order.getCosts(), order.getStatus());
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> findOrderById(@PathVariable Long id) {
        Optional<Order> order = Optional.ofNullable(Order.findById(id));
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean wasDeleted = Order.deleteById(id);
        HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(responseStatus);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        boolean wasUpdated = Order.updateById(id, updatedOrder);

        if (wasUpdated) {
            return new ResponseEntity<>(Order.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
