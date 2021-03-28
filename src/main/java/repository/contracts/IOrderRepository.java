package repository.contracts;

import exceptions.OrderException;
import models.AvailableTime;
import models.Order;

import java.util.List;

public interface IOrderRepository {

    int create(Order order);
    Order update(Order order);
    void delete(int id) throws OrderException;
    Order getOrder(int id);
    List<Order> getOrders();
    List<AvailableTime> availableTimes(int restaurantId,String date);



}
