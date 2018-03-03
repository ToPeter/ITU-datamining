package Enums;

/**
 * Created by lucas on 3/20/2017.
 */
public enum Interest {
    not_interested(-0.5),
    meh(-0.167),
    sounds_interesting(+0.167),
    very_interesting(0.5);

    private double numVal;

    Interest(double numVal) {
        this.numVal = numVal;
    }

    public double getNumVal(){
        return numVal;
    }
}
