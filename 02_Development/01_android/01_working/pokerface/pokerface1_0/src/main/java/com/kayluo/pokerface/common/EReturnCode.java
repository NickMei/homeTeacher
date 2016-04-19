package com.kayluo.pokerface.common;

/**
 * Created by Nick on 2016-04-12.
 */
public enum EReturnCode {

        SUCCESS(0),
        USER_EXPIRED(2),
        UNKNOWN_ERROR(999);

        private final int value;

        private EReturnCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
}
