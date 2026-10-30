
package ru.student.productioncalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Производственный календарь на один год.
 */
public class ProductionCalendar {

    private final int year;
    private final List<Holiday> holidays;
    private final List<LocalDate> preHolidays;
    /**
     * Создаёт календарь указанного года.
     *
     * @param year год календаря
     */
    public ProductionCalendar(int year) {
        if (year < 1) {
            throw new IllegalArgumentException(
                    "Год должен быть положительным"
            );
        }

        this.year = year;
        this.holidays = new ArrayList<>();
        this.preHolidays = new ArrayList<>();

    }

    /**
     * Возвращает год календаря.
     *
     * @return год
     */
    public int getYear() {
        return year;
    }

    /**
     * Добавляет праздник в календарь.
     *
     * @param holiday праздничный день
     * @throws CalendarException если праздник относится к другому году
     */
    public void addHoliday(Holiday holiday) throws CalendarException {
        Objects.requireNonNull(holiday, "Праздник не может быть null");

        if (holiday.date().getYear() != year) {
            throw new CalendarException(
                    "Праздник должен относиться к " + year + " году"
            );
        }

        if (!holidays.contains(holiday)) {
            holidays.add(holiday);
        }
    }

    /**
     * Возвращает копию списка праздников.
     *
     * @return список праздников
     */
    public List<Holiday> getHolidays() {
        return List.copyOf(holidays);
    }


    /**
     * Определяет тип указанной даты.
     *
     * @param date дата для проверки
     * @return тип календарного дня
     * @throws CalendarException если дата относится к другому году
     */
    public DayType getDayType(LocalDate date) throws CalendarException {
        Objects.requireNonNull(date, "Дата не может быть null");

        if (date.getYear() != year) {
            throw new CalendarException(
                    "Дата должна относиться к " + year + " году"
            );
        }

        if (holidays.stream().anyMatch(holiday -> holiday.date().equals(date))) {
            return DayType.HOLIDAY;
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY) {
            return DayType.WEEKEND;
        }

        if (preHolidays.contains(date)) {
            return DayType.PRE_HOLIDAY;
        }

        return DayType.WORKING;
    }

    /**
     * Подсчитывает рабочие дни за указанный период включительно.
     *
     * @param start начальная дата
     * @param end конечная дата
     * @return количество рабочих дней
     * @throws CalendarException если даты некорректны
     */
    public int countWorkingDays(LocalDate start, LocalDate end)
            throws CalendarException {
        validatePeriod(start, end);

        int count = 0;

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (getDayType(date).workingHours() > 0) {
                count++;
            }
        }

        return count;
    }

    /**
     * Подсчитывает рабочие часы за указанный период включительно.
     *
     * @param start начальная дата
     * @param end конечная дата
     * @return количество рабочих часов
     * @throws CalendarException если даты некорректны
     */
    public int countWorkingHours(LocalDate start, LocalDate end)
            throws CalendarException {
        validatePeriod(start, end);

        int hours = 0;

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            hours += getDayType(date).workingHours();
        }

        return hours;
    }

    /**
     * Проверяет корректность периода.
     *
     * @param start начальная дата
     * @param end конечная дата
     * @throws CalendarException если период некорректен
     */
    private void validatePeriod(LocalDate start, LocalDate end)
            throws CalendarException {
        Objects.requireNonNull(start, "Начальная дата не может быть null");
        Objects.requireNonNull(end, "Конечная дата не может быть null");

        if (start.isAfter(end)) {
            throw new CalendarException(
                    "Начальная дата не может быть позже конечной"
            );
        }

        if (start.getYear() != year || end.getYear() != year) {
            throw new CalendarException(
                    "Даты должны относиться к " + year + " году"
            );
        }
    }
    /**
     * Добавляет предпраздничную дату.
     *
     * @param date дата сокращённого рабочего дня
     * @throws CalendarException если дата относится к другому году
     */
    public void addPreHoliday(LocalDate date) throws CalendarException {
        Objects.requireNonNull(date, "Дата не может быть null");

        if (date.getYear() != year) {
            throw new CalendarException(
                    "Дата должна относиться к " + year + " году"
            );
        }

        if (!preHolidays.contains(date)) {
            preHolidays.add(date);
        }
    }

    /**
     * Выводит календарь указанного месяца с типами дней.
     *
     * @param month месяц от 1 до 12
     * @throws CalendarException если номер месяца некорректен
     */
    public void printMonth(int month) throws CalendarException {
        if (month < 1 || month > 12) {
            throw new CalendarException(
                    "Номер месяца должен быть от 1 до 12"
            );
        }

        LocalDate date = LocalDate.of(year, month, 1);

        System.out.println("\nКалендарь: " + date.getMonth() + " " + year);

        while (date.getMonthValue() == month) {
            System.out.printf(
                    "%s — %s%n",
                    date,
                    getDayType(date)
            );

            date = date.plusDays(1);
        }
    }

    /**
     * Подсчитывает рабочие дни за указанный квартал.
     *
     * @param quarter номер квартала от 1 до 4
     * @return количество рабочих дней
     * @throws CalendarException если номер квартала некорректен
     */
    public int countWorkingDaysInQuarter(int quarter)
            throws CalendarException {
        if (quarter < 1 || quarter > 4) {
            throw new CalendarException(
                    "Номер квартала должен быть от 1 до 4"
            );
        }

        int firstMonth = (quarter - 1) * 3 + 1;

        LocalDate start = LocalDate.of(year, firstMonth, 1);
        LocalDate end = start.plusMonths(3).minusDays(1);

        return countWorkingDays(start, end);
    }

    /**
     * Подсчитывает рабочие часы за указанный квартал.
     *
     * @param quarter номер квартала от 1 до 4
     * @return количество рабочих часов
     * @throws CalendarException если номер квартала некорректен
     */
    public int countWorkingHoursInQuarter(int quarter)
            throws CalendarException {
        if (quarter < 1 || quarter > 4) {
            throw new CalendarException(
                    "Номер квартала должен быть от 1 до 4"
            );
        }

        int firstMonth = (quarter - 1) * 3 + 1;

        LocalDate start = LocalDate.of(year, firstMonth, 1);
        LocalDate end = start.plusMonths(3).minusDays(1);

        return countWorkingHours(start, end);
    }

    /**
     * Подсчитывает рабочие дни за весь год.
     *
     * @return количество рабочих дней за год
     * @throws CalendarException если при подсчёте возникла ошибка
     */
    public int countWorkingDaysInYear() throws CalendarException {
        return countWorkingDays(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31)
        );
    }

    /**
     * Подсчитывает рабочие часы за весь год.
     *
     * @return количество рабочих часов за год
     * @throws CalendarException если при подсчёте возникла ошибка
     */
    public int countWorkingHoursInYear() throws CalendarException {
        return countWorkingHours(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31)
        );
    }
}

