package ch.frostnova.persistence.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity metadata (version, creation and last modification time).
 *
 * @author pwalser
 * @since 27.06.2017
 */
public interface EntityMetadata extends Serializable {

    /**
     * Version number, supposed to get incremented each time the entity is updated. The version can be
     * used for <i>Optimistic Locking</i>.
     *
     * @return version number. The initial version number is not defined, versions should only be compared among
     * themselves.
     */
    long getVersion();

    /**
     * Timestamp of the creation time.
     *
     * @return creation timestamp
     */
    LocalDateTime getCreatedOn();

    /**
     * Timestamp of the last modification time
     *
     * @return last modification timestamp
     */
    LocalDateTime getLastUpdatedOn();

}
