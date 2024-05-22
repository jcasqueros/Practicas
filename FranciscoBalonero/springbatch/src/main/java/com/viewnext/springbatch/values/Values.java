package com.viewnext.springbatch.values;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component that holds values injected from properties file.
 *
 * @author Francisco Balonero Olivera
 */
@Component
@Getter
public class Values {

    /**
     * Input file path.
     */
    @Value("${file_input}")
    private String fileInput;

    /**
     * Distrito value.
     */
    @Value("${distrito}")
    private String distrito;

    /**
     * MongoDB URL.
     */
    @Value("${mongo_url}")
    private String mongoUrl;
}
