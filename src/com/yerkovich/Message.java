package com.yerkovich;

import java.math.BigInteger;

public class Message {
    BigInteger[] message;

    public Message(BigInteger[] message) {
        this.message = message;
    }

    public BigInteger[] getMessage() {
        return message;
    }

    public void setMessage(BigInteger[] message) {
        this.message = message;
    }
}
