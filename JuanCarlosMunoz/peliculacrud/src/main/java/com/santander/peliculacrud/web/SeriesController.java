/*
package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

*/
/**
 * The type SeriesBO controller.
 *//*

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesServiceInterface seriesService;

    @Autowired
    private CommonOperation commonOperation;

    private static final Logger logger = LoggerFactory.getLogger(SeriesController.class);

    */
/**
     * Create series out response entity.
     *
     * @param seriesDTO
     *         the series out
     * @param bindingResult
     *         the binding result
     * @return the response entity
     *//*

    @PostMapping
    public ResponseEntity<String> createSeriesOut(@Valid @RequestBody SeriesDTO seriesDTO, BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Series not created";
        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            if (seriesService.createSeries(seriesDTO)) {
                status = HttpStatus.CREATED;
                message = "Series created successfully";

            }
        }

        return new ResponseEntity<>(message, status);
    }

    */
/**
     * Update series out response entity.
     *
     * @param id
     *         the id
     * @param updatedSeriesDTO
     *         the updated series out
     * @param bindingResult
     *         the binding result
     * @return the response entity
     *//*

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSeriesOut(@PathVariable Long id, @Valid @RequestBody SeriesDTO updatedSeriesDTO,
            BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Series not updated";
        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            if (seriesService.updateSeries(id, updatedSeriesDTO)) {
                status = HttpStatus.OK;
                message = "Series updated successfully";

            }
        }

        return new ResponseEntity<>(message, status);
    }

    */
/**
     * Gets all series outs.
     *
     * @return the all series outs
     *//*

    @GetMapping()
    public List<SeriesBO> getAllSeriesOuts() {
        return seriesService.getAllSeries();
    }

    */
/**
     * Gets series out by id.
     *
     * @param id
     *         the id
     * @return the series out by id
     *//*

    @GetMapping("/{id}")
    public SeriesBO getSeriesOutById(@PathVariable Long id) {
        return seriesService.seriesOut(id);
    }

    */
/**
     * Delete series out response entity.
     *
     * @param id
     *         the id
     * @return the response entity
     *//*

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeriesOut(@PathVariable Long id) {
        String message = "Series not deleted";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (id != null) {

            if (seriesService.deleteSeries(id)) {
                message = "Series deleted";
                status = HttpStatus.NO_CONTENT;
            }

        } else {
            logger.error("Id is null");

        }

        return new ResponseEntity<>(message, status);
    }

}
*/
