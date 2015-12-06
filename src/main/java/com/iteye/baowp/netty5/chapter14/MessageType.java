package com.iteye.baowp.netty5.chapter14;

/**
 * Created by baowp on 15-1-25.
 */
public enum MessageType {
    LOGIN_REQ((byte)3),
    LOGIN_RESP((byte)4),
    HEARTBEAT_REQ((byte)5),
    HEARTBEAT_RESP((byte)6);

    private byte type;

    private MessageType(byte type){
        this.type=type;
    }

    public byte value() {
        return type;
    }
}
