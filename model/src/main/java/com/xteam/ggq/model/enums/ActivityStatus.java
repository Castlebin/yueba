package com.xteam.ggq.model.enums;

/**
 * Created by admin on 2016-04-09.
 */
public enum ActivityStatus {
    ALL(0), BEFORE(1), IN_PROGRESS(2), FINISH(3);

    private int statusCode = 0;

    ActivityStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static ActivityStatus valueOf(int value) {
        switch (value) {
            case 0:
                return ALL;
            case 1:
                return BEFORE;
            case 2:
                return IN_PROGRESS;
            case 3:
                return FINISH;
            default:
                return null;
        }
    }

    public int value() {
        return this.statusCode;
    }
}
