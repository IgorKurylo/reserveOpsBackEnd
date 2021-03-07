package repository.contracts;

import models.Order;

import java.util.List;

public interface IOrderRepository {

    int create(Order order);
    Order update(Order order);
    void delete(int id);
    Order getOrder(int id);
    List<Order> getOrders();


}
