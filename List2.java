public interface List2<T> {
    boolean add(T item);
    T get(int index);
    void add(int index, T item) throws Exception;
    T remove(int index) throws Exception;
    int size();
    //The following methods above are meant to be used by its implementors
    //LinkedList2 and ArrayList2
}
