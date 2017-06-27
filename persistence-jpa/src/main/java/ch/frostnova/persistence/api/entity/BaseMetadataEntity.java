package ch.frostnova.persistence.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base entity implementation for {@link Entity} with {@link EntityMetadata}.<br>
 * Provides default column mappings for the {@link EntityMetadata} properties, but not for the ID.
 *
 * @author pwalser
 * @since 27.06.2017
 */
@MappedSuperclass
public abstract class BaseMetadataEntity<ID extends Serializable> extends BaseEntity<ID> implements EntityMetadata {

    @Version
    @Column(name = "VERSION", nullable = false)
    private long version;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime lastUpdatedOn;

    @Override
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public LocalDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    @PrePersist
    private void onCreate() {
        createdOn = LocalDateTime.now();
        lastUpdatedOn = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        lastUpdatedOn = LocalDateTime.now();
    }
}