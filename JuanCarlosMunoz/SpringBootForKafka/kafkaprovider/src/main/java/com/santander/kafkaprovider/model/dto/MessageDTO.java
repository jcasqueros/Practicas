package com.santander.kafkaprovider.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

/**
 * The type Message dto.
 */
@Data
@Builder
public class MessageDTO {

    @NotNull
    @Pattern(regexp = "\\S+.*", message = "User  cannot be empty or contain only spaces")
    private String user;
    @NotNull
    @Pattern(regexp = "\\S+.*", message = "Message  cannot be empty or contain only spaces")
    private String text;
    @NotNull
    private Boolean primerUser;

}
