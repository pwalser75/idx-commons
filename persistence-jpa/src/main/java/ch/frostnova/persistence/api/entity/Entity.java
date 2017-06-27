package ch.frostnova.persistence.api.entity;

import java.io.Serializable;

/**
 * Entity interface with the following characteristics:
 * <ul>
 * <li>Entities are identified by their ID</li>
 * <li>Entities are Serializable</li>
 * <li>Entities can be persisted</li>
 * </ul>
 *
 * @author pwalser
 * @since 27.06.2017
 */
public interface Entity<ID> extends Serializable {

    /**
     * Return the ID of the entity
     *
     * @return id (may be null)
     */
    ID getId();

    /**
     * Checks if the entity is persistent. The default implementation assumes that the ID is generated and assigned upon
     * persisting the entity, hence the entity counts as <i>persistent</i> when it has a (non-null) ID.<br> Entities
     * whose ID is not generated but assigned before persisting it should override this method and determine the
     * <i>persistent</i> state by other means (e.g. by using a 'created' timestamp or a version).
     *
     * @return persistent
     */
    default boolean isPersistent() {
        return getId() != null;
    }
}