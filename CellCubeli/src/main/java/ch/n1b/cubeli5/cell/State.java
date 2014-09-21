package ch.n1b.cubeli5.cell;

/**
 * Created on 21.09.2014.
 *
 * @author Thomas
 */
public class State {
    private byte state;

    public State(byte state) {
        this.state = state;
    }

    public State(int state) {
        this((byte) state);
    }

    public State(Integer state) {
        this(state.byteValue());
    }

    public byte getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state1 = (State) o;

        if (state != state1.state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) state;
    }

    public static class StateBuilder {
        private byte state = 0;

        public StateBuilder setBit(Direction3D direction3D, boolean on) {
            if (on) {
                state |= (1 << direction3D.bitoffset);
            } else {
                state &= ~(1 << direction3D.bitoffset);
            }
            return this;
        }

        public State build() {
            return new State(state);
        }
    }
}
