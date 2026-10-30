package ru.student.productioncalendar;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws CalendarException {
        ProductionCalendar calendar = new ProductionCalendar(2026);


        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 1), "Новый год", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 2), "Новогодние каникулы", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 3), "Новогодние каникулы", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 4), "Новогодние каникулы", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 1, 7), "Рождество Христово", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 2, 23), "День защитника Отечества", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 3, 8), "Международный женский день", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 5, 1), "Праздник Весны и Труда", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 5, 9), "День Победы", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 6, 12), "День России", false
        ));
        calendar.addHoliday(new Holiday(
                LocalDate.of(2026, 11, 4), "День народного единства", false
        ));

        calendar.addPreHoliday(LocalDate.of(2026, 2, 20));

        System.out.println("Тип предпраздничного дня: "
                + calendar.getDayType(LocalDate.of(2026, 2, 20)));

        System.out.println("Рабочие часы в предпраздничный день: "
                + calendar.getDayType(LocalDate.of(2026, 2, 20)).workingHours());

        System.out.println("Производственный календарь на "
                + calendar.getYear() + " год");

        LocalDate date = LocalDate.of(2026, 1, 1);

        System.out.println("Дата: " + date);
        System.out.println("Тип дня: " + calendar.getDayType(date));
        System.out.println("\nСписок праздников:");

        for (Holiday holiday : calendar.getHolidays()) {
            System.out.println(
                    holiday.date()
                            + " (" + holiday.date().getDayOfWeek() + ")"
                            + " — " + holiday.name()
            );
        }

        System.out.println("Рабочих дней в январе: "
                + calendar.countWorkingDays(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        ));

        System.out.println("Рабочих часов в январе: "
                + calendar.countWorkingHours(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        ));

        System.out.println("Рабочих дней в I квартале: "
                + calendar.countWorkingDaysInQuarter(1));

        System.out.println("Рабочих часов в I квартале: "
                + calendar.countWorkingHoursInQuarter(1));

        System.out.println("Рабочих дней за год: "
                + calendar.countWorkingDaysInYear());

        System.out.println("Рабочих часов за год: "
                + calendar.countWorkingHoursInYear());

        calendar.printMonth(1);
    }
}