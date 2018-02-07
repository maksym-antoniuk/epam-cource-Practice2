package ua.nure.antoniuk.Practice2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by Max on 04.11.2017.
 */
public class MyListImpl implements MyList, ListIterable {
    private Object[] array = new Object[2];
    private int lenght;



    @Override
    public void add(Object e) {
        ensureContains();
        array[lenght++] = e;
    }

    @Override
    public void clear() {
        array = new Object[2];
        lenght = 0;
    }

    @Override
    public boolean remove(Object o) {
        if(!contains(o)) return false;
        else{
            int point = getFirstOccurrence(o);
            Object[] temp = new Object[array.length];
            System.arraycopy(array, 0, temp, 0, point);
            System.arraycopy(array, point + 1, temp, point, array.length - point - 1);
            array = temp;
            lenght--;
            return true;
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, lenght);
    }

    @Override
    public int size() {
        return lenght;
    }

    @Override
    public boolean contains(Object o) {
        return getFirstOccurrence(o) >= 0;
    }

    @Override
    public boolean containsAll(MyList c) {
        //Object[] temp = toArray();
        Object[] subTemp = c.toArray();
        for(Object el : subTemp){
            //System.out.println(el);
            if(!contains(el)) {

                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for(int i = 0; i < lenght; i++){
            if (Objects.isNull(array[i])){
                s.append("null");
            } else {
                s.append(array[i].toString());
            }
            if(i != lenght - 1)
                s.append(", ");
        }
        s.append("]");
        return s.toString();
    }

    private void ensureContains(){
        if(lenght == array.length){
            Object[] temp = new Object[(int)(lenght * 1.5)];
            System.arraycopy(array, 0, temp, 0, lenght);
            array = temp;
        }
    }

    private int getFirstOccurrence(Object o) {
        for (int i = 0; i < lenght; i++){
            if (Objects.equals(array[i], o))
                return i;
        }
        return -1;
    }

    @Override
    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    @Override
    public ListIterator listIterator() {
        return new ListIteratorImpl();
    }

    private class ListIteratorImpl extends IteratorImpl implements ListIterator {
        private boolean f1;

        @Override
        public boolean hasPrevious() {
            if (index >= 0) {
                if (index == 0) {
                    f1 = false;
                }
            } else {
                //return false;
            }
            return flag;
        }

        @Override
        public Object previous() {
            flag = true;
            if(index == lenght) {
                index--;
            }
            Object get = array[index--];
            if(index == -1) flag = false;
            return get;
        }

        @Override
        public void set(Object e) {

        }
    }


    private class IteratorImpl implements Iterator<Object> {
        protected int index;
        protected boolean flag;

        // returns true if the iteration has more elements
        public boolean hasNext() {
            if(index < lenght) {
                return true;
            } else {
                return false;
            }
        }

        // returns the next element in the iteration
        public Object next() {
            flag = true;
            Object get = array[index++];
                //index++;
            return get;
        }

        // removes from the underlying collection the last element
        //returned by this iterator
        public void remove() {
            if(!flag) {
                throw new IllegalStateException();
            } else {
                flag = false;
                MyListImpl.this.remove(array[--index]);
            }
        }
    }

}
