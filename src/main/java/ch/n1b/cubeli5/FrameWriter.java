package ch.n1b.cubeli5;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created on 20.09.2014.
 *
 * @author Thomas
 */
public class FrameWriter implements AutoCloseable {

    private BufferedOutputStream outputStream = null;
    private ByteCubeFrame5D previousFrame = null;

    public FrameWriter(OutputStream outputStream){
        this.outputStream = new BufferedOutputStream(outputStream);
    }

    public void write(ByteCubeFrame5D frame) throws IOException {
        // next step, we can now write the previous frame
        ByteCubeFrame5D oldFrame = previousFrame;
        previousFrame = frame;
        // was this the first frame?
        if(oldFrame==null){
            previousFrame.setSceneStart(true);
        }else{
            ByteBuffer buf = oldFrame.buildCube();
            appendFrame(buf);
        }
    }

    private void appendFrame(ByteBuffer buf) throws IOException {
        buf.flip();
        byte[] buffer = new byte[buf.limit()];
        buf.get(buffer);
        outputStream.write(buffer);
    }

    @Override
    public void close() throws Exception {
        previousFrame.setFinalFrame(true);
        previousFrame.setSceneEnd(true);
        appendFrame(previousFrame.buildCube());
        outputStream.close();
    }
}
