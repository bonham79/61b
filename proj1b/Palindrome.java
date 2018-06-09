public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> palinDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); ++i) {
            palinDeque.addLast(word.charAt(i));
        }
        return palinDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque);
    }

    private boolean isPalindromeHelper(Deque wordDeque) {
        //Note, helper variable is destructive.  Make copy in isPalindrome if issue.
        if (wordDeque.size() <= 1) {
            return true;
        } else {
            Character first = (Character)wordDeque.removeFirst();
            Character last = (Character)wordDeque.removeLast();
            if (first.equals(last)) {
                return isPalindromeHelper(wordDeque);
            }
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque, cc);
    }

    private boolean isPalindromeHelper(Deque wordDeque, CharacterComparator cc) {
        //Note, helper variable is destructive.  Make copy in isPalindrome if issue.
        if (wordDeque.size() <= 1) {
            return true;
        } else {
            Character first = (char)wordDeque.removeFirst();
            //char charFirst = first.charValue();
            Character last = (char)wordDeque.removeLast();
            //char charLast = last.charValue;
            if (cc.equalChars(first, last)) {
                return isPalindromeHelper(wordDeque, cc);
            }
        }
        return false;
    }

}
