package sin.lib;

public class Vector {

    /**
     * Keep in mind that sometimes magnitude and angle have not been calculated, so ideally use
     * getMagnitude() and getAngle() at least once to ensure they have been calculated before you use them
     * in this class, just to be safe. Probably though, this class won't need to be modified much more so,
     * not that big of a deal. Actually, I would very much prefer if you didn't modify this class at all.
     **/

    float angle, magnitude, horiz, vert;
    boolean angleCalced, magnitudeCalced, directionCalced;
    Direction direction;

    /**
     * CONSTRUCTOR 1 - From components.
     * Boolean parameters for calculating angle and magnitude, because you might need one and not the other.
     * Saves computing power.
     **/
    public Vector(float horiz, float vert, boolean calculateAngleNow, boolean calculateMagnitudeNow) {
        if (calculateAngleNow) {
            this.angle = (float) Math.atan2(vert, horiz);
            angleCalced = true;
        }
        if (calculateMagnitudeNow) {
            this.magnitude = (float) Math.sqrt(Math.pow(horiz, 2) - Math.pow(vert, 2));
            magnitudeCalced = true;
        }
        this.horiz = horiz;
        this.vert = vert;
    }

    /**
     * CONSTRUCTOR 2 - From angle and magnitude.
     * The angle parameter must follow this format.
     *      In radians.
     *      In what I will call ATAN2 form. See the documentation above getVariable() for more explanation.
     * If need be, use the static methods in this class, as well as Math.toRadians()
     * to convert it to the proper form before calling this constructor.
     **/
    public Vector(float angle, float magnitude) {
        this.angle = angle;
        angleCalced = true;
        this.magnitude = magnitude;
        magnitudeCalced = true;
        this.horiz = (float) (magnitude * Math.cos(angle));
        this.vert = (float) (magnitude * Math.sin(angle));
    }

    // CONSTRUCTOR 3 - Refers to CONSTRUCTOR 2.
    public Vector(float xo, float yo, float xf, float yf, float magnitude) {
        this((float) Math.atan2(yf - yo, xf - xo), magnitude);
    }

    /**
     * The angle variable in this vector class is stored in what I will call ATAN2 form. Keep in mind I only use
     * degrees here because they make more sense. This class functions in radians.
     * See below some demonstration of what I mean with some example angles around the unit circle in each angle form.
     * I hope you can make sense of it. Perhaps I've overcomplicated it.
     * 360 form: Ranging from 0 to 360.
     * 		FIRST QUADRANT
     * 		0 degrees (verticalComponent: 0, horizontalComponent: 10)
     * 		26.5 degrees (verticalComponent: 5, horizontalComponent: 10)
     * 		45 degrees (verticalComponent: 10, horizontalComponent: 10)
     * 		63.5 degrees (verticalComponent: 10, horizontalComponent: 5)
     * 		SECOND QUADRANT
     * 		90 degrees (verticalComponent: 10, horizontalComponent: 0)
     * 		116.5 degrees (verticalComponent: 10, horizontalComponent: -5)
     * 		135 degrees (verticalComponent: 10, horizontalComponent: -10)
     * 		153.5 degrees (verticalComponent: 5, horizontalComponent: -10)
     * 		THIRD QUADRANT
     * 		180 degrees (verticalComponent: 0, horizontalComponent: -10)
     * 		206.5 degrees (verticalComponent: -5, horizontalComponent: -10)
     * 		225 degrees (verticalComponent: -10, horizontalComponent: -10)
     * 		243.5 degrees (verticalComponent: -10, horizontalComponent: -5)
     * 		FOURTH QUADRANT
     * 		270 degrees (verticalComponent: -10, horizontalComponent: 0)
     * 		296.5 degrees (verticalComponent: -10, horizontalComponent: 5)
     * 		315 degrees (verticalComponent: -10, horizontalComponent: 10)
     * 		333.5 degrees (verticalComponent: -5, horizontalComponent: 10)
     * ATAN function.
     * 		FIRST QUADRANT
     * 		Math.toDegrees(Math.atan(0.0/10.0)) returns 0 degrees.
     * 		Math.toDegrees(Math.atan(5.0/10.0)) returns 26.5 degrees.
     * 		Math.toDegrees(Math.atan(10.0/10.0)) returns 45 degrees.
     * 		Math.toDegrees(Math.atan(10.0/5.0)) returns 63.5 degrees.
     * 		SECOND QUADRANT
     * 		Math.toDegrees(Math.atan(10.0/0.0)) causes an error: undefined.
     * 		Math.toDegrees(Math.atan(10.0/-5.0)) returns -63.5 degrees.
     * 		Math.toDegrees(Math.atan(10.0/-10.0)) returns -45 degrees.
     * 		Math.toDegrees(Math.atan(5.0/-10.0)) returns -26.5 degrees.
     * 		THIRD QUADRANT
     * 		Math.toDegrees(Math.atan(.0/-10.0)) returns 0 degrees.
     * 		Math.toDegrees(Math.atan(-5.0/-10.0)) returns 26.5 degrees.
     * 		Math.toDegrees(Math.atan(-10.0/-10.0)) returns 45 degrees.
     * 		Math.toDegrees(Math.atan(-10.0/-5.0)) returns 63.5 degrees.
     * 		FOURTH QUADRANT
     * 		Math.toDegrees(Math.atan(-10.0/0.0)) causes an error: undefined.
     * 		Math.toDegrees(Math.atan(-10.0/5.0)) returns -63.5 degrees.
     * 		Math.toDegrees(Math.atan(-10.0/10.0)) returns -45 degrees.
     * 		Math.toDegrees(Math.atan(-5.0/10.0)) returns -26.5 degrees.
     * 		Math.toDegrees(Math.atan(-5.0/10.0)) returns -26.5 degrees.
     * ATAN2 function:
     * 		FIRST QUADRANT
     * 		Math.toDegrees(Math.atan2(0, 10)) returns 0 degrees.
     * 		Math.toDegrees(Math.atan2(5, 10)) returns 26.5 degrees.
     * 		Math.toDegrees(Math.atan2(10, 10)) returns 45 degrees.
     * 		Math.toDegrees(Math.atan2(10, 5)) returns 63.5 degrees.
     * 		SECOND QUADRANT
     * 		Math.toDegrees(Math.atan2(10, 0)) returns 90 degrees.
     * 		Math.toDegrees(Math.atan2(10, -5)) returns 116.5 degrees.
     * 		Math.toDegrees(Math.atan2(10, -10)) returns 135 degrees.
     * 		Math.toDegrees(Math.atan2(5, -10)) returns 153.5 degrees.
     * 		THIRD QUADRANT
     * 		Math.toDegrees(Math.atan2(0, -10)) returns 180 degrees
     * 		Math.toDegrees(Math.atan2(-5, -10)) returns -153.5 degrees.
     * 		Math.toDegrees(Math.atan2(-10, -10)) returns -135 degrees.
     * 		Math.toDegrees(Math.atan2(-10, -5)) returns -116.5 degrees.
     * 		FOURTH QUADRANT
     * 		Math.toDegrees(Math.atan2(-10, 0)) returns -90 degrees.
     * 		Math.toDegrees(Math.atan2(-10, 5)) returns -63.5 degrees.
     * 		Math.toDegrees(Math.atan2(-10, 10)) returns -45 degrees.
     * 		Math.toDegrees(Math.atan2(-5, 10)) returns -26.5 degrees.
     * This final chunk of code above, is the form in which getAngle() returns the angle
     * stored in this vector object. It is of course, in radians instead of degrees however.
     **/
    public float getAngle() {
        if (!angleCalced) {
            angle = (float) Math.atan2(vert, horiz);
            angleCalced = true;
        }
        return angle;
    }

    // Checks if magnitude has been calculated, and calculates it if hasn't.
    public float getMagnitude() {
        if (!magnitudeCalced) {
            magnitude = (float) Math.sqrt(Math.pow(horiz, 2) + Math.pow(vert, 2));
            magnitudeCalced = true;
        }
        return magnitude;
    }

    public float getHorizComp() {
        return horiz;
    }

    public float getVertComp() {
        return vert;
    }

    // Checks if direction has been calculated, and calculates it if hasn't.
    // TODO make switch
    public Direction getDirection() {
        if(!directionCalced) {
            float pi = (float) Math.PI;
            float p8 = pi / 8;
            if(getAngle() >= -p8 && getAngle() < p8) {
                direction = Direction.W;
                return direction;
            } else if(angle >= p8 && angle < 3 * p8) {
                direction = Direction.SE;
                return direction;
            } else if(angle >= 3 * p8 && angle < 5 * p8) {
                direction = Direction.S;
                return direction;
            } else if(angle >= 5 * p8 && angle < 7 * p8) {
                direction = Direction.SW;
                return direction;
            } else if(angle >= 7 * p8 || angle < -7 * p8) {
                direction = Direction.E;
                return direction;
            } else if(angle >= -7 * p8 && angle < -5 * p8) {
                direction = Direction.NW;
                return direction;
            } else if(angle >= -5 * p8 && angle < -3 * p8) {
                direction = Direction.N;
                return direction;
            } else if(angle >= -3 * p8 && angle < -p8) {
                direction = Direction.NE;
                return direction;
            }
            directionCalced = true;
        }
        return direction;
    }

    // Based on the positiveness or negativeness of the horizontal and vertical components.
    // Doesn't save in the class because its static. Only player really is going to use this.
    // Use this only for objects that can only move 8 directions, because
    // it will only return N S E W if there is no other component of movement at all besides
    // the respective direction.
    public static Direction getRoughDirection(float horiz, float vert) {
        if(horiz < 0) {
            if (vert < 0) return Direction.NW;
            if (vert == 0) return Direction.E;
            else return Direction.SW;
        } else if (horiz == 0) {
            if (vert < 0) return Direction.N;
            if (vert == 0) return Direction.None;
            else return Direction.S;
        } else {
            if (vert < 0) return Direction.NE;
            if (vert == 0) return Direction.W;
            else return Direction.SE;
        }
    }

    // Takes an angle in 360 form, returns angle in ATAN2 form. See the documentation above getAngle().
    // Uses radians. Call Math.toRadians() if need be.
    // Perhaps calling it 360 form is confusing when really, this parameter takes
    // a radian value from 0 to 2 * pi.
    public static float atan2From360(float angle) {
        float pi = (float) Math.PI;
        if(angle >= 0 && angle <= pi) {
            return angle;
        } else if (angle <= 2 * pi) {
            return angle - (2 * pi);
        }
        return atan2From360(angle - (2 * pi));
    }

}
