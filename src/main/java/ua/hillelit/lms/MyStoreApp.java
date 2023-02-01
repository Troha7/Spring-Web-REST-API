package ua.hillelit.lms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.hillelit.lms.dto.OrderDto;
import ua.hillelit.lms.dto.OrderInfoDto;
import ua.hillelit.lms.dto.ProductDto;
import ua.hillelit.lms.service.OrderService;
import ua.hillelit.lms.service.ProductService;

import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class MyStoreApp {

    private final ProductService productService;
    private final OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(MyStoreApp.class, args);
    }

    /**
     * Run application after all initialization steps.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        var milk = new ProductDto(1, "Milk", 40.75);
        var bread = new ProductDto(2, "Bread", 18.50);
        var cola = new ProductDto(3, "Coca-Cola", 50.0);
        var sugar = new ProductDto(4, "Sugar", 35.60);
        productService.createProduct(milk);
        productService.createProduct(bread);
        productService.createProduct(cola);
        productService.createProduct(sugar);

        OrderDto order1 = orderService.createOrder(1);
        orderService.addProductToOrder(order1, sugar, 1);
        orderService.addProductToOrder(order1, bread, 2);

        OrderDto order2 = orderService.createOrder(2);
        orderService.addProductToOrder(order2, cola, 3);
        orderService.addProductToOrder(order2, milk, 1);

        List<OrderInfoDto> orders = orderService.getAll();
        log.info("All orders: {}", orders);

        OrderInfoDto order = orderService.getById(1);
        log.info("Order: {}", order);

    }

}
