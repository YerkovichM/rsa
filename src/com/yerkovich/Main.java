package com.yerkovich;

public class Main {

    public static void main(String[] args) {
        Encryptor encryptor = new Encryptor();
        Message message = encryptor.encrypt("message");
        System.out.println(message);
        System.out.println(encryptor.decrypt(message));
    }
}
