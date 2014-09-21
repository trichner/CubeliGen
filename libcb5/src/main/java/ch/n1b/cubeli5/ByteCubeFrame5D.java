package ch.n1b.cubeli5;

import java.nio.ByteBuffer;

/**
 * Created on 19.09.2014.
 *
 * @author Thomas
 */
public class ByteCubeFrame5D {
    private static final int SIZE = 5;

    private boolean[][][] cube5 =
           {{{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false}},
            {{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false}},
            {{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false}},
            {{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false}},
            {{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false}}};

    private int intensity = 5;

    private boolean finalFrame = false;

    private boolean sceneStart = false;
    private boolean sceneEnd = false;

    public void set(int x, int y, int z, boolean on){
        cube5[z][y][x] = on;
    }
    public void set(boolean[][][] cube5){
        this.cube5 = cube5;
    }
    public boolean get(int x,int y,int z){
        return cube5[z][y][x];
    }

    private ByteBuffer buildPlane(boolean[][] plane){
        // 4 bytes per plane
        ByteBuffer buffer = ByteBuffer.allocate(4);

        int bitoffset = 7;
        byte aByte = 0;
        for (int y = 0; y < plane.length; y++) {
            for (int x = 0; x < plane[0].length; x++) {
                if(plane[y][x]){
                    aByte |= 1 << bitoffset;
                }
                bitoffset--;
                // 7bits enough, write it
                if(bitoffset<0){
                    buffer.put(aByte);
                    bitoffset=7;
                    aByte = 0;
                }
            }
        }
        // write the last byte
        buffer.put(aByte);
        buffer.flip();
        return buffer;
    }

    public ByteBuffer buildCube(){
        ByteBuffer buffer = ByteBuffer.allocate(4*SIZE);
        // write all planes
        for (int z = 0; z < cube5.length; z++) {
            buffer.put(buildPlane(cube5[z]));
        }
        // add the intensity
        addIntensityBit(buffer);
        // add all the flags
        addSceneStartFlag(buffer);
        addSceneEndFlag(buffer);
        addFinalFlag(buffer);
        return buffer;
    }

    private void addIntensityBit(ByteBuffer buffer){
        if(intensity<5){
            final int OFFSET = 7;
            setByteOffset(buffer,OFFSET,1 << (4-intensity));
        }
    }

    private void addFinalFlag(ByteBuffer buffer){
        if(finalFrame){
            // should be the last byte
            final int OFFSET = 19;
            setByteOffset(buffer,OFFSET,0x01);
        }
    }

    private void addSceneStartFlag(ByteBuffer buffer){
        if(sceneStart){
            final int OFFSET = 3;
            setByteOffset(buffer,OFFSET,0x01);
        }
    }

    private void addSceneEndFlag(ByteBuffer buffer){
        if(sceneEnd){
            final int OFFSET = 19;
            setByteOffset(buffer,OFFSET,0x02);
        }
    }

    private void setByteOffset(ByteBuffer buffer,int offset, int aByte){
        byte bByte = buffer.get(offset);
        bByte |= (byte) aByte;
        buffer.put(offset,bByte);
    }

    public void setIntensity(int intensity){
        if(intensity<= 5 && intensity>=0){
            this.intensity = intensity;
        }else {
            throw new IllegalArgumentException("Out of range: [0,5]!="+intensity);
        }
    }

    public boolean isFinalFrame() {
        return finalFrame;
    }

    public void setFinalFrame(boolean finalFrame) {
        this.finalFrame = finalFrame;
    }

    public boolean isSceneStart() {
        return sceneStart;
    }

    public void setSceneStart(boolean sceneStart) {
        this.sceneStart = sceneStart;
    }

    public boolean isSceneEnd() {
        return sceneEnd;
    }

    public void setSceneEnd(boolean sceneEnd) {
        this.sceneEnd = sceneEnd;
    }
}
