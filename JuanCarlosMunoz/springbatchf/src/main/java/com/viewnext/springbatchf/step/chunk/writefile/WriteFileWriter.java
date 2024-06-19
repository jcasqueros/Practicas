package com.viewnext.springbatchf.step.chunk.writefile;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;

import com.viewnext.springbatchf.util.*;

import com.viewnext.springbatchf.util.exception.GenericException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ExtractObjectOperation extractObject;

    /**
     * Instantiates a new Write file writer.
     *
     * @param extractObject
     *         the extract object
     */
    @Autowired
    public WriteFileWriter(ExtractObjectOperation extractObject) {
        this.extractObject = extractObject;
    }

    @Override
    public void write(List<?> list) throws Exception {


        if (list.isEmpty()){
            throw new Exception("List cannot be empty");
        }

        Object firstElement = list.get(0);
        if (firstElement instanceof Object[]) {
            extractObjectsList(list);
        } else if (list.size() == 3) {

            writeFile(extractObject.extractObject(list));

        } else {
            throw new Exception("Error to process list");
        }

    }

    private void extractObjectsList(List<?> list) throws IOException, GenericException {
        Object[] array;
        for (Object object : list) {
            array = (Object[]) object;

            if (array == null){
                throw new GenericException("Error to process list");
            }
            writeFile(array);
        }
    }

    private void writeFile(Object[] array) throws GenericException {
        if (array == null){
            throw new GenericException("Error to process list");
        }

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
        }catch (IOException ignored){
            throw new GenericException("Error writing file");
        }

    }

}
