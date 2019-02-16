package com.comcast.algorithms;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by zzhou200 on 8/18/15.
 */
public class IteratorUtil {

    public static Iterable iterarorUnion(Iterator a, Iterator b) {
        if (a == null || b == null)
            return null;

        final Iterator fa = a;
        final Iterator fb = b;

        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {

                    private Iterator current;
                    private Object currentObjA;
                    private Object currentObjB;

                    @Override
                    public boolean hasNext() {
                        return currentObjA != null || currentObjB != null || fa.hasNext() || fb.hasNext();
                    }

                    @Override
                    public Object next() {
                        Object result;

                        if (current == null) {
                            if (!fa.hasNext()) {
                                current = fb;
                                if (current.hasNext()) {
                                    return current.next();
                                }
                                return null;
                            }

                            if (!fb.hasNext()) {
                                current = fa;
                                if (current.hasNext())
                                    return current.next();
                                return null;
                            }

                            currentObjA = fa.next();
                            currentObjB = fb.next();

                            if ((int)currentObjA <= (int)currentObjB) {
                                current = fa;
                                result = currentObjA;
                                currentObjA = null;
                                return result;
                            } else {
                                result = currentObjB;
                                currentObjB = null;
                                return result;
                            }
                        } else {
                            if (current == fa) {
                                if (current.hasNext()) {
                                    currentObjA = fa.next();
                                    if ((int)currentObjA <= (int)currentObjB) {
                                        current = fa;
                                        result = currentObjA;
                                        currentObjA = null;
                                        return result;
                                    } else {
                                        current = fb;
                                        result = currentObjB;
                                        currentObjB = null;
                                        return result;
                                    }
                                } else {
                                    current = fb;
                                    result = currentObjB;
                                    currentObjB = null;
                                    return result;
                                }
                            } else if (current == fb) {
                                if (current.hasNext()) {
                                    currentObjB = fb.next();
                                    if ((int)currentObjA <= (int)currentObjB) {
                                        current = fa;
                                        result = currentObjA;
                                        currentObjA = null;
                                        return result;
                                    } else {
                                        current = fb;
                                        result = currentObjB;
                                        currentObjB = null;
                                        return result;
                                    }
                                } else {
                                    current = fa;
                                    result = currentObjA;
                                    currentObjA = null;
                                    return result;
                                }
                            }
                        }

                        return null;
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        };
    }

    public static Iterable itreatorIntersect(Iterator a, Iterator b) {
        if (a==null || b==null)
            return null;

        final Iterator fa = a;
        final Iterator fb = b;
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    private Comparable currentObj;

                    @Override
                    public boolean hasNext() {
                        if (currentObj != null)
                            return true;

                        if (!fa.hasNext() || !fb.hasNext())
                            return false;

                        Comparable aObj = (Comparable)fa.next();
                        Comparable bObj = (Comparable)fb.next();

                        int cmp = aObj.compareTo(bObj);

                        if (cmp < 0) {
                            while (cmp < 0) {
                                aObj = (Comparable)fa.next();
                                cmp = aObj.compareTo(bObj);
                            }
                        } else if (cmp > 0) {
                            while (cmp > 0) {
                                bObj = (Comparable)fb.next();
                                cmp = (aObj.compareTo(bObj));
                            }
                        }

                        if (cmp == 0) {
                            currentObj = aObj;
                            return true;
                        }

                        currentObj = null;
                        return false;
                    }

                    @Override
                    public Object next() {
                        // call hasNext to prepare current in case it has not been called earlier
                        if (hasNext()) {
                            Object v = currentObj;
                            currentObj = null;
                            return v;
                        }

                        currentObj = null;
                        return null;
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        };
    }

    public static Iterable mergeKSortedIterators(Iterator[] iters) {
        final Iterator[] fIts = iters;

        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    class ItElement implements Comparable<ItElement> {
                        private Iterator it;
                        Integer current;

                        public ItElement(Iterator it) {
                            this.it = it;
                            next();
                        }

                        public Integer getCurrentValue() {
                            return current;
                        }

                        public Iterator getIterator() {
                            return it;
                        }

                        public boolean hasNext() {
                            return it.hasNext();
                        }

                        public void next() {
                            if (it.hasNext())
                                current = (Integer)this.it.next();
                            else
                                current = null;
                        }

                        @Override
                        public int compareTo(ItElement another) {
                            if (current == null)
                                return 1;
                            if (another.current == null)
                                return -1;
                            return current - another.current;
                        }
                    }

                    PriorityQueue<ItElement> pq = null;

                    @Override
                    public boolean hasNext() {
                        if (pq == null) {
                            for (int i=0; i<fIts.length; ++i)
                                if (fIts[i].hasNext())
                                    return true;
                            return false;

                        }

                        return !pq.isEmpty();
                    }

                    @Override
                    public Object next() {
                        if (pq == null) {
                            pq = new PriorityQueue<>();

                            for (int i = 0; i < fIts.length; ++i) {
                                if (fIts[i].hasNext())
                                    pq.add(new ItElement(fIts[i]));
                            }
                        }

                        if (!pq.isEmpty()) {
                            ItElement current = pq.remove();

                            // add next from the same iterator
                            if (current.hasNext())
                                pq.add(new ItElement(current.getIterator()));

                            return current.getCurrentValue();
                        }

                        return null;
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        };
    }
}
