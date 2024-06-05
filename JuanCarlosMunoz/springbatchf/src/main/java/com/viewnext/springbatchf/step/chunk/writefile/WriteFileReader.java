package com.viewnext.springbatchf.step.chunk.writefile;

import com.opencsv.CSVReader;
import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The type Write file reader.
 */
public class WriteFileReader implements ResourceAwareItemReaderItemStream<Object> {

    private CSVReader csvReader;
    private static final Logger logger = LoggerFactory.getLogger(WriteFileReader.class);

    @Override
    public void setResource(Resource resource) {
        try {
            csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()));
        } catch (IOException e) {
            throw new ItemStreamException("Error initializing file reader", e);
        }
    }

    @Override
    public Object[] read() throws Exception {
        Object[] objects = new Object[3];

        if (csvReader != null) {
            String[] row = csvReader.readNext();
            if (row == null) {
                return null;
            }
            cleanArray(row);

            if (row.length < 7) {
                throw new ItemStreamException("CSV file must have at least 7 columns");
            }

            User user = User.builder().name(row[0]).dni(row[1]).address(row[2]).build();
            City city = City.builder().name(row[3]).codPostal(Integer.parseInt(row[4])).build();
            Order order = Order.builder().price(Double.parseDouble(row[5])).numOrder(Long.parseLong(row[6])).build();

            objects[0] = user;
            objects[1] = city;
            objects[2] = order;
        } else {
            logger.error("Error csvReader is null");
        }
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

    private void cleanArray(String[] row) {
        for (int i = 0; i < row.length; i++) {
            row[i] = row[i].trim();
        }
    }
}
