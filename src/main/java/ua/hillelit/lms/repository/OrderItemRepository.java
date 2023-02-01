package ua.hillelit.lms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.hillelit.lms.model.OrderItem;

import java.util.List;

/**
 * {@link OrderItemRepository}
 *
 * @author Dmytro Trotsenko on 1/28/23
 */

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    List<OrderItem> findAllByOrder_Id(Integer id);

}
