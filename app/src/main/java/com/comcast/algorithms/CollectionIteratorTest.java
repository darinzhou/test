package com.comcast.algorithms;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by zzhou200 on 8/15/15.
 */
public class CollectionIteratorTest {

    public interface Data<T> {
        // Does this Data hold a collection?
        public boolean isCollection();

        // Returns the collection contained by this Data, or null if it is a single element
        public Collection<Data<T>> getCollection();

        // Returns the single element contained by this Data, or null if it is a collection
        public T getElement();
    }

    public class DataIterator implements Iterator {
        private Data<Integer> data;
        private DataIterator pIt;
        private DataIterator it;

        public DataIterator(Data<Integer> data, DataIterator pIt) {
            this.pIt = pIt;
            if (data.isCollection()) {
                it = (DataIterator) data.getCollection().iterator();
            } else {
                it = null;
                this.data = data;
            }
        }

        public boolean hasNext() {
            if (data!=null)
                return true;

            if (it == null && pIt == null)
                return false;

            if (it != null)
                return it.hasNext();

            return pIt.hasNext();
        }

        public Data<Integer> next() {
            if (it == null)
                return data;

            data = it.next();
            if (data == null)
                it = pIt;
            else if (data.isCollection())
                it = new DataIterator(data, it);
            else
                return data;

            data = null;
            if (it != null && it.hasNext())
                data = it.next();

            return data;
        }

        @Override
        public void remove() {

        }
    }

    public class DataInerator2 implements Iterator {

        private Stack<Iterator> stack;
        private Iterator it;
        private Data<Integer> nextData;

        public DataInerator2(Data<Integer> data) {
            stack = new Stack<>();

            if (data != null) {
                while (data.isCollection()) {
                    it = data.getCollection().iterator();
                    nextData = (Data<Integer>) it.next();
                    if (nextData == null || !nextData.isCollection())
                        break;
                    stack.push(it);
                    data = nextData;
                }
            }

            // for now, nextData is not collection, "it" is the current iterator
        }

        @Override
        public boolean hasNext() {
            if (nextData != null && !nextData.isCollection())
                return true;
            return false;
        }

        @Override
        public Object next() {
            Integer result = nextData.getElement();

            nextData = null;

            if (it == null || !it.hasNext())
                if (!stack.isEmpty())
                    it = stack.pop();

            if (it!=null && it.hasNext()) {
                Data<Integer> data = (Data<Integer>) it.next();
                while (data.isCollection()) {
                    it = data.getCollection().iterator();
                    nextData = (Data<Integer>) it.next();
                    if (nextData == null || !data.isCollection())
                        break;
                    stack.push(it);
                    data = nextData;
                }
            }

            // for now, nexData is not collection, "it" is the current iterator

            return result;
        }

        @Override
        public void remove() {

        }
    }
}
