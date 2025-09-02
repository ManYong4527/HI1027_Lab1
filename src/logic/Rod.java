package logic;

import java.util.Arrays;

public class Rod {
    private final Disk[] disks;
    private int n;

    //Constructor
    public Rod(int initialCapacity)
    {
        this.disks = new Disk[initialCapacity];
        this.n = 0;
    }

    //Method
    public boolean canPush(Disk d)
    {
        if (isEmpty()) return true;
        Disk top = disks[n - 1];
        return d.diameter() <= top.diameter();
    }

    public boolean isEmpty()
    {
        return n == 0;
    }

    public void push(Disk d)
    {
        if (!canPush(d)) throw new IllegalStateException("Can't push the disk");

        disks[n] = d;
        n++;
    }

    public Disk pop()
    {
        if (isEmpty()) throw new IllegalStateException("Rod is empty, Can't pop the disk");

        Disk top = disks[n - 1];
        disks[n - 1] = null;
        n--;
        return top;
    }

    public Disk peek(){return disks[n - 1];}

    //Getter
    public Disk[] copyDiskArray()
    {
        Disk[] copy = new Disk[n];
        System.arraycopy(disks, 0, copy, 0, copy.length);
        return copy;
    }

    public int getNumberOfDisks(){return n;}

    @Override
    public String toString()
    {
        return "Rod: " + Arrays.toString(disks) + ", n = " + n;
    }
}
