package com.yerkovich;

import java.math.BigInteger;

public class Encryptor {

    RsaProvider rsa;

    public Encryptor() {
        this.rsa = new RsaProvider();
    }

    public Message encrypt(String message) {
        char[] chars = message.toCharArray();
        BigInteger[] mes = new BigInteger[chars.length];
        for (int i = 0; i < chars.length; i++) {
            mes[i] = rsa.encodeMessage(BigInteger.valueOf(chars[i]));
        }
        return new Message(mes);
    }

    public String decrypt(Message message){
        BigInteger[] message1 = message.getMessage();
        char[] mes = new char[message1.length];
        for (int i = 0; i < message1.length; i++) {
            mes[i] = (char) rsa.decodeMessage(message1[i]).intValue();
        }
        return String.valueOf(mes);
    }
}
