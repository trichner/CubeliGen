package ch.n1b.cubeli5.cell;

import ch.n1b.cubeli5.FrameWriter;
import ch.n1b.cubeli5.Minions;

import java.io.FileOutputStream;

/**
 * Created on 21.09.2014.
 *
 * @author Thomas
 */
public class Main {
    public static void main(String[] args) {
        try(FrameWriter writer = new FrameWriter(new FileOutputStream("cell.cb5"))){
            // generate one scene
            CellularAutomaton automaton = new CellularAutomaton(NamedRules.GAME_OF_LIFE);
            writer.write(automaton.init(Minions.generateRandomCube()));
            for (int i = 0; i < 300; i++) {
                writer.write(automaton.iterate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
