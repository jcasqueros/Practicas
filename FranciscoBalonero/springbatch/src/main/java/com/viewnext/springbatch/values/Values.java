package com.viewnext.springbatch.values;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class holds the values injected from the application properties file.
 *
 * <p> It uses the {@link org.springframework.beans.factory.annotation.Value} annotation to inject the values from the
 * properties file.</p>
 *
 * <p>The values are used throughout the application and are made accessible through the getters.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Component
@Getter
public class Values {

    /**
     * The input file path.
     *
     * <p>This value is injected from the application properties file using the key "file_input".</p>
     *
     * // Este valor se utiliza para leer el archivo de entrada del proceso de batch
     */
    @Value("${file_input}")
    private String fileInput;

    /**
     * The specific district.
     *
     * <p>This value is injected from the application properties file using the key "distrito_especifico".</p>
     *
     * // Este valor se utiliza para filtrar los datos por un distrito específico
     */
    @Value("${distrito_especifico}")
    private String distritoEspecifico;

    /**
     * The MongoDB URL.
     *
     * <p>This value is injected from the application properties file using the key "mongo_url".</p>
     *
     * // Este valor se utiliza para conectarse a la base de datos de MongoDB
     */
    @Value("${mongo_url}")
    private String mongoUrl;

    /**
     * The remaining districts.
     *
     * <p>This value is injected from the application properties file using the key "distritos_restantes".</p>
     *
     * // Este valor se utiliza para procesar los distritos restantes después de filtrar por el distrito específico
     */
    @Value("${distritos_restantes}")
    private String distritosRestantes;
}
