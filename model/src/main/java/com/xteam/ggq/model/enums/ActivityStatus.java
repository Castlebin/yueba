package com.xteam.ggq.model.enums;

/**
 * Created by admin on 2016-04-09.
 */
public enum ActivityStatus {
    ALL(0), // 所有状态
    BEFORE(1), // 报名已截止但活动未开始
    IN_PROGRESS(2), // 活动进行中
    FINISH(3), // 活动已经结束
    IN_ENROLLMENT(4);// 报名尚未截止

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
            case 4:
                return IN_ENROLLMENT;
            default:
                return null;
        }
    }

    public int value() {
        return this.statusCode;
    }
}
