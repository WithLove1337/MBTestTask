package MyList;

import java.util.Comparator;
import java.util.Optional;

public class MyList<T> implements AdvancedList<T>, AuthorHolder {

    private class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public MyList() {
        head = null;
        tail = null;
    }

    @Override
    public AdvancedList<T> shuffle() {
        MyList<T> sortedList = new MyList();

        Node current = head;
        Node last = current;
        while (current.next != null) {
            sortedList.add((T) current.next.data);
            current = current.next;
        }
        sortedList.add((T) last.data);
        return sortedList;
    }

    public AdvancedList<T> sort(Comparator<T> comparator) {
        MyList<T> sortedList = new MyList();
        Node temptail = tail;
        Node tempi = head;
        while (tempi != temptail.prev) {
            Node tempj = temptail;
            while (tempj != tempi) {
                if (comparator.compare((T) tempj.prev.data, (T) tempj.data) > 0) {
                    T switchable = (T) tempj.prev.data;
                    tempj.prev.data = tempj.data;
                    tempj.data = switchable;
                }
                tempj = tempj.prev;
            }
            tempi = tempi.next;
        }
        Node<T> temp = head;
        while (temp != null) {
            sortedList.add(temp.data);
            temp = temp.next;
        }
        return sortedList;
    }




    /*@Override
    public AdvancedList<T> sort(Comparator<T> comparator) {
        MyList<T> sortedList = new MyList();

        boolean statement = false;
        while (!statement) {
            statement = true;
            Node temp = head;
            while (temp.next != null) {
                T switchable;
                if ((comparator.compare((T) temp.data, (T) temp.next.data) > 0) || (temp.data == null)) {
                    switchable = (T) temp.data;
                    temp.data = temp.next.data;
                    temp.next.data = switchable;
                    statement = true;
                }
                temp = temp.next;
            }
        }

        Node current = head.next;
        while (current != null) {
            sortedList.add((T) current.data);
            current = current.next;
        }

        return sortedList;
    }*/

    @Override
    public String author() {
        return "Gorolevich Ivan";
    }

    @Override
    public void add(T item) {
        Node<T> temp = new Node (item);
        if (isEmpty())
            head = temp;
        else
            tail.next = temp;

        temp.prev = tail;
        tail = temp;
    }

    @Override
    public void insert(int index, T item) throws Exception {
        Node<T> cur = head;
        int c = 0;

        while (cur != null && c != index) {
            cur = cur.next;
            c++;
        }

        Node temp = new Node(item);
        cur.prev.next = temp;
        temp.prev = cur.prev;
        cur.prev = temp;
        temp.next = cur;
    }

    public void removeFirst() {
        if (head.next == null)
            tail = null;
        else
            head.next.prev = null;

        head = head.next;
    }

    public void removeLast() {
        if (head.next == null)
            head = null;
        else
            tail.prev.next = null;

        tail = tail.prev;
    }

    @Override
    public void remove(int index) throws Exception {
        Node<T> cur = head;
        int c = 0;

        while (cur != null && c != index) {
            cur = cur.next;
            c++;
        }

        if (cur == head)
            removeFirst();
        else
            cur.prev.next = cur.next;

        if (cur == tail)
            removeLast();
        else
            cur.next.prev = cur.prev;

    }

    @Override
    public Optional<T> get(int index) {
        Node<T> cur = head;
        int c = 0;

        while (cur != null && c != index) {
            cur = cur.next;
            c++;
        }
        return Optional.ofNullable(index < size() ? cur.data : null);
    }

    @Override
    public int size() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    @Override
    public void addAll(SimpleList<T> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isPresent())
                add(list.get(i).get());
        }
    }

    @Override
    public int first(T item) {
        Node<T> cur = tail;
        int count = 0;

        while (cur.data != item) {
            cur = cur.prev;
            count++;
        }

        return count;
    }

    @Override
    public int last(T item) {
        Node<T> cur = head;
        int count = 0;

        while (cur.data != item) {
            cur = cur.next;
            count++;
        }

        count = size() - count + 1;
        return count;
    }

    @Override
    public boolean contains(T item) {
        Node<T> temp = head;
        while (temp != null) {
            if (temp.data == item)
                return true;
            temp = temp.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    public void print() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}