package ch.frostnova.jee.testbase;

import ch.frostnova.jee.testbase.entity.Gender;
import ch.frostnova.jee.testbase.entity.PersonEntity;
import ch.frostnova.jee.testbase.repository.PersonRepository;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(CdiTestRunner.class)
public class RepositoryTest {

    @Inject
    private PersonRepository repository;

    @Inject
    private TransactionalWrapper tx;

    @Test
    public void testCRUD() throws Exception {

        // create
        final PersonEntity person = new PersonEntity();
        person.setFirstName("Peter");
        person.setLastName("Walser");
        person.setGender(Gender.MALE);

        Assert.assertFalse(person.isPersistent());
        Assert.assertNull(person.getCreatedOn());
        Assert.assertNull(person.getLastUpdatedOn());

        final LocalDateTime beforeCreate = LocalDateTime.now();
        PersonEntity created = tx.execute(() -> repository.save(person));
        final LocalDateTime afterCreate = LocalDateTime.now();
        Assert.assertTrue(created.isPersistent());
        Assert.assertNotNull(created.getId());
        Assert.assertNotNull(created.getCreatedOn());
        Assert.assertNotNull(created.getLastUpdatedOn());
        Assert.assertFalse(created.getCreatedOn().isBefore(beforeCreate));
        Assert.assertFalse(created.getCreatedOn().isAfter(afterCreate));
        Assert.assertFalse(created.getLastUpdatedOn().isBefore(beforeCreate));
        Assert.assertFalse(created.getLastUpdatedOn().isAfter(afterCreate));
        long version = created.getVersion();

        // read
        PersonEntity read = tx.execute(() -> repository.findById(person.getId())).orElse(null);
        Assert.assertNotNull(read);
        Assert.assertEquals("Peter", read.getFirstName());
        Assert.assertEquals("Walser", read.getLastName());
        Assert.assertEquals(Gender.MALE, read.getGender());

        // update
        read.setDateOfBirth(LocalDate.of(1975, 12, 20));
        final LocalDateTime beforeUpdate = LocalDateTime.now();
        PersonEntity updated = tx.execute(() -> repository.save(person));
        final LocalDateTime afterUpdate = LocalDateTime.now();

        Assert.assertTrue(person.getVersion() > version);
        Assert.assertFalse(updated.getCreatedOn().isBefore(beforeCreate));
        Assert.assertFalse(updated.getCreatedOn().isAfter(afterCreate));
        Assert.assertFalse(updated.getLastUpdatedOn().isBefore(beforeUpdate));
        Assert.assertFalse(updated.getLastUpdatedOn().isAfter(afterUpdate));

        // delete
        repository.deleteById(updated.getId());
        PersonEntity deleted = repository.findById(person.getId()).orElse(null);
        Assert.assertNull(deleted);
    }
}
