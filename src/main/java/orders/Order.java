package orders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// https://dzone.com/articles/creating-a-rest-api-with-java-and-spring
public class Order {
    private static final AtomicInteger count = new AtomicInteger(0);

    private final long id;
    private String content;
    private long costs;
    private boolean status;

    private static List<Order> orders = new ArrayList();

    public static Order newOrder(String content, long costs, boolean status) {
        Order o = new Order(count.incrementAndGet(), content, costs, status);
        //orders.add(new java.lang.ref.WeakReference(o));
        orders.add(o);
        return o;
    }

    public static List getOrders() {
        return orders;
    }

    public static Order findById(long id){
        for(Order o : orders) {
            if(o.getId() == id){
                return o;
            }
        }
        return null;
    }

    public static boolean updateById(long id, Order updatedOrder){
        Order o = findById(id);
        if(o != null) {
            o.setContent(updatedOrder.getContent());
            o.setCosts(updatedOrder.getCosts());
            o.setStatus(updatedOrder.getStatus());
            return true;
        }
        return false;
    }

    public static boolean deleteById(long id){
        Order o = findById(id);
        if(o != null) {
            orders.remove(o);
            return true;
        }
        return false;
    }

    /*default constructor is used by com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `orders.Order` (no Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
    at [Source: (PushbackInputStream); line: 1, column: 2] */
    public Order() {
        this.id = 0; //create order objects with invalid ids
    }

    //use class function newOrder to create a order
    private Order(long id, String content, long costs, boolean status) {
        this.id = id;
        this.content = content;
        this.costs = costs;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean getStatus() {
        return status;
    }

    public long getCosts() {
        return costs;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCosts(long costs) {
        this.costs = costs;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", costs=" + costs +
                ", status=" + status +
                '}';
    }
}
