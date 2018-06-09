public class OffByN implements CharacterComparator {
    private int Diff;
    public OffByN(int N) {
        Diff = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return ((diff == Diff) || (diff == -Diff));
    }
}
