import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void ArrayDequeTest() {
       StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque();
       ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution();
       for (int i = 0; i < 500; ++i) {
           int rand = StdRandom.uniform(500);
           int firstLast = StdRandom.uniform(1);
           if (firstLast == 1) {
               studentDeque.addFirst(rand);
               solutionDeque.addFirst(rand);
           } else {
               studentDeque.addLast(rand);
               solutionDeque.addLast(rand);
           }
       }
        for (int i = 0; i < 500; ++i) {
            int firstLast = StdRandom.uniform(1);
            if (firstLast == 1) {  assertEquals((Integer) solutionDeque.removeFirst(), (Integer) studentDeque.removeFirst());
            } else { assertEquals((Integer) solutionDeque.removeLast(), (Integer) studentDeque.removeLast());}
        }
    }
}
