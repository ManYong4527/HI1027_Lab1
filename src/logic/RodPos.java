package logic;

public enum RodPos {
    LEFT(0), MIDDLE(1), RIGHT(2);

    private final int value;

    private RodPos(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
