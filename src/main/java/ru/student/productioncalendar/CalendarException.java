
package ru.student.productioncalendar;

/**
 * Исключение при выполнении операций производственного календаря.
 */
public class CalendarException extends Exception {

    /**
     * Создаёт исключение с описанием причины ошибки.
     *
     * @param message описание ошибки
     */
    public CalendarException(String message) {
        super(message);
    }
}
