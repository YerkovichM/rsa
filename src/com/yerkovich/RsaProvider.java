package com.yerkovich;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RsaProvider {

    private static List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    private BigInteger[] keys;

    public RsaProvider() {
        generateRsaKeys();
    }

    public static BigInteger encodeMessage(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    public BigInteger encodeMessage(BigInteger message) {
        return encodeMessage(message, keys[0], keys[2]);
    }

    public BigInteger decodeMessage(BigInteger encodedMessage) {
        return encodedMessage.modPow(keys[1], keys[2]);
    }

    public BigInteger[] getPublicKey(){
        BigInteger[] pub = new BigInteger[2];
        pub[0] = keys[0];
        pub[1] = keys[2];
        return pub;
    }

    private void generateRsaKeys(){
        keys = new BigInteger[3];

        BigInteger p = PrimeGenerator.generatePrime(512);
        BigInteger q = PrimeGenerator.generatePrime(512);
        BigInteger n = p.multiply(q);
        BigInteger fN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = generateRalativePrime(fN);
        BigInteger d = multiInverse(e, fN);

        keys[0] = e;
        keys[1] = d;
        keys[2] = n;
    }

    private BigInteger generateRalativePrime(BigInteger fN) {
        for (int i = 0; i < primes.size(); i++) {
            BigInteger check = BigInteger.valueOf(primes.get(i));
            BigInteger previous = fN;
            while (check.compareTo(BigInteger.ZERO) > 0){
                BigInteger temp = previous.divideAndRemainder(check)[1];
                if(temp.equals(BigInteger.ONE)){
                    return BigInteger.valueOf(primes.get(i));
                }
                previous = check;
                check = temp;
            }
        }
        return null;
    }

    private BigInteger multiInverse(BigInteger num, BigInteger mod) {
        ArrayList<BigInteger> n = new ArrayList<>();
        n.add(mod);
        n.add(num);
        ArrayList<BigInteger> a = new ArrayList<>();
        a.add(BigInteger.ZERO);
        a.add(BigInteger.ONE);
        for (int i = 1; n.get(i).compareTo(BigInteger.ONE)>0; i++) {
            BigInteger[] divideAndRemainder = n.get(i - 1).divideAndRemainder(n.get(i));
            n.add(divideAndRemainder[1]);
            a.add(a.get(i - 1).subtract(a.get(i).multiply(divideAndRemainder[0])));
        }
        return a.get(a.size() - 1).mod(mod);
    }
}
