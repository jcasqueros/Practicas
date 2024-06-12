package com.viewnext.springbatchf.step.chunk.writefile;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The type Write file writer.
 */
@Component
public class WriteFileWriter implements ItemWriter<Object> {

    @Value("${log.resource.finalFileUser}")
    private String finalFilePath;

    @Override
    public void write(List<?> list) throws Exception {
        for (Object object : list) {
            Object[] array = (Object[]) object;
            writeFile(array);
        }
    }

    private void writeFile(Object[] array) throws IOException {
        User user = (User) array[0];
        City city = (City) array[1];
        Order order = (Order) array[2];

        String fileName = user.getDni() + ".txt";
        File file = new File(finalFilePath, fileName);

        boolean append = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {

            writer.write(
                    "El/La Sr/a " + user.getName() + " con DNI " + user.getDni() + ", reside en " + user.getAddress() + ",\n");
            writer.write(city.getName() + " con codigo postal " + city.getCodPostal() + ",\n");
            writer.write("ha realizado una compra de " + order.getPrice() + " euros\n");
            writer.write("con número de pedido " + order.getNumOrder() + ".\n\n");
        }
    }

}
