package com.viewnext.practica.businesslayer.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The User business object.
 *
 * @author Francisco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {
    private String dni;

    private String name;

    private String surname;
    private int age;

    /**
     * Validate the DNI
     *
     * @return True if is valid, false not valid
     */
    public boolean validateDNI() {
        Pattern format = Pattern.compile("\\d{8}[A-Z]");
        String letter = "TRWAGMYFPDXBNJZSQVHLCKE";
        String[] invalid = new String[] { "00000000T", "00000001R", "99999999R" };
        return Arrays.binarySearch(invalid, dni) < 0 && format.matcher(dni).matches() && dni.charAt(8) == letter.charAt(
                Integer.parseInt(dni.substring(0, 8)) % 23);
    }
}
