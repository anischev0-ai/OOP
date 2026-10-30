
package ru.student.productioncalendar;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductionCalendarTest {

    @Test
    void shouldReturnHolidayForRegisteredHoliday() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);
        LocalDate date = LocalDate.of(2026, 1, 1);

        calendar.addHoliday(new Holiday(date, "Новый год", false));

        assertEquals(DayType.HOLIDAY, calendar.getDayType(date));
    }

    @Test
    void shouldReturnWeekendForSaturday() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertEquals(
                DayType.WEEKEND,
                calendar.getDayType(LocalDate.of(2026, 1, 10))
        );
    }

    @Test
    void shouldReturnWorkingDayForOrdinaryMonday() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertEquals(
                DayType.WORKING,
                calendar.getDayType(LocalDate.of(2026, 1, 5))
        );
    }

    @Test
    void shouldReturnSevenHoursForPreHoliday() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);
        LocalDate date = LocalDate.of(2026, 2, 20);

        calendar.addPreHoliday(date);

        assertEquals(DayType.PRE_HOLIDAY, calendar.getDayType(date));
        assertEquals(7, calendar.getDayType(date).workingHours());
    }

    @Test
    void shouldCountWorkingDaysInJanuary() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 1), "Новый год", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 2), "Новогодние каникулы", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 7), "Рождество Христово", false
        ));

        assertEquals(
                19,
                calendar.countWorkingDays(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 31)
                )
        );
    }

    @Test
    void shouldRejectDateFromAnotherYear() {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertThrows(
                CalendarException.class,
                () -> calendar.getDayType(LocalDate.of(2025, 12, 31))
        );
    }

    @Test
    void shouldRejectHolidayFromAnotherYear() {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        Holiday holiday = new Holiday(
                LocalDate.of(2025, 12, 31),
                "Новый год",
                false
        );

        assertThrows(
                CalendarException.class,
                () -> calendar.addHoliday(holiday)
        );
    }

    @Test
    void shouldNotAddDuplicateHoliday() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);
        LocalDate date = LocalDate.of(2026, 1, 1);

        calendar.addHoliday(new Holiday(date, "Новый год", false));
        calendar.addHoliday(new Holiday(date, "Новый год", false));

        assertEquals(1, calendar.getHolidays().size());
    }

    @Test
    void shouldRejectNullHoliday() {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertThrows(
                NullPointerException.class,
                () -> calendar.addHoliday(null)
        );
    }

    @Test
    void shouldCountWorkingHoursIncludingPreHoliday() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);
        calendar.addPreHoliday(LocalDate.of(2026, 2, 20));

        assertEquals(
                15,
                calendar.countWorkingHours(
                        LocalDate.of(2026, 2, 20),
                        LocalDate.of(2026, 2, 23)
                )
        );
    }

    @Test
    void shouldCountZeroWorkingHoursForWeekend() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertEquals(
                0,
                calendar.countWorkingHours(
                        LocalDate.of(2026, 1, 10),
                        LocalDate.of(2026, 1, 11)
                )
        );
    }

    @Test
    void shouldReturnImmutableHolidayList() throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 1),
                "Новый год",
                false
        ));

        assertThrows(
                UnsupportedOperationException.class,
                () -> calendar.getHolidays().add(
                        new Holiday(
                                LocalDate.of(2026, 1, 2),
                                "Новогодние каникулы",
                                false
                        )
                )
        );
    }

    @Test
    void shouldRejectInvalidMonth() {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertThrows(
                CalendarException.class,
                () -> calendar.printMonth(13)
        );
    }

    @Test
    void shouldRejectInvalidQuarter() {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertThrows(
                CalendarException.class,
                () -> calendar.countWorkingDaysInQuarter(5)
        );
    }

    @Test
    void shouldRejectInvalidPeriod() {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertThrows(
                CalendarException.class,
                () -> calendar.countWorkingDays(
                        LocalDate.of(2026, 5, 10),
                        LocalDate.of(2026, 5, 1)
                )
        );
    }
    @Test
    void shouldRejectNullPreHoliday() {
        ProductionCalendar calendar = new ProductionCalendar(2026);

        assertThrows(
                NullPointerException.class,
                () -> calendar.addPreHoliday(null)
        );
    }
}
