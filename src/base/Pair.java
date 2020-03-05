package base;

public final class Pair<E, T> {
    public E first;
    public T second;

    public Pair(E first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
