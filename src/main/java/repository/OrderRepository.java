package repository;

import models.Order;
import repository.contracts.IOrderRepository;

import java.util.List;

public class OrderRepository implements IOrderRepository {
    @Override
    public int create(Order order) {
        return 0;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Order getOrder(int id) {
        return null;
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }
}
