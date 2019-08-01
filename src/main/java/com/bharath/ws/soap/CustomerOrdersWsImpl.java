package com.bharath.ws.soap;

import com.bharath.ws.trainings.*;
import org.apache.cxf.feature.Features;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Features(features = "org.apache.cxf.ext.logging.LoggingFeature")
public class CustomerOrdersWsImpl implements CustomerOrdersPortType {

    private Map<BigInteger, List<Order>> customerOrders = new HashMap<>();
    private int currentId;

    public CustomerOrdersWsImpl() {
        init();
    }

    public void init() {
        List<Order> orders = new ArrayList<>();

        Order order = new Order();
        order.setId(BigInteger.valueOf(1));

        List<Product> products = order.getProduct();

        Product product = new Product();

        product.setId("1");
        product.setDescription("IPhone");
        product.setQuantity(BigInteger.valueOf(3));
        products.add(product);

        order.getProduct().add(product);
        orders.add(order);

        customerOrders.put(BigInteger.valueOf(++currentId), orders);
    }

    @Override
    public GetOrdersResponse getOrders(GetOrdersRequest request) {
        BigInteger customerId = request.getCustomerId();
        List<Order> orders = customerOrders.get(customerId);

        GetOrdersResponse response = new GetOrdersResponse();
        response.getOrder().addAll(orders);

        return response;
    }

    @Override
    public CreateOrdersResponse createOrders(CreateOrdersRequest request) {
        BigInteger customerId = request.getCustomerId();
        Order order = request.getOrder();

        List<Order> orders = customerOrders.get(customerId);
        orders.add(order);

        CreateOrdersResponse response = new CreateOrdersResponse();
        response.setResult(true);

        return response;
    }

    @Override
    public DeleteOrdersResponse deleteOrders(DeleteOrdersRequest request) {
        BigInteger customerId = request.getCustomerId();
        List<Order> orders = customerOrders.get(customerId);
        for(Order order : orders){
            orders.remove(order);
        }

        DeleteOrdersResponse response = new DeleteOrdersResponse();
        response.setResult(true);
        return response;
    }
}
