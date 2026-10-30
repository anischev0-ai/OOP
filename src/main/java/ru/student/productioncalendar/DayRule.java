package ru.student.productioncalendar;

/**
 * Правило, определяющее продолжительность рабочего дня.
 */
public interface DayRule {

    /**
     * Возвращает продолжительность рабочего дня в часах.
     *
     * @return количество рабочих часов
     */
    int workingHours();
}