package ch.n1b.cubeli5;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created on 21.09.2014.
 *
 * @author Thomas
 */
public class Minions {

    public static ByteCubeFrame5D generateRandomFrame(){
        ByteCubeFrame5D frame = new ByteCubeFrame5D();
        frame.set(generateRandomCube());
        return frame;
    }

    public static boolean[][][] generateRandomCube(){
        Random rand = new Random();
        boolean[][][] cube = new boolean[5][5][5];
        IntStream.range(0, 5).parallel().forEach(x -> IntStream.range(0, 5).forEach(
                y -> IntStream.range(0, 5).forEach(z -> cube[z][y][x]=rand.nextBoolean())));
        return cube;
    }
}
