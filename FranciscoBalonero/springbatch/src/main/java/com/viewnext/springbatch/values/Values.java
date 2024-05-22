package com.viewnext.springbatch.values;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Values {

    @Value("${file_input}")
    private String fileInput;

    @Value("${distrito}")
    private String distrito;

    @Value("${mongo_url}")
    private String mongoUrl;

    @Value("${others_file_output}")
    private String othersFileOutput;
}
