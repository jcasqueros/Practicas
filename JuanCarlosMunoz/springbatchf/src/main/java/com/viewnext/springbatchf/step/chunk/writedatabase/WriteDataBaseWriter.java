package com.viewnext.springbatchf.step.chunk.writedatabase;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import org.springframework.batch.item.ItemWriter;

import java.io.IOException;
import java.util.List;

public class WriteDataBaseWriter implements ItemWriter<Object> {


    @Override
    public void write(List<?> list) throws Exception {
        for (Object object : list) {
            Object[] array = (Object[]) object;
            writeDb(array);
        }
    }
    private void writeDb(Object[] array) throws IOException {

        User user = (User) array[0];
        City city = (City) array[1];
        Order order = (Order) array[2];


    }



}
