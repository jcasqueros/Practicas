package com.viewnext.springbatchf.step.chunk.writedatabase;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import com.viewnext.springbatchf.model.repository.CityRepository;
import com.viewnext.springbatchf.model.repository.OrderRepository;
import com.viewnext.springbatchf.model.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The type Write data base writer.
 */
public class WriteDataBaseWriter implements ItemWriter<Object> {

    private final CityRepository cityRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * Instantiates a new Write data base writer.
     *
     * @param cityRepository
     *         the city repository
     * @param orderRepository
     *         the order repository
     * @param userRepository
     *         the user repository
     */
    @Autowired
    public WriteDataBaseWriter(CityRepository cityRepository, OrderRepository orderRepository,
            UserRepository userRepository) {
        this.cityRepository = cityRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void write(List<?> list) {
        for (Object object : list) {
            Object[] array = (Object[]) object;
            writeFile(array);
        }
    }

    private void writeFile(Object[] array) {

        User user = (User) array[0];
        City city = (City) array[1];
        Order order = (Order) array[2];

        if (!userRepository.existsById(user.getDni())) {
            userRepository.save(user);
        }
        if (!cityRepository.existsById(city.getCodPostal())) {
            cityRepository.save(city);
        }

        if (!orderRepository.existsById(order.getNumOrder())) {
            orderRepository.save(order);
        }

    }

}
