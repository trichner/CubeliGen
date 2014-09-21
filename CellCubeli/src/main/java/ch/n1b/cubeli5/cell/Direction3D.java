package ch.n1b.cubeli5.cell;

/**
 * Created on 21.09.2014.
 *
 * @author Thomas
 */
public enum Direction3D {
    ORIGIN(new int[]{0, 0, 0}, 0),
    ZENIT(new int[]{0, 0, 1}, 1),
    NADIR(new int[]{0, 0, -1}, 2),
    NORTH(new int[]{0, 1, 0}, 3),
    EAST(new int[]{1, 0, 0}, 4),
    SOUTH(new int[]{0, -1, 0}, 5),
    WEST(new int[]{-1, 0, 0}, 6);
    public final int x, y, z;
    public final int bitoffset;

    Direction3D(int[] xyz, int bitoffset) {
        this.x = xyz[0];
        this.y = xyz[1];
        this.z = xyz[2];
        this.bitoffset = bitoffset;
    }

}
