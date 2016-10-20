import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
        
    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        // cast needed since no generic array creation in Java
        q = (Item[]) new Object[2];
    }     
    public boolean isEmpty()                 // is the queue empty?
    {
        return N==0;
    }    
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    
    
    public int size() {
        return N;
    }
    // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }
    public void enqueue(Item item)           // add the item
    {
        // double size of array if necessary and recopy to front of array
        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        N++;
    }
    public Item sample()                     // return a random item (but do not remove it)
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int random1 = StdRandom.uniform(0, N);
        return q[(first+random1)%q.length];
    }
    public Item dequeue()                    // remove and return a random item   
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int random2 = StdRandom.uniform(0, N);
        //find a random position to remove, switch the item from selected to first, then remove first
        int selected=(first+random2)%q.length;
        Item item = q[selected];
        q[selected] = q[first];
        q[first]=null;
        N--;
        first++;
        if (first == q.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) resize(q.length/2); 
        return item;
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        //StdRandom.shuffle(q);
        //q = (Item[]) new Object[2];
        //private Item[] rq = (Item[]) new Object[q.length];            // queue elements
        private Item[] rq = q;
        public ArrayIterator()                 // construct an empty randomized queue
        {
            StdRandom.shuffle(rq);
        }            
        private int i = 0;
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = rq[(i + first) % rq.length];
            i++;
            return item;
        }
    }
    public static void main(String[] args)   // unit testing (required)
    {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}