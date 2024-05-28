package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.business.bo.ShowBo;
import com.viewnext.bsan.practica04.business.exception.BadInputDataException;
import com.viewnext.bsan.practica04.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica04.business.service.ShowService;
import com.viewnext.bsan.practica04.persistence.entity.Show;
import com.viewnext.bsan.practica04.persistence.repository.ShowRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomShowRepository;
import com.viewnext.bsan.practica04.persistence.specification.ShowSpecifications;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.presentation.request.WatchableFilter;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.constants.RestApiDefaultParams;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelShowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ShowService} interface.
 *
 * @author Antonio Gil
 */
@Slf4j
@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository repository;
    private final CustomShowRepository customRepository;
    private final ServiceLevelShowMapper mapper;

    public ShowServiceImpl(ShowRepository repository, CustomShowRepository customRepository,
                           ServiceLevelShowMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }


    @Override
    public List<ShowBo> getAll(WatchableFilter filter, QueryOptions queryOptions) {
        boolean useCustomRepository = queryOptions.getUseCustomRepository().orElse(false);
        int pageNumber = queryOptions.getPageNumber().orElse(0);
        int pageSize = queryOptions.getPageSize().orElse(RestApiDefaultParams.DEFAULT_PAGE_SIZE);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return (useCustomRepository ? customGetAll(filter, pageable) : regularGetAll(filter, pageable));
    }

    private List<ShowBo> customGetAll(WatchableFilter filter, Pageable pageable) {
        List<Show> entityList;
        if (filter.isEmpty()) {
            entityList = customRepository.findAll(pageable);
        } else {
            Specification<Show> spec = Specification.where(null);

            if (filter.getTitle().isPresent()) {
                String title = filter.getTitle().orElseThrow();
                spec = spec.and(ShowSpecifications.titleContainsIgnoreCase(title));
            }
            if (filter.getYear().isPresent()) {
                int year = filter.getYear().orElseThrow();
                spec = spec.and(ShowSpecifications.yearIsEqualTo(year));
            }

            entityList = customRepository.findAll(spec, pageable);
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    private List<ShowBo> regularGetAll(WatchableFilter filter, Pageable pageable) {
        List<Show> entityList;
        if (filter.isEmpty()) {
            entityList = customRepository.findAll(pageable);
        } else {
            Specification<Show> spec = Specification.where(null);

            if (filter.getTitle().isPresent()) {
                String title = filter.getTitle().orElseThrow();
                spec = spec.and(ShowSpecifications.titleContainsIgnoreCase(title));
            }
            if (filter.getYear().isPresent()) {
                int year = filter.getYear().orElseThrow();
                spec = spec.and(ShowSpecifications.yearIsEqualTo(year));
            }

            entityList = repository.findAll(spec, pageable).toList();
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    @Override
    public ShowBo getById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        Optional<Show> foundEntity = (custom ? customRepository.findById(id) : repository.findById(id));

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ShowBo create(ShowBo show, Optional<Boolean> useCustomRepository) {
        validateShow(show);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customCreate(show) : regularCreate(show));
    }

    private ShowBo customCreate(ShowBo show) {
        if (customRepository.existsById(show.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.SHOW_ENTITY_NAME, show.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Show entity = mapper.boToEntity(show);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private ShowBo regularCreate(ShowBo show) {
        if (repository.existsById(show.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.SHOW_ENTITY_NAME, show.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Show entity = mapper.boToEntity(show);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public ShowBo update(long id, ShowBo show, Optional<Boolean> useCustomRepository) {
        validateShow(show);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customUpdate(show) : regularUpdate(show));
    }

    private ShowBo customUpdate(ShowBo show) {
        if (!customRepository.existsById(show.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, show.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Show entity = mapper.boToEntity(show);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private ShowBo regularUpdate(ShowBo show) {
        if (!repository.existsById(show.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, show.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Show entity = mapper.boToEntity(show);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public void validateShow(ShowBo show) {
        if (show == null) {
            String message = MessageBuilder.buildNullNotAllowedMessage(Messages.SHOW_ENTITY_NAME);
            log.warn(message);
            throw new MissingRequiredFieldException(message, Messages.SHOW_ENTITY_NAME);
        }

        validateTitle(show.getTitle());
        validateYear(show.getYear());
    }

    @Override
    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("title");
            log.warn(message);
            throw new MissingRequiredFieldException(message, "title");
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            String message = MessageBuilder.buildNegativeNumberNotAllowedMessage("year");
            log.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void deleteById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        if (custom) {
            customDeleteById(id);
        } else {
            regularDeleteById(id);
        }
    }

    private void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }

    private void regularDeleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }
}
