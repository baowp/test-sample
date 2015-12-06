package com.iteye.baowp.netty5.chapter14;

/**
 * Created by baowp on 15-1-25.
 */
public final class NettyMessage {
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String toString() {
        return "NettyMessage [header=" + header + "]";
    }
}
