package ch.frostnova.persistence.api.entity;

import java.util.Objects;

/**
 * Base implementation of an {@link Entity}. Provides implementations of <code>equals(Object)</code>,
 * <code>hashCode()</code> and <code>toString()</code>.<br> The <code>equals(Object)</code> method is final and
 * implemented as follows:<br> Two entities are equal when they have the <b>same type and the same ID</b>. Entities
 * which are <b>not persistent</b> are <b>only equal to themselves</b>.
 *
 * @param <ID> ID type
 * @author pwalser
 * @since 27.06.2017
 */
public abstract class BaseEntity<ID> implements Entity<ID> {

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> other = (BaseEntity<?>) obj;
        if (!isPersistent()) {
            return other == this;
        }
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public final int hashCode() {
        if (!isPersistent()) {
            return super.hashCode();
        }
        return Objects.hash(getClass().getName(), getId());
    }

    @Override
    public String toString() {
        return getClass().getName() + "#" + getId();
    }
}