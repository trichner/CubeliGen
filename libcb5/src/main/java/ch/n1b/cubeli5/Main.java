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
        try(FrameWriter writer = new FrameWriter(new FileOutputStream("mycube.cb5"))){
            Random rand = new Random();
            int lastIntensity=0;
            for (int i = 0; i < 300; i++) {
                ByteCubeFrame5D frame = new ByteCubeFrame5D();
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        for (int z = 0; z < 5; z++) {
                            frame.set(x,y,z,rand.nextBoolean());
                        }
                    }
                }
                int intensity = lastIntensity-1+rand.nextInt(3);
                if(intensity<0 || intensity>5){
                    intensity = lastIntensity;
                }else {
                    lastIntensity = intensity;
                }
                frame.setIntensity(intensity);
                writer.write(frame);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
