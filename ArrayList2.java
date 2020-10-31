public class ArrayList2<T> implements List2<T> {
    //These are the data members of an ArrayList, size and arr
    int size=0;
    T[] arr;
    //This is the constructor that is used to create the ArrayList
    public ArrayList2(){
        arr=(T[]) new Object[10];
        size=0;
    }
    //The method growArray is used incase the array ever runs out of space by
    //creating an array double the size and putting the elements back in
    private void growArray() {
        T[] arr2 = (T[]) new Object[this.arr.length*2];
        for(int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }
        arr = arr2;
    }
    /*
    The add method takes an item to add and adds it to the end of the list
    and returns true because it will always add
     */
    @Override
    public boolean add(T item){
        if(size >= arr.length) {
            growArray();
            arr[size] = item;
        }
        else {
            arr[size] = item;
        }
        size++;
        return true;
    }
    /*
    The get method returns the specific index at an array,
    makes sure the index is also valid
     */
    @Override
    public T get(int index) {
        if(index >= arr.length || index < 0) {
            return null;
        }
        return arr[index];
    }
    /*
        The add method takes in a certain index, makes sure the index is valid
        otherwise it will throw an error otherwise it will shift the array positions
        by one and then add the item at that position
     */
    @Override
    public void add(int index, T item) throws Exception {
        if(index >= arr.length || index < 0) {
            throw new Exception("Position out of bounds");
        }

        if(size >= arr.length) {
            growArray();
        }

        T t = item;

        for(int i = index-1; i < size; i++) {
            T t2 = arr[i+1];
            arr[i+1] = t;
            t = t2;
        }
        this.size++;
    }
    /*
        It will first check if the inputted index is a valid index, otherwise
        it will throw an error, then it will take the positon and shift all the elements
        to the right side of it to replace it and returns the value of the removed item
     */
    @Override
    public T remove(int index) throws Exception {
        if(index >= arr.length || index < 0) {
            throw new Exception("Position: out of bounds");
        }

        T val = this.arr[index];

        for(int i = index; i < arr.length-1; i++) {
            arr[i] = arr[i+1];
        }

        arr[arr.length-1] = null;
        size--;
        return val;
    }
    //size returns the size
    @Override
    public int size() {
        return size;
    }
}
