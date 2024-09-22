package ru.kravchenko.testFramework;

import ru.kravchenko.TestClass;
import ru.kravchenko.testFramework.annotations.After;
import ru.kravchenko.testFramework.annotations.Before;
import ru.kravchenko.testFramework.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestRunner {
    private static Class testClass;

    private static class Result {
        int testCount = 0;
        int testSuccess = 0;
        int testFail = 0;

        public Result(int testCount) {
            this.testCount = testCount;
        }
    }

    public static void main(String[] args) {
        try {
            run("ru.kravchenko.TestClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void run(String testClassName) throws ClassNotFoundException {
        testClass = Class.forName(testClassName);

        Method[] methods = testClass.getDeclaredMethods();
        Method[] testMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Test.class))
                .toArray(Method[]::new);
        Method[] beforeMetods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Before.class))
                .toArray(Method[]::new);
        Method[] afterMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(After.class))
                .toArray(Method[]::new);
        Result result = runMethods(testMethods, beforeMetods, afterMethods);
        printTestResult(result);
    }

    private static Result runMethods(Method[] testMethods, Method[] beforeMethods, Method[] afterMethods) {
        Result result = new Result(testMethods.length);

        for (Method method : testMethods) {
            try {
                Object instance = testClass.getDeclaredConstructor().newInstance();
                System.out.printf("Тестируемый метод: %s; hashCode: %d %n", method.getName(), instance.hashCode());
                boolean success = true;
                try {
                    runBeforeMethod(beforeMethods, instance);
                    runTestMethod(method, instance);
                } catch (Exception e) {
                    System.out.println(e.getCause().getMessage());
                    success = false;
                } finally {
                    try {
                        runAfterMethod(afterMethods, instance);
                    } catch (Exception e) {
                        System.out.println(e.getCause().getMessage());
                        success = false;
                    }
                }
                if (success) {
                    result.testSuccess += 1;
                } else {
                    result.testFail += 1;
                }
            } catch (Exception e) {
                System.out.println(e.getCause().getMessage());
            }
        }
        return result;
    }

    private static void runTestMethod(Method method, Object instance) throws Exception {
        method.invoke(instance);
    }

    private static void runBeforeMethod(Method[] beforeMethods, Object instance) throws Exception {
        for (Method method : beforeMethods) {
            method.invoke(instance);
        }
    }

    private static void runAfterMethod(Method[] afterMethods, Object instance) throws Exception {

        for (Method method : afterMethods) {
            method.invoke(instance);
        }
    }

    private static void printTestResult(Result result) {
        System.out.printf("Всего тестов запущено: %d; Успешно выполнено: %d; Выполнено с ошибкой: %d %n", result.testCount, result.testSuccess, result.testFail);
    }
}
