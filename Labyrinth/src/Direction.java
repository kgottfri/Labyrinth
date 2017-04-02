/**
 * Created by Eric on 3/25/2017.
 */
public enum Direction {
    up(0), right(1), down(2), left(3);

    private final int value;
    private Direction(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
