public class Array<T> {
    private T[] array;
    private int size;
    private int capacity;

    //TODO There is an issue with time complexity of add and remove methods
    // The time complexity of add and remove methods should be O(log(n))
    // ChatGpt suggests using BinaryTree with nodes instead

    public Array() {
        this(10);
    }

    public Array(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.array = (T[]) new Object[capacity];
    }

    // Time complexity: O(n)
    public void add(T element) {
        if (size == capacity) {
            T[] newArray = (T[]) new Object[capacity * 2]; // double the capacity
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i]; // copy the elements
            }
            array = newArray; // update the reference
            capacity *= 2;
        }
        array[size] = element; // add the element
        size++;
    }

    // Time complexity: O(n)
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
    }

    // Time complexity: O(1)
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    public T getLast() {
        return this.get(size - 1);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void clear() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

}
