import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("Notapalindrome"));
        assertFalse(palindrome.isPalindrome("Not a palindrome"));

        CharacterComparator offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("ab", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("aAb", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("racecar", offByOne));
        assertFalse(palindrome.isPalindrome("Not a palindrome", offByOne));


    }


}
