package ru.netology.domain;

public class Main {
    public static void main(String[] args) {
        Ticket ticket1 = new Ticket("Москва", "Краснодар", 40000, 7, 11);
        Ticket ticket2 = new Ticket("Краснодар", "Сочи", 5000, 8, 9);
        Ticket ticket3 = new Ticket("Москва", "Дублин", 70000, 3, 14);
        Ticket ticket4 = new Ticket("Ростов", "Сочи", 40000, 7, 11);
        Ticket ticket5 = new Ticket("Сочи", "Владивосток", 80000, 6, 18);
        Ticket ticket6 = new Ticket("Саратов", "Владикавказ", 50000, 10, 15);

        System.out.println(ticket1.compareTo(ticket2));
    }
}