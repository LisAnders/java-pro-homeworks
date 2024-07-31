package homework;

import java.util.*;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить
public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private NavigableMap<Customer, String> navMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> smallest = navMap.firstEntry();

        return smallest != null ? new AbstractMap.SimpleEntry<>(new Customer(smallest.getKey()), smallest.getValue()) : null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = navMap.higherEntry(customer);

        return next != null ? new AbstractMap.SimpleEntry<>(new Customer(next.getKey()), next.getValue()) : null;
    }

    public void add(Customer customer, String data) {
        navMap.put(customer, data);
    }
}
