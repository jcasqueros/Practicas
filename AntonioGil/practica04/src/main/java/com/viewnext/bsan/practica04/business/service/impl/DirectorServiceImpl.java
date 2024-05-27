package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.business.bo.DirectorBo;
import com.viewnext.bsan.practica04.business.exception.BadInputDataException;
import com.viewnext.bsan.practica04.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica04.business.service.DirectorService;
import com.viewnext.bsan.practica04.persistence.entity.Director;
import com.viewnext.bsan.practica04.persistence.repository.DirectorRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomDirectorRepository;
import com.viewnext.bsan.practica04.persistence.specification.DirectorSpecifications;
import com.viewnext.bsan.practica04.presentation.request.PersonFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.constants.RestApiDefaultParams;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelDirectorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code DirectorService} interface.
 *
 * @author Antonio Gil
 */
@Slf4j
@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository repository;
    private final CustomDirectorRepository customRepository;
    private final ServiceLevelDirectorMapper mapper;

    public DirectorServiceImpl(DirectorRepository repository, CustomDirectorRepository customRepository,
                               ServiceLevelDirectorMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DirectorBo> getAll(PersonFilter filter, QueryOptions queryOptions) {
        boolean useCustomRepository = queryOptions.getUseCustomRepository().orElse(false);
        int pageNumber = queryOptions.getPageNumber().orElse(0);
        int pageSize = queryOptions.getPageSize().orElse(RestApiDefaultParams.DEFAULT_PAGE_SIZE);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return (useCustomRepository ? customGetAll(filter, pageable) : regularGetAll(filter, pageable));
    }

    private List<DirectorBo> customGetAll(PersonFilter filter, Pageable pageable) {
        List<Director> entityList;
        if (filter.isEmpty()) {
            entityList = customRepository.findAll(pageable);
        } else {
            Specification<Director> spec = Specification.where(null);

            if (filter.getName().isPresent()) {
                String name = filter.getName().orElseThrow();
                spec = spec.and(DirectorSpecifications.nameContainsIgnoreCase(name));
            }
            if (filter.getAge().isPresent()) {
                int age = filter.getAge().orElseThrow();
                spec = spec.and(DirectorSpecifications.ageIsEqualTo(age));
            }
            if (filter.getNationality().isPresent()) {
                String nationality = filter.getNationality().orElseThrow();
                spec = spec.and(DirectorSpecifications.nationalityIsEqualToIgnoreCase(nationality));
            }

            entityList = customRepository.findAll(spec, pageable);
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    private List<DirectorBo> regularGetAll(PersonFilter filter, Pageable pageable) {
        List<Director> entityList;
        if (filter.isEmpty()) {
            entityList = repository.findAll(pageable).toList();
        } else {
            Specification<Director> spec = Specification.where(null);

            if (filter.getName().isPresent()) {
                String name = filter.getName().orElseThrow();
                spec = spec.and(DirectorSpecifications.nameContainsIgnoreCase(name));
            }
            if (filter.getAge().isPresent()) {
                int age = filter.getAge().orElseThrow();
                spec = spec.and(DirectorSpecifications.ageIsEqualTo(age));
            }
            if (filter.getNationality().isPresent()) {
                String nationality = filter.getNationality().orElseThrow();
                spec = spec.and(DirectorSpecifications.nationalityIsEqualToIgnoreCase(nationality));
            }

            entityList = repository.findAll(spec, pageable).toList();
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    @Override
    public DirectorBo getById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        Optional<Director> foundEntity = (custom ? customRepository.findById(id) : repository.findById(id));

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public DirectorBo create(DirectorBo director, Optional<Boolean> useCustomRepository) {
        validateDirector(director);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customCreate(director) : regularCreate(director));
    }

    private DirectorBo customCreate(DirectorBo director) {
        if (customRepository.existsById(director.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.DIRECTOR_ENTITY_NAME, director.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Director entity = mapper.boToEntity(director);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private DirectorBo regularCreate(DirectorBo director) {
        if (repository.existsById(director.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.DIRECTOR_ENTITY_NAME, director.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Director entity = mapper.boToEntity(director);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public DirectorBo update(long id, DirectorBo director, Optional<Boolean> useCustomRepository) {
        validateDirector(director);
        director.setId(id);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customUpdate(director) : regularUpdate(director));
    }

    private DirectorBo customUpdate(DirectorBo director) {
        if (!customRepository.existsById(director.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, director.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Director entity = mapper.boToEntity(director);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private DirectorBo regularUpdate(DirectorBo director) {
        if (!repository.existsById(director.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, director.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Director entity = mapper.boToEntity(director);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public void validateDirector(DirectorBo director) {
        if (director == null) {
            String message = MessageBuilder.buildNullNotAllowedMessage(Messages.DIRECTOR_ENTITY_NAME);
            log.warn(message);
            throw new MissingRequiredFieldException(message, Messages.DIRECTOR_ENTITY_NAME);
        }

        validateName(director.getName());
        validateAge(director.getAge());
        validateNationality(director.getNationality());
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("name");
            log.warn(message);
            throw new MissingRequiredFieldException(message, "name");
        }
    }

    @Override
    public void validateAge(int age) {
        if (age < 0) {
            String message = MessageBuilder.buildNegativeNumberNotAllowedMessage("age");
            log.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void validateNationality(String nationality) {
        if (nationality == null || nationality.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("nationality");
            log.warn(message);
            throw new MissingRequiredFieldException(message, "nationality");
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
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }

    private void regularDeleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }

}
