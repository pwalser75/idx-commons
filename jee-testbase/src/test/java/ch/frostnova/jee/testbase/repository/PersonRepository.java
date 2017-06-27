package ch.frostnova.jee.testbase.repository;

import ch.frostnova.jee.testbase.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by pwalser on 04.02.2017.
 */
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
