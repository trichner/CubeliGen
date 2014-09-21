package ch.n1b.cubeli5.cell;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 21.09.2014.
 *
 * @author Thomas
 */
public class NamedRules {
    static {
        // Create GoL ruleset via fancy new Java8 stuff
        Map<State,Boolean> rules = IntStream.range(0,128).parallel().boxed().collect(Collectors.toConcurrentMap(t -> new State(t), state -> {
            // check how many neighbors are alive
            int popcount = Integer.bitCount(state & 0xFE);
            // chek if the cell is currently alive
            boolean isAlive = (state & 0x01) != 0;
            // decide next state
            boolean next;
            if (isAlive) {
                next = popcount==3 || popcount==2;
            } else {
                next = popcount==3;
            }
            return next;
        }));
        GAME_OF_LIFE = Collections.unmodifiableMap(rules);
    }
    public static final Map<State,Boolean> GAME_OF_LIFE;
}
