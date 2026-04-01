package CSCI1933P3;
public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] array;
    private int size;
    private boolean isSorted;
    private static final int startCapacity = 2;

    public ArrayList() {
        array = (T[]) new Comparable[startCapacity];
        size = 0;
        isSorted = true;
    }
    //helper method to enlargen array when full
    private void growArray() {
        int newCapacity = array.length * 2;
        T[] newArray = (T[]) new Comparable[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
    private void updateIsSorted() {
        isSorted = true;
        for(int i = 0; i < size-1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                isSorted = false;
                break;
            }
        }
    }


    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        if (size == array.length) {
            growArray();
        }

        if (isSorted && size > 0 && array[size - 1].compareTo(element) > 0) {
            isSorted = false;
        }
        array[size] = element;
        size++;
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null) return false;
        if (index < 0 || index > size) return false;


        if (size == array.length) {
            growArray();
        }


        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }

        array[index] = element;
        size++;




        updateIsSorted();
        return true;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

    @Override
    public void clear() {
        array = (T[]) new Comparable[startCapacity];
        size = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return array[index];
    }

    @Override
    public int indexOf(T element) {
        if (element == null) return -1;

        if (isSorted) {

            for (int i = 0; i < size; i++) {
                if (array[i].compareTo(element) > 0) return -1;
                if (array[i].equals(element)) return i;
            }
            return -1;
        } else {

            for (int i = 0; i < size; i++) {
                if (array[i].equals(element)) return i;
            }
            return -1;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void sort() {
        if (size <= 1) {
            isSorted = true;
            return;
        }


        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < size - 1; i++) {
                if (array[i].compareTo(array[i + 1]) > 0) {
                    T temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);

        isSorted = true;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) return null;

        T removed = array[index];


        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;


        updateIsSorted();


        return removed;
    }

    @Override
    public void reverse() {
        int left = 0;
        int right = size - 1;
        while (left < right) {
            T temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
        updateIsSorted();
    }

    @Override
    public void removeDuplicates() {
        if (size == 0) return;

        int rIndex = 0;
        for (int i = 0; i < size; i++) {
            boolean duplicateFound = false;
            for (int j = 0; j < rIndex; j++) {
                if (array[i].equals(array[j])) {
                    duplicateFound = true;
                    break;
                }
            }
            if (!duplicateFound) {
                array[rIndex] = array[i];
                rIndex++;
            }
        }

        for (int i = rIndex; i < size; i++) {
            array[i] = null;
        }
        size = rIndex;
        updateIsSorted();
    }
    @Override
    public void intersect(List<T> otherList) {
        if (otherList == null) return;


        this.sort();
        otherList.sort();

        ArrayList<T> result = new ArrayList<>();

        int i = 0, j = 0;
        while (i < this.size && j < otherList.size()) {
            T a = this.array[i];
            T b = otherList.get(j);
            int cmp = a.compareTo(b);
            if (cmp == 0) {
                if (result.isEmpty() || !result.get(result.size() - 1).equals(a)) {
                    result.add(a);
                }
                i++;
                j++;
            } else if (cmp < 0) {
                i++;
            } else {
                j++;
            }
        }


        this.array = (T[]) new Comparable[result.array.length];
        for (int k = 0; k < result.size; k++) {
            this.array[k] = result.array[k];
        }
        this.size = result.size;
        this.isSorted = true;
    }

    @Override
    public void merge(List<T> otherList) {
        if (otherList == null) return;


        this.sort();
        otherList.sort();

        ArrayList<T> result = new ArrayList<>();

        int i = 0, j = 0;
        while (i < this.size && j < otherList.size()) {
            T a = this.array[i];
            T b = otherList.get(j);
            if (a.compareTo(b) <= 0) {
                result.add(a);
                i++;
            } else {
                result.add(b);
                j++;
            }
        }


        while (i < this.size) {
            result.add(this.array[i++]);
        }
        while (j < otherList.size()) {
            result.add(otherList.get(j++));
        }


        this.array = (T[]) new Comparable[result.array.length];
        for (int k = 0; k < result.size; k++) {
            this.array[k] = result.array[k];
        }
        this.size = result.size;
        this.isSorted = true;
    }

    @Override
    public T getMin() {
        if (size == 0) return null;
        if (isSorted) {
            return array[0];
        }
        T min = array[0];
        for (int i = 1; i < size; i++) {//loops through array and updates array as is goes
            if (array[i].compareTo(min) < 0) {
                min = array[i];
            }
        }
        return min;
    }

    @Override
    public T getMax() {
        if (size == 0) return null;
        if (isSorted) {
            return array[size - 1];
        }
        T max = array[0];
        for (int i = 1; i < size; i++) {//loops through and updates max as is
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(array[i]).append("\n");
        }
        return result.toString();
    }

}



