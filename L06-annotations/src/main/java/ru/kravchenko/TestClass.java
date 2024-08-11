package ru.kravchenko;


import ru.kravchenko.testFramework.annotations.After;
import ru.kravchenko.testFramework.annotations.Before;
import ru.kravchenko.testFramework.annotations.Test;

public class TestClass {

    @Before
    public void firstBefore() {
        System.out.println("Запущен первый метод с аннотацией Before");
    }
    @Before
    public void secondBefore() {
        System.out.println("Запущен второй метод с аннотацией Before");
    }
    @Before
    public void thirdBefore() {
        System.out.println("Запущен третий метод с аннотацией Before");
    }

    @Test
    public void firstTest(){
        System.out.println("Запущен первый метод с аннотацией Test");
    }
    @Test
    public void secondTest(){
        System.out.println("Запущен второй метод с аннотацией Test");
    }
    @Test
    public void thirdTest(){
        System.out.println("Запущен третий метод с аннотацией Test");
    }

    @After
    public void after(){
        System.out.println("Запущен метод с аннотацией After");
    }
}
