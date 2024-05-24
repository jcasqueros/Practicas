package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.dto.SeriesDTOMapper;
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

    private static final Logger logger = LoggerFactory.getLogger(SeriesController.class);

    private final SeriesServiceInterface seriesService;
    private final CommonOperation commonOperation;
    private final SeriesDTOMapper seriesDTOMapper;

    @Autowired
    public SeriesController(SeriesServiceInterface seriesService, CommonOperation commonOperation, SeriesDTOMapper seriesDTOMapper) {
        this.seriesService = seriesService;
        this.commonOperation = commonOperation;
        this.seriesDTOMapper = seriesDTOMapper;
    }

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
    public ResponseEntity<String> createSeries(@Valid @RequestBody SeriesDTO series, BindingResult bindingResult)
            throws GenericException {

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
    @PutMapping("/")
    public ResponseEntity<String> updateSeries(@RequestParam @NotNull Long id, @Valid @RequestBody SeriesDTO updatedSeries,
            BindingResult bindingResult) throws GenericException {
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
    @DeleteMapping("/")
    public ResponseEntity<String> deleteSeries(@RequestParam @NotNull Long id) throws GenericException {
        String message = "User not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (seriesService.deleteSeries(id)) {
            message = "Series delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets series by id.
     *
     * @param id
     *         the id
     * @return the series by id
     */
    @GetMapping("/")
    public SeriesDTO getSeriesById(@RequestParam @NotNull Long id) {
        SeriesBO seriesBO = seriesService.getSeriesById(id);
        SeriesDTO seriesDTO = seriesDTOMapper.boToDTO(seriesBO);

        if (seriesDTO == null) {
            logger.error("Series not found");
        }
        return seriesDTO;

    }

    /**
     * Gets all actors.
     *
     * @param page
     *         the page
     * @return the all actors
     */
    @GetMapping("/all")
    public ResponseEntity<List<SeriesDTO>> getAllActors(@RequestParam(defaultValue = "0") int page) {
        List<SeriesBO> seriesBOS = seriesService.getAllSeries(page);
        return ResponseEntity.ok(seriesDTOMapper.bosToDtos(seriesBOS));
    }

    /**
     * Gets series by created.
     *
     * @param created
     *         the created
     * @param page
     *         the page
     * @return the series by created
     */
    @GetMapping("/by-created")
    public ResponseEntity<List<SeriesDTO>> getSeriesByCreated(@RequestParam int created,
            @RequestParam(defaultValue = "0") int page) {
        List<SeriesBO> seriesBOS = seriesService.getSeriesByCreated(created, page);
        if (seriesBOS.isEmpty()) {
            logger.error("No series found with created {}", created);
            return ResponseEntity.notFound().build();
        }
        List<SeriesDTO> seriesDTOS = seriesDTOMapper.bosToDtos(seriesBOS);
        return ResponseEntity.ok(seriesDTOS);
    }

    /**
     * Gets series by title.
     *
     * @param title
     *         the title
     * @param page
     *         the page
     * @return the series by title
     */
    @GetMapping("/by-title")
    public ResponseEntity<List<SeriesDTO>> getSeriesByTitle(@RequestParam String title,
            @RequestParam(defaultValue = "0") int page) {
        List<SeriesBO> seriesBOS = seriesService.getSeriesByTitle(title, page);

        if (seriesBOS.isEmpty()) {
            logger.error("No series found with name {}", title);
            return ResponseEntity.notFound().build();
        }
        List<SeriesDTO> seriesDTOS = seriesDTOMapper.bosToDtos(seriesBOS);
        return ResponseEntity.ok(seriesDTOS);
    }

    /**
     * Gets seriess by actors.
     *
     * @param actorsName
     *         the actors nameº
     * @param page
     *         the page
     * @return the seriess by actors
     */
    @GetMapping("/by-actors")
    public ResponseEntity<List<SeriesDTO>> getSeriessByActors(@RequestParam List<String> actorsName,
            @RequestParam int page) {
        List<SeriesBO> seriesBOS = seriesService.getSeriesByActors(actorsName, page);

        if (seriesBOS.isEmpty()) {
            logger.error("No series found with actorsName {}", actorsName);
            return ResponseEntity.notFound().build();
        }
        List<SeriesDTO> seriesDTOS = seriesDTOMapper.bosToDtos(seriesBOS);

        return ResponseEntity.ok(seriesDTOS);
    }

    /**
     * Gets seriess by directors.
     *
     * @param directorsName
     *         the directors name
     * @param page
     *         the page
     * @return the seriess by directors
     */
    @GetMapping("/by-directors")
    public ResponseEntity<List<SeriesDTO>> getSeriessByDirectors(@RequestParam List<String> directorsName,
            @RequestParam int page) {
        List<SeriesBO> seriesBOS = seriesService.getSeriesByDirectors(directorsName, page);
        if (seriesBOS.isEmpty()) {
            logger.error("No series found with directorsName {}", directorsName);
            return ResponseEntity.notFound().build();
        }
        List<SeriesDTO> seriesDTOS = seriesDTOMapper.bosToDtos(seriesBOS);

        return ResponseEntity.ok(seriesDTOS);
    }

}

