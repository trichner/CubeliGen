package ch.n1b.cubeli5.cell;

import ch.n1b.cubeli5.ByteCubeFrame5D;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created on 21.09.2014.
 *
 * @author Thomas
 */
public class CellularAutomaton {
    private ByteCubeFrame5D state = new ByteCubeFrame5D();
    private Map<State, Boolean> rulemap;

    public CellularAutomaton(Map<State, Boolean> rulemap) {
        this.rulemap = rulemap;
    }

    /**
     * @param cube [z][y][x]
     * @return
     */
    public ByteCubeFrame5D init(boolean[][][] cube) {
        if (cube.length == 5 && cube[0].length == 5 && cube[0][0].length == 5) {
            state.set(cube);
        } else {
            throw new IllegalArgumentException("malformed cube, must be 5x5x5");
        }

        return state;
    }

    public ByteCubeFrame5D iterate() {
        ByteCubeFrame5D nextState = new ByteCubeFrame5D();
        IntStream.range(0, 5).parallel().forEach(x -> IntStream.range(0, 5).forEach(
                y -> IntStream.range(0, 5).forEach(z -> nextState.set(x, y, z, evaluateCell(x, z, y)))));
        state = nextState;
        return state;
    }

    public ByteCubeFrame5D getState() {
        return state;
    }


    private boolean evaluateCell(int x, int y, int z) {
        State.StateBuilder stateBuilder = new State.StateBuilder();
        Arrays.stream(Direction3D.values()).forEach(d -> {
            boolean on = false;
            int tx = x + d.x, ty = y + d.y, tz = z + d.z;
            if (inRange(tx, ty, tz)) {
                on = state.get(tx, ty, tz);
            }
            stateBuilder.setBit(d, on);
        });
        return rulemap.get(stateBuilder.build());
    }

    private static final boolean inRange(int... xyz) {
        for (int i : xyz) {
            if (i < 0 || i > 4) {
                return false;
            }
        }
        return true;
    }
}
