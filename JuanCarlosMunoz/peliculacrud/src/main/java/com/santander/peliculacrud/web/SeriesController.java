package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

import com.santander.peliculacrud.util.mapper.SeriesDTOMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/**
 * The type Series controller.
 */
@RestController
@RequestMapping("/seriess")
public class SeriesController {

    @Autowired
    private SeriesServiceInterface seriesService;
    @Autowired
    private CommonOperation commonOperation;
    @Autowired
    private SeriesDTOMapper seriesDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(SeriesController.class);

    /**
     * Create series response entity.
     *
     * @param series
     *         the series
     * @param bindingResult
     *         the binding result
     * @return the response entity
     */
    @ApiOperation(value = "Create a new series", notes = "Create a new series with the provided information")
    @ApiResponses({ @ApiResponse(code = 201, message = "Series created successfulnesses"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping
    public ResponseEntity<String> createSeries(@Valid @RequestBody SeriesDTO series, BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Series not created";

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            SeriesBO seriesBO = seriesDTOMapper.dtoToBo(series);
            if (seriesService.createSeries(seriesBO) != null) {
                message = "Series created successfully";
                status = HttpStatus.CREATED;

            }

        }
        return new ResponseEntity<>(message, status);

    }

    /**
     * Update series response entity.
     *
     * @param id
     *         the id
     * @param updatedSeries
     *         the updated series
     * @param bindingResult
     *         the binding result
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSeries(@PathVariable @NotNull Long id, @Valid @RequestBody SeriesDTO updatedSeries,
            BindingResult bindingResult) {
        String message = "Series not update";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            SeriesBO seriesBO = seriesDTOMapper.dtoToBo(updatedSeries);
            if (seriesService.updateSeries(id, seriesBO) != null) {
                message = "Series updated successfully";
                status = HttpStatus.OK;

            }
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete series response entity.
     *
     * @param id
     *         the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeries(@PathVariable @NotNull Long id) {
        String message = "User not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (seriesService.deleteSeries(id)) {
            message = "Series delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets all seriess.
     *
     * @return the all seriess
     */
    @GetMapping()
    public List<SeriesDTO> getAllSeriess() {
        List<SeriesBO> seriesBOS = seriesService.getAllSeries();
        return seriesDTOMapper.bosToDtos(seriesBOS);
    }

    /**
     * Gets series by id.
     *
     * @param id
     *         the id
     * @return the series by id
     */
    @GetMapping("/{id}")
    public SeriesDTO getSeriesById(@PathVariable @NotNull Long id) {
        SeriesBO seriesBO = seriesService.getSeriesById(id);
        SeriesDTO seriesDTO = seriesDTOMapper.boToDTO(seriesBO);

        if (seriesDTO == null) {
            logger.error("Series not found");
        }
        return seriesDTO;

    }
}

