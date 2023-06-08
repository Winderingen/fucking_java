import java.util.*;

public class HashTable<K, V> {
    private final int INITIAL_CAPACITY = 8; // начальная емкость массива 8
    private final double LOAD_FACTOR = 0.9;

    // Массив для хранения записей по ключу и значению создается приватным чтобы никто в него не залез
    private Entry<K, V>[] table;

    // Размер массива является приватным чтобы никто не мог его изменить
    private int size;

    public HashTable() {
        table = new Entry[INITIAL_CAPACITY]; // Создание массива с начальной емкостью 8
        size = 0; //задаем первоначальное значение size для пустого массива
    }

    // Выполнем хеширование ключа для определения индекса массива
    private int hash(K key) {
        int hashCode = key.hashCode();
        int idx = (hashCode ^ (hashCode >>> 16)) % table.length; // хэшируем побитово со сдвигом на 16 бит вправо

        return idx >= 0 ? idx : -idx; // проверка на неотрицательность индекса элемента
        // если индекс отрицательный, то функция возвращает его неотрицательное значение (-1) -> (1)
        // индекс может быть отрицательным если результат побитового XOR стал отрицательным
        // (при применении операции % к отрицательному числу, результат т.е. индекс будет отрицательным)

    }

    private void resize() {
        Entry<K, V>[] newTable = new Entry[table.length * 2]; // Создание нового массива с увеличенной емкостью
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                Entry<K, V> current = entry;
                int idx = hash(current.key);
                while (newTable[idx] != null) {
                    idx = (idx + 1) % newTable.length; // Решение коллизии методом цепочек
                    // Обновление значение переменной idx, добавляя к нему 1 и затем берет остаток от деления на размер новой таблицы
                    // Переход к следующей ячейке в хэш-таблице с учетом ее размера.
                }
                newTable[idx] = new Entry<>(current.key, current.value); // Перехеширование и добавление записей в новый массив
            }
        }
        table = newTable; // Замена старого массива новым
    }

    public void put(K key, V value) {
        if ((double) size / table.length >= LOAD_FACTOR) {
            resize();
        }

        int idx = hash(key); // Определение индекса массива
        while (table[idx] != null) {
            if (table[idx].key.equals(key)) { //получение ключа текущего элемента
                table[idx].value = value; // Если ключ уже существует в хеш-таблице, обновляем значение
                return;
            }
            idx = (idx + 1) % table.length; // Решение коллизии методом цепочек
            // Обновление значение переменной idx, добавляя к нему 1 и затем берет остаток от деления на размер новой таблицы
            // Переход к следующей ячейке в хэш-таблице с учетом ее размера.
        }

        table[idx] = new Entry<>(key, value); // Создание новой записи (ключ, элемент) в таблицу
        size++; // Размер хэш-таблицы +1 т.к. добавили элемент
    }

    public boolean remove(K key) {
        int idx = hash(key); // Определение индекса массива
        while (table[idx] != null) {
            if (table[idx].key.equals(key)) {
                table[idx] = null; // Удаление записи по индексу
                size--;
                rehash(); // Перехеширование оставшихся записей в случае, если они были сдвинуты
                return true;
            }
            idx = (idx + 1) % table.length; // Решение коллизии методом цепочек
            // Обновление значение переменной idx, добавляя к нему 1 и затем берет остаток от деления на размер новой таблицы
            // Переход к следующей ячейке в хэш-таблице с учетом ее размера.
        }
        return false; // Элемент не найден
    }

    private void rehash() {
        Entry<K, V>[] tempTable = table;
        table = new Entry[INITIAL_CAPACITY]; // Создание нового массива
        size = 0; // С размерностью 0
        for (Entry<K, V> entry : tempTable) {
            if (entry != null) {
                put(entry.key, entry.value); // Перехеширование и добавление записей в новый массив
            }
        }
    }

    public V get(K key) {
        int idx = hash(key); // Определение индекса массива по ключу
        while (table[idx] != null) {
            if (table[idx].key.equals(key)) {
                return table[idx].value; // Если найдена запись с искомым ключом, возвращаем значение
            }
            idx = (idx + 1) % table.length; // Решение коллизии методом цепочек
            // Обновление значение переменной idx, добавляя к нему 1 и затем берет остаток от деления на размер новой таблицы
            // Переход к следующей ячейке в хэш-таблице с учетом ее размера.
        }
        throw new NoSuchElementException("Ключ не был найден"); // Если элемент не найден, генерируем исключение
    }

    // Внутренний класс Entry для представления записей элементов в хеш-таблице
    private static class Entry<K, V> {
        K key; // даем понять таблице что K это ключ key
        V value; // даем понять таблице что V это значение value

        public Entry(K key, V value) {
            this.key = key; // привязываем значения ключа в таблице
            this.value = value; // привязываем значение элемента в таблице
        }
    }
}