package ru.netology.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Ticket;

import java.util.Arrays;
import java.util.Comparator;

public class AviaSoulsTest {

    AviaSouls aviaSouls = new AviaSouls();
    TicketTimeComparator ticketTimeComparator = new TicketTimeComparator();

    Ticket ticket1 = new Ticket("Ростов", "Сочи", 8000, 21, 1); //4
    Ticket ticket2 = new Ticket("Ростов", "Сочи", 4000, 24, 1); //1
    Ticket ticket3 = new Ticket("Москва", "Владикавказ", 7000, 3, 14);
    Ticket ticket4 = new Ticket("Краснодар", "Сочи", 5000, 13, 16);
    Ticket ticket5 = new Ticket("Ростов", "Сочи", 5000, 23, 2); //3
    Ticket ticket6 = new Ticket("Саратов", "Владикавказ", 10000, 10, 15);
    Ticket ticket7 = new Ticket("Ростов", "Сочи", 9000, 22, 23);  //2

    @BeforeEach
    public void setup() {
        // AviaSouls aviaSouls = new AviaSouls();

        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);
        aviaSouls.add(ticket4);
        aviaSouls.add(ticket5);
        aviaSouls.add(ticket6);

    }

    // Тесты на сравнение билетов
    @Test
    public void comparePayLess() {

        // Проверяем что стоимость второго билета меньше стоимости четвертого

        Assertions.assertEquals(-1, ticket2.compareTo(ticket4));
        System.out.println("Тест-1_Билет '" + ticket2.getFrom() + "-" + ticket2.getTo() + "' (" + ticket2.getPrice() + "р) дешевле чем билет '" + ticket4.getFrom() + "-" + ticket4.getTo() + "' (" + ticket4.getPrice() + "р)");
    }

    @Test
    public void comparePayMore() {

        // Проверяем что стоимость (например) шестого билета больше стоимости (например) пятого

        Assertions.assertEquals(1, ticket6.compareTo(ticket5));
        System.out.println("Тест-2_Билет '" + ticket6.getFrom() + "-" + ticket6.getTo() + "' (" + ticket6.getPrice() + "р) дороже чем билет '" + ticket5.getFrom() + "-" + ticket5.getTo() + "' (" + ticket5.getPrice() + "р)");
    }

    @Test
    public void comparePayEqual() { // Равная стоимость

        // Проверяем что стоимость четвертого и пятого билетов равны
        Assertions.assertEquals(0, ticket4.compareTo(ticket5));
        System.out.println("Тест-3_Билеты '" + ticket4.getFrom() + "-" + ticket4.getTo() + "' (" + ticket4.getPrice() + "р) и " + ticket5.getFrom() + "-" + ticket5.getTo() + "' (" + ticket5.getPrice() + "р) равны по стоимости");
    }

    @Test
    public void compareFlightTimeLess() { // Время полета "первого" меньше чем "второго"

        Assertions.assertEquals(-1, ticketTimeComparator.compare(ticket4, ticket3));

        System.out.println("Тест-4_Длительность полета '" + ticket4.getFrom() + "-" + ticket4.getTo() + "' (" + (ticket4.getTimeTo() - ticket4.getTimeFrom()) + "ч) меньше чем '" + ticket3.getFrom() + "-" + ticket3.getTo() + "' (" + (ticket3.getTimeTo() - ticket3.getTimeFrom()) + "ч)");
    }

    @Test
    public void compareFlightTimeMore() { //Время полета "первого" больше чем "второго"

        Assertions.assertEquals(1, ticketTimeComparator.compare(ticket1, ticket2));

        System.out.println("Тест-5_Длительность полета '" + ticket1.getFrom() + "-" + ticket1.getTo() + "' (" + ((ticket1.getTimeTo() - ticket1.getTimeFrom()) + 24) + "ч) больше чем '" + ticket2.getFrom() + "-" + ticket2.getTo() + "' (" + ((ticket2.getTimeTo() - ticket2.getTimeFrom()) + 24) + "ч)");
    }

    @Test
    public void compareFlightTimeEqual() { // время полета равно

        Assertions.assertEquals(0, ticketTimeComparator.compare(ticket4, ticket5));

        System.out.println("Тест-6_Длительность полета '" + ticket4.getFrom() + "-" + ticket4.getTo() + "' (" + (ticket4.getTimeTo() - ticket4.getTimeFrom()) + "ч) равна длительности полета  '" + ticket5.getFrom() + "-" + ticket5.getTo() + "' (" + ((ticket5.getTimeTo() - ticket5.getTimeFrom()) + 24) + "ч)");
    }


    // Тесты на поиск и сортировку билетов
    @Test
    public void searchManyTickets() { // Находим несколько билетов на одно направление

        Ticket[] expected = {ticket2, ticket5, ticket1};
        Ticket[] actual = aviaSouls.search("Ростов", "Сочи");

        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-7_По направлению 'Ростов-Сочи' найдено " + expected.length + " билета, стоимостью " + ticket2.getPrice() + "р, " + ticket5.getPrice() + "р и " + ticket1.getPrice() + "р.");
    }

    @Test
    public void searchOneTicket() { // находим только один билет (направление)

        aviaSouls.add(ticket7);

        Ticket[] expected = {ticket4};
        Ticket[] actual = aviaSouls.search("Краснодар", "Сочи");

        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-8_По направлению 'Краснодар-Сочи' найден " + expected.length + " билет, стоимостью " + ticket4.getPrice() + "р.");
    }

    @Test
    public void searchNothingTickets() { // Не находим ничего

        aviaSouls.add(ticket7);

        Ticket[] expected = {};
        Ticket[] actual = aviaSouls.search("Саратов", "Сочи");

        Assertions.assertArrayEquals(expected, actual);
        System.out.println("Тест-9_По направлению 'Саратов-Сочи' билетов не найдено.");
    }

    @Test
    public void searchAndSortTime() {
        Comparator<Ticket> comparator = new TicketTimeComparator();

//  Будем искать билеты из Ростова в Сочи и сортировать их по времени перелёта от меньшего к большему

        aviaSouls.add(ticket7);

        Ticket[] expected = {ticket2, ticket7, ticket5, ticket1};
        Ticket[] actual = aviaSouls.searchAndSortBy("Ростов", "Сочи", comparator);

        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-10_По направлению 'Ростов-Сочи' найдено " + expected.length + " билета, длительностью перелета: " + ((ticket2.getTimeTo() - ticket2.getTimeFrom()) + 24) + "ч, " + (ticket7.getTimeTo() - ticket7.getTimeFrom()) + "ч, " + (ticket5.getTimeTo() - ticket5.getTimeFrom() + 24) + "ч и " + (ticket1.getTimeTo() - ticket1.getTimeFrom() + 24) + "ч.");
    }
}
