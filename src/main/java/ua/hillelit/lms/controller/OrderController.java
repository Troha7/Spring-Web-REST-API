package ua.hillelit.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hillelit.lms.dto.OrderInfoDto;
import ua.hillelit.lms.service.OrderService;

import java.util.List;

/**
 * {@link OrderController} is a class for making restful web service.
 * Main path "/orders" and port "8080".
 *
 * @author Dmytro Trotsenko on 2/1/23
 */

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * GET request for getting order by id.
     *
     * @param id order id
     * @return {@link OrderInfoDto}
     */
    @GetMapping("{id}")
    public OrderInfoDto getById(@PathVariable int id) {
        if (id < 0) {
            throw new IllegalArgumentException("The order id < 0 is not exist");
        }
        return orderService.getById(id);
    }

    /**
     * GET request for getting all orders.
     *
     * @return {@link List<OrderInfoDto>}
     */
    @GetMapping
    public List<OrderInfoDto> getAll() {
        return orderService.getAll();
    }

}
