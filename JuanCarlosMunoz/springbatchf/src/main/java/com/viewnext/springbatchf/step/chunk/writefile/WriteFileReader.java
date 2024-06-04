package com.viewnext.springbatchf.step.chunk.writefile;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The type Write file reader.
 */
public class WriteFileReader implements ResourceAwareItemReaderItemStream<Object> {

    private CSVReader csvReader;

    @Override
    public void setResource(Resource resource) {
        try {
            csvReader = new CSVReader((new InputStreamReader(resource.getInputStream())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object[] read() throws Exception {
        String[] row = csvReader.readNext();
        if (row == null) {
            return null;
        }

        User user = User.builder().name(row[0]).dni(Long.valueOf(row[1])).address(row[2]).build();
        City city = City.builder().name(row[3]).codPostal(Integer.parseInt(row[4])).build();
        Order order = Order.builder().price(Float.parseFloat(row[4])).numOrder(Long.parseLong(row[5])).build();

        Object[] objects = new Object[3];
        objects[0] = user;
        objects[1] = city;
        objects[2] = order;

        return objects;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() {
        try {
            csvReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
