package com.storehouse.geetestSdk.enums;

public enum DigestmodEnum {

    MD5(0, "md5"), SHA256(1, "sha256"), HMAC_SHA256(2, "hmac-sha256");

    private int index;
    private String name;

    DigestmodEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
