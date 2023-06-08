import java.util.*;

public class Main {
    public static void main(String[] args) {
        // создаем хэш таблицу и задаем ключу элемента тип Integer - число
        // значению элемента тип String - строку
        HashTable<Integer, String> hashTable = new HashTable<>();
//  работа с хэш таблицей:
//  put - добавление элемента
//  get - взятие элемента
//  remove - удаление элемента

        // Добавление элементов (key,value)
        // key - ключ элемента хэш-таблицы
        // value - значение элемента хэш таблицы

        hashTable.put(1, "10");
        hashTable.put(2, "20");
        hashTable.put(3, "30");

        // Получение элемента таблицы по ключу данного элемента
        System.out.println("Поиск элемента таблицы по ключу 1");
        String get_value = hashTable.get(1);
        System.out.println("Значение ключа:" + get_value + "\n");


        // Удаление элемента по ключу
        System.out.println("Удаление элемента по ключу 2");
        boolean removed = hashTable.remove(2);
        System.out.println("Удаление элемента ключа: " + removed);

        // Попытка получить удаленный элемент по ключу
        System.out.println("Пытаемся получить значение удаленного элемента...");
        try {
            String remove_value = hashTable.get(2);
            System.out.println("Значение удаленного элемента: " + remove_value);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }
}