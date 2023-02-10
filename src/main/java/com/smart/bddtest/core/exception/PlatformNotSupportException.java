package com.smart.bddtest.core.exception;

public class PlatformNotSupportException extends RuntimeException {

    public PlatformNotSupportException(String platform) {
        super("Not support platform: " + platform);
    }

}
