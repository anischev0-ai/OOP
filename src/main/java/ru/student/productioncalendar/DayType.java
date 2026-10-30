
package ru.student.productioncalendar;

/**
 * Тип календарного дня и продолжительность работы в этот день.
 */
public enum DayType implements DayRule {
    WORKING(8),
    WEEKEND(0),
    HOLIDAY(0),
    PRE_HOLIDAY(7),
    VACATION(0);

    private final int hours;

    DayType(int hours) {
        this.hours = hours;
    }

    /**
     * Возвращает продолжительность рабочего дня.
     *
     * @return количество рабочих часов
     */
    @Override
    public int workingHours() {
        return hours;
    }
}
