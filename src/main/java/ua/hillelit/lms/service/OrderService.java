package ua.hillelit.lms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hillelit.lms.dto.OrderDto;
import ua.hillelit.lms.dto.OrderInfoDto;
import ua.hillelit.lms.dto.OrderItemDto;
import ua.hillelit.lms.dto.ProductDto;
import ua.hillelit.lms.model.Order;
import ua.hillelit.lms.model.OrderItem;
import ua.hillelit.lms.model.Product;
import ua.hillelit.lms.repository.OrderItemRepository;
import ua.hillelit.lms.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * {@link OrderService}
 *
 * @author Dmytro Trotsenko on 1/28/23
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ObjectMapper objectMapper;

    /**
     * Add new Order to the database
     *
     * @param id order id
     * @return OrderDto
     */
    public OrderDto createOrder(Integer id) {
        var orderDto = new OrderDto(id, LocalDate.now(), 0.0);
        Order order = objectMapper.convertValue(orderDto, Order.class);
        orderRepository.save(order);
        orderDto.setId(order.getId());
        log.info("Order id:{} was created", orderDto.getId());
        return orderDto;
    }

    /**
     * Add product to the order, update order price and save in database
     *
     * @param orderDto   order
     * @param productDto product
     * @param count      quantity
     * @return OrderItemDto
     */
    public OrderItemDto addProductToOrder(OrderDto orderDto, ProductDto productDto, Integer count) {

        var orderItemModel = new OrderItem();
        orderItemModel.setCount(count);

        Order order = objectMapper.convertValue(orderDto, Order.class);
        orderItemModel.setOrder(order);

        Product product = objectMapper.convertValue(productDto, Product.class);
        orderItemModel.setProduct(product);

        orderItemRepository.save(orderItemModel);
        log.info("Client add {} products id:{} to order id:{}", count, productDto.getId(), orderDto.getId());

        order.setPrice(getOrderPrice(order.getId()));
        orderRepository.save(order);
        log.info("Order id:{} update price", orderDto.getId());

        return objectMapper.convertValue(orderItemModel, OrderItemDto.class);
    }

    /**
     * Calculate order items and return total sum.
     *
     * @param id order id
     * @return order price
     */
    private Double getOrderPrice(Integer id) {
        return orderItemRepository.findAllByOrder_Id(id).stream()
                .map(o -> o.getProduct().getPrice() * o.getCount())
                .reduce(0.0, Double::sum);
    }

    /**
     * Get order by id
     *
     * @param id order id
     * @return order information
     */
    @Transactional(readOnly = true)
    public OrderInfoDto getById(Integer id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Order with id=" + id + " wasn't found"));
        return objectMapper.convertValue(order, OrderInfoDto.class);
    }

    /**
     * Get all orders
     *
     * @return order list information
     */
    @Transactional(readOnly = true)
    public List<OrderInfoDto> getAll() {
        List<Order> orders = (List<Order>) orderRepository.findAll();
        return orders.stream()
                .map(o -> objectMapper.convertValue(o, OrderInfoDto.class))
                .collect(toList());
    }

}
