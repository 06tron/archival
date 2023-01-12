package mpc.structs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Box<E> implements Iterable<E> {

  private List<Iterable<E>> contents;
  
  public Box() {
    contents = new ArrayList<>();
  }
  
  public void put(Iterable<E> item) {
    contents.add(item);
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      
      private int index;
      private Iterator<E> subIterator;
      
      {
        index = 0;
        subIterator = contents.get(index).iterator();
      }

      @Override
      public boolean hasNext() {
        return index < contents.size() - 1 || subIterator.hasNext();
      }

      @Override
      public E next() {
        try {
          if (!subIterator.hasNext()) {
            subIterator = contents.get(++index).iterator();
          }
          return subIterator.next();
        }
        catch (IndexOutOfBoundsException e) {
          throw new NoSuchElementException();
        }
      }
      
    };
  }
  
}

class Example implements Iterable<Example> {

  @Override
  public Iterator<Example> iterator() {
    return new Iterator<Example>() {
      
      private boolean hasNext = true;

      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public Example next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        hasNext = false;
        return Example.this;
      }
      
    };
  }
  
}
