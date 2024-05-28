package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.business.bo.ActorBo;
import com.viewnext.bsan.practica04.business.exception.BadInputDataException;
import com.viewnext.bsan.practica04.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica04.business.service.ActorService;
import com.viewnext.bsan.practica04.persistence.entity.Actor;
import com.viewnext.bsan.practica04.persistence.repository.ActorRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomActorRepository;
import com.viewnext.bsan.practica04.persistence.specification.ActorSpecifications;
import com.viewnext.bsan.practica04.presentation.request.PersonFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.constants.RestApiDefaultParams;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ActorService} interface.
 *
 * @author Antonio Gil
 */
@Slf4j
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository repository;
    private final CustomActorRepository customRepository;
    private final ServiceLevelActorMapper mapper;

    public ActorServiceImpl(ActorRepository repository, CustomActorRepository customRepository,
                            ServiceLevelActorMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }


    @Override
    public List<ActorBo> getAll(PersonFilter filter, QueryOptions queryOptions) {
        boolean useCustomRepository = queryOptions.getUseCustomRepository().orElse(false);
        int pageNumber = queryOptions.getPageNumber().orElse(0);
        int pageSize = queryOptions.getPageSize().orElse(RestApiDefaultParams.DEFAULT_PAGE_SIZE);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return (useCustomRepository ? customGetAll(filter, pageable) : regularGetAll(filter, pageable));
    }

    private List<ActorBo> customGetAll(PersonFilter filter, Pageable pageable) {
        List<Actor> entityList;
        if (filter.isEmpty()) {
            entityList = customRepository.findAll(pageable);
        } else {
            Specification<Actor> spec = Specification.where(null);

            if (filter.getName().isPresent()) {
                String name = filter.getName().orElseThrow();
                spec = spec.and(ActorSpecifications.nameContainsIgnoreCase(name));
            }
            if (filter.getAge().isPresent()) {
                int age = filter.getAge().orElseThrow();
                spec = spec.and(ActorSpecifications.ageIsEqualTo(age));
            }
            if (filter.getNationality().isPresent()) {
                String nationality = filter.getNationality().orElseThrow();
                spec = spec.and(ActorSpecifications.nationalityIsEqualToIgnoreCase(nationality));
            }

            entityList = customRepository.findAll(spec, pageable);
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    private List<ActorBo> regularGetAll(PersonFilter filter, Pageable pageable) {
        List<Actor> entityList;
        if (filter.isEmpty()) {
            entityList = repository.findAll(pageable).toList();
        } else {
            Specification<Actor> spec = Specification.where(null);

            if (filter.getName().isPresent()) {
                String name = filter.getName().orElseThrow();
                spec = spec.and(ActorSpecifications.nameContainsIgnoreCase(name));
            }
            if (filter.getAge().isPresent()) {
                int age = filter.getAge().orElseThrow();
                spec = spec.and(ActorSpecifications.ageIsEqualTo(age));
            }
            if (filter.getNationality().isPresent()) {
                String nationality = filter.getNationality().orElseThrow();
                spec = spec.and(ActorSpecifications.nationalityIsEqualToIgnoreCase(nationality));
            }

            entityList = repository.findAll(spec, pageable).toList();
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    @Override
    public ActorBo getById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        Optional<Actor> foundEntity = (custom ? customRepository.findById(id) : repository.findById(id));

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    @Transactional
    public ActorBo create(ActorBo actor, Optional<Boolean> useCustomRepository) {
        validateActor(actor);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customCreate(actor) : regularCreate(actor));
    }

    private ActorBo customCreate(ActorBo actor) {
        if (customRepository.existsById(actor.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.ACTOR_ENTITY_NAME, actor.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Actor entity = mapper.boToEntity(actor);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private ActorBo regularCreate(ActorBo actor) {
        if (repository.existsById(actor.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.ACTOR_ENTITY_NAME, actor.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Actor entity = mapper.boToEntity(actor);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    @Transactional
    public ActorBo update(long id, ActorBo actor, Optional<Boolean> useCustomRepository) {
        validateActor(actor);
        actor.setId(id);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customUpdate(actor) : regularUpdate(actor));
    }

    private ActorBo customUpdate(ActorBo actor) {
        if (!customRepository.existsById(actor.getId())) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, actor.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Actor entity = mapper.boToEntity(actor);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private ActorBo regularUpdate(ActorBo actor) {
        if (!repository.existsById(actor.getId())) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, actor.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Actor entity = mapper.boToEntity(actor);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public void validateActor(ActorBo actor) {
        if (actor == null) {
            String message = MessageBuilder.buildNullNotAllowedMessage(Messages.ACTOR_ENTITY_NAME);
            log.warn(message);
            throw new MissingRequiredFieldException(message, Messages.ACTOR_ENTITY_NAME);
        }

        validateName(actor.getName());
        validateAge(actor.getAge());
        validateNationality(actor.getNationality());
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
    @Transactional
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
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }

    private void regularDeleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }

}
