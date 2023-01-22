package org.godfather.fastconfig.universal;

public final class WorldUtils {

    public static float getNearestYaw(float yaw) {
        if (yaw >= -22.5 && yaw < 22.5) {
            return 0.0f;
        } else if (yaw >= 22.5 && yaw < 67.5) {
            return 45.0f;
        } else if (yaw >= 67.5 && yaw < 112.5) {
            return 90.0f;
        } else if (yaw >= 112.5 && yaw < 157.5) {
            return 135.0f;
        } else if (yaw >= 157.5 || yaw < -157.5) {
            return 180.0f;
        } else if (yaw >= -157.5 && yaw < -112.5) {
            return -135.0f;
        } else if (yaw >= -112.5 && yaw < -67.5) {
            return -90.0f;
        } else return -45.0f;
    }

    public static double getAdjusted(double coordinate) {
        double rounded = Math.round(coordinate * 10.0) / 10.0;
        int intPart = Integer.parseInt(String.valueOf(rounded).split("\\.")[0]);
        double decimalPart = Integer.parseInt(String.valueOf(rounded).split("\\.")[1]) * 0.1;

        if (decimalPart >= 0 && decimalPart <= 0.2)
            return intPart;

        else if (decimalPart > 0.2 && decimalPart < 0.8)
            if (intPart < 0)
                return intPart - 0.5;
            else return intPart + 0.5;

        else if (decimalPart >= 0.8)
            if (intPart < 0)
                return intPart - 1;
            else return intPart + 1;

        else if (intPart < 0)
            return intPart - 0.5;
        else return intPart + 0.5;
    }
}
