package logic;

import java.util.Arrays;

public class Tower {
    private final Rod[] rods;
    private int moves;

    //Constructor
    public Tower (int nrOfDisk)
    {
        if (nrOfDisk <= 0) throw new IllegalArgumentException("Illegal number of disk");

        rods = new Rod[RodPos.values().length];
        rods[RodPos.LEFT.getValue()] = new Rod(nrOfDisk);
        rods[RodPos.MIDDLE.getValue()] = new Rod(nrOfDisk);
        rods[RodPos.RIGHT.getValue()] = new Rod(nrOfDisk);

        for (int i = nrOfDisk; i > 0; i--)
        {
            Disk temp = new Disk(Color.BLACK, i);
            rods[RodPos.LEFT.getValue()].push(temp);
        }

        moves = 0;
    }

    //Method
    public void initNewGame()
    {
        int diskCount =
                rods[RodPos.LEFT.getValue()].getNumberOfDisks() +
                rods[RodPos.MIDDLE.getValue()].getNumberOfDisks() +
                rods[RodPos.RIGHT.getValue()].getNumberOfDisks();

        rods[RodPos.LEFT.getValue()] = new Rod(diskCount);
        rods[RodPos.MIDDLE.getValue()] = new Rod(diskCount);
        rods[RodPos.RIGHT.getValue()] = new Rod(diskCount);

        for (int i = diskCount; i > 0; i--)
        {
            Disk temp = new Disk(Color.BLACK, i);
            rods[RodPos.LEFT.getValue()].push(temp);
        }

        moves = 0;
    }

    public boolean isLegalMove(RodPos src, RodPos des)
    {
        Disk target = rods[src.getValue()].peek();

        return rods[des.getValue()].canPush(target);
    }

    public void makeMove(RodPos src, RodPos des)
    {
        if (!isLegalMove(src, des)) throw new IllegalStateException("Illegal moves");

        Disk target = rods[src.getValue()].pop();
        rods[des.getValue()].push(target);

        moves++;
    }

    public boolean isSolved()
    {
        return rods[RodPos.LEFT.getValue()].isEmpty() && rods[RodPos.MIDDLE.getValue()].isEmpty();
    }

    //Getter
    public Disk[] getDisks(RodPos pos)
    {
        return rods[pos.getValue()].copyDiskArray();
    }

    public int getNoOfDisks()
    {
        return rods[RodPos.LEFT.getValue()].getNumberOfDisks()
            + rods[RodPos.MIDDLE.getValue()].getNumberOfDisks()
            + rods[RodPos.RIGHT.getValue()].getNumberOfDisks();
    }

    public int getMoves()
    {
        return moves;
    }

    @Override
    public String toString() {
        return "Tower{" +
                "rods=" + Arrays.toString(rods) +
                ", moves=" + moves +
                '}';
    }
}
