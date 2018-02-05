package interfaces;
import java.util.List;

public interface Position<E> {
          
          public E getElement();
          public void setElement(E element);
          
          public Position<E> getParent();
          public void setParent(Position<E> parent);
          
          public List<Position<E>> getChildren();
          
          public void addChild(Position<E> child);
          public void removeChild(Position<E> child);
}