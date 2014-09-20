package ch.n1b.cubeli5;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created on 19.09.2014.
 *
 * @author Thomas
 */
public class Main {
    public static void main(String[] args) {
        String filename = "somefile";
        try(FrameWriter writer = new FrameWriter(new FileOutputStream("mycube.cb5"))){
            Random rand = new Random();
            for (int i = 0; i < 200; i++) {
                ByteCubeFrame5D frame = new ByteCubeFrame5D();

                frame.set(rand.nextInt(5),rand.nextInt(5),rand.nextInt(5),true);
                writer.write(frame);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
