package com.viewnext.springbatchf.step.chunk.writedatabase;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import com.viewnext.springbatchf.model.repository.jpa.CityRepository;
import com.viewnext.springbatchf.model.repository.jpa.OrderRepository;
import com.viewnext.springbatchf.model.repository.jpa.UserRepository;
import com.viewnext.springbatchf.util.ExtractObjectOperation;
import com.viewnext.springbatchf.util.exception.GenericException;
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

    private final ExtractObjectOperation extractObject;

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
            UserRepository userRepository, ExtractObjectOperation extractObject) {
        this.cityRepository = cityRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.extractObject = extractObject;
    }

    @Override
    public void write(List<?> list) throws Exception {
        if (list.isEmpty()){
            throw new GenericException("List cannot be empty");
        }
        Object firstElement = list.get(0);
        if (firstElement instanceof Object[]) {
            extractObjectsList(list);
        } else if (list.size() == 3) {

            writeFile(extractObject.extractObject(list));

        } else {
            throw new Exception("Invalid object type");
        }
    }

    void extractObjectsList(List<?> list) throws  GenericException {
        Object[] array;
        for (Object object : list) {
            array = (Object[]) object;


            writeFile(array);
        }
    }


    void writeFile(Object[] array) throws GenericException {

        if (array[0] == null || array[1] == null || array[2] == null){
            throw new GenericException("Error to process list");
        }

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
