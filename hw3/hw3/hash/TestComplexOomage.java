package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        // Your code here.
        //tHE IDEA is to feed a series of parameters that forces continued powers of 256
        //Which means we just need a 1, followed by a series of 0's.
        List<Integer> params1 = new ArrayList<>();
        params1.add(1);//hash = 1;
        params1.add(0);//hash = 256;
        params1.add(0);//hash = 256^2
        params1.add(0);//hash = 256^3
        params1.add(0);//hash = 256^4
        ComplexOomage s1 = new ComplexOomage(params1);

        List<Integer> params2 = new ArrayList<>();
        params2.add(0);
        params2.add(1);
        params2.add(0);
        params2.add(0);
        params2.add(0);
        params2.add(0);
        ComplexOomage s2 = new ComplexOomage(params2);

        List<Integer> params3 = new ArrayList<>();
        params3.add(0);
        params3.add(0);
        params3.add(1);
        params3.add(0);
        params3.add(0);
        params3.add(0);
        params3.add(0);
        ComplexOomage s3 = new ComplexOomage(params3);

        ArrayList param4 = new ArrayList();
        param4.add(0);
        ComplexOomage s4 = new ComplexOomage(param4);

        deadlyList.add(s1);
        deadlyList.add(s2);
        deadlyList.add(s3);
        deadlyList.add(s4);

        for (int i = 0; i < 7; ++i) {deadlyList.add(ComplexOomage.randomComplexOomage());}
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
