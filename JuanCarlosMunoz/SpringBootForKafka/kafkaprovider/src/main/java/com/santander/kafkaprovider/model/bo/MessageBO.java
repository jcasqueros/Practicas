package com.santander.kafkaprovider.model.bo;

import lombok.Builder;
import lombok.Data;

/**
 * The type Message bo.
 */
@Data
@Builder
public class MessageBO {

    private String user;
    private String text;
    private Boolean primerUser;
}
