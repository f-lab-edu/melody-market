package com.melodymarket.domain.admin.enums;

public enum MembershipLevelEnum {
    BRONZE(1),
    SILVER(2),
    GOLD(3),
    PLATINUM(4);

    private final int level;

    MembershipLevelEnum(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    // 정수 값을 기반으로 해당 Enum 객체를 반환하는 메서드
    public static MembershipLevelEnum valueOf(int level) {
        for (MembershipLevelEnum membershipLevelEnum : values()) {
            if (membershipLevelEnum.getLevel() == level) {
                return membershipLevelEnum;
            }
        }
        throw new IllegalArgumentException("Invalid membership level: " + level);
    }
}
