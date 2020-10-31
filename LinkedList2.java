public class LinkedList2<T> implements List2<T> {
    //These are the data members that every LinkedList should have
    int size;
    Node<T> head;
    Node<T> tail;
    //Constructor is used to initialize LinkedList which sets head and tail to null
    public LinkedList2(){
        head=tail=null;
    }
    //add method takes an item of whatever type and adds it
    //to the end of the linkedlist and will return true at
    //the end because it won't fail to add
    @Override
    public boolean add(T item) {
        Node<T> node = new Node<T>(item);

        if(head == null) {
            head = node;
            tail = node;
        }

        else {
            tail.next = node;
            tail = node;
        }
        size++;
        return true;
    }
    //get method will take an index and make sure
    //that the index is valid, if it is
    //it will then iterate through the forloop
    //and return once we get to the incremented position
    @Override
    public T get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }

        Node<T> curr = head;
        for(int i = 0; i < index; i++) {
            curr = curr.next;
        }

        return curr.value;
    }
    /*
     The add method will take an index, make sure it is valid and then if it is valid
     will loop through the list until we get to the index and add the item
     by changing up the pointers.
     */
    @Override
    public void add(int index, T item) throws Exception {
        if(index < 0 || index >= size) {
            throw new Exception("Position out of bounds");
        }
        Node<T> t = new Node<T>(item);

        if(index == 0) {
            Node<T> temp = head;
            head = t;
            head.next = temp;
        }
        else {
            Node<T> curr = head;

            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
            }

            t.next = curr.next;
            curr.next = t;
        }
        size++;
    }
    //get method will take an index and make sure
    //that the index is valid, if it is
    //it will then iterate through the forloop
    //once we get to the index it will redirect the pointers
    //so that we can't access the removed index
    @Override
    public T remove(int index) throws Exception {
        if(this.head == null) {
            throw new Exception("Cannot remove from empty list");
        }

        if(index < 0 || index >= size) {
            throw new Exception("Position out of bounds");
        }

        if(index == 0) {
            Node<T> t = head;
            head = t.next;
            size--;
            return t.value;
        }

        Node<T> curr = this.head;
        for(int i = 0; i < index - 1; i++) {
            curr = curr.next;
        }

        Node<T> temp = curr.next;
        curr.next = temp.next;
        size--;
        return temp.value;
    }
    //will just return the size
    @Override
    public int size() {
        return size;
    }
    //Crates the definition of a Node class with a value and next pointer
    public class Node<T> {
        T value;
        Node<T> next;
        public Node(T value) {
            this.next = null;
            this.value = value;
        }
    }
}
