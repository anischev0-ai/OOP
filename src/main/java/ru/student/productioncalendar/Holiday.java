
package ru.student.productioncalendar;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Праздничный день производственного календаря.
 *
 * @param date дата праздника
 * @param name название праздника
 * @param transferable признак возможности переноса
 */
public record Holiday(
        LocalDate date,
        String name,
        boolean transferable
) {
    /**
     * Проверяет корректность данных праздника.
     */
    public Holiday {
        Objects.requireNonNull(date, "Дата праздника не может быть null");
        Objects.requireNonNull(name, "Название праздника не может быть null");

        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Название праздника не может быть пустым"
            );
        }
    }
}
