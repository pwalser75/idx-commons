package ch.frostnova.persistence.api.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * JPA type converter for {@link java.time.LocalDate} (mapped as {@link java.sql.Date})
 *
 * @author pwalser
 * @since 27.06.2017
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate dateTime) {
        return dateTime == null ? null : Date.valueOf(dateTime);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
