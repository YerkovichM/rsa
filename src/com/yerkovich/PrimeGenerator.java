package com.yerkovich;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class PrimeGenerator {

    private static List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);

    public static BigInteger generatePrime(int bitsLength) {
        BigInteger number = new BigInteger(bitsLength, new Random());
//        int frequency = (int) (bitsLength / Math.log(bitsLength));
        for (; true; number = number.add(BigInteger.ONE)) {
            BigInteger finalNum = number;
            boolean present = primes.stream()
                    .anyMatch(p -> finalNum.mod(BigInteger.valueOf(p)).equals(BigInteger.ZERO));
            if (present) {
                continue;
            }

            if (!fermaTest(5, finalNum)) {
                continue;
            }

            if (millerRabinTest(5, bitsLength, finalNum)) {
                return finalNum;
            }
        }
    }

    private static boolean millerRabinTest(int firstPrime, int bitsLength, BigInteger finalNum) {
        BigInteger nMin = finalNum.subtract(BigInteger.ONE);
        BigInteger d = nMin;
        BigInteger i = BigInteger.ZERO;
        for (; i.compareTo(BigInteger.valueOf(bitsLength)) <= 0; i = i.add(BigInteger.ONE)) {
            BigInteger[] divAndRem = d.divideAndRemainder(BigInteger.TWO);
            if (!divAndRem[1].equals(BigInteger.ZERO)) {
                break;
            }
            d = divAndRem[0];
        }
        for (int j = 0; j < firstPrime; j++) {
            BigInteger pow = BigInteger.valueOf(primes.get(j)).modPow(d, finalNum);
            if (pow.equals(BigInteger.ONE) || pow.equals(nMin)) {
                return true;
            }
            BigInteger I = i;
            for (; I.compareTo(BigInteger.ZERO) > 0; I = I.subtract(BigInteger.ONE)) {
                pow = pow.pow(2);
                if (pow.mod(finalNum).equals(nMin)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean fermaTest(int firstPrimes, BigInteger num) {
        for (int i = 0; i < firstPrimes; i++) {
            BigInteger prime = BigInteger.valueOf(primes.get(i));
            boolean isPrime = prime.modPow(num.subtract(BigInteger.ONE), num)
                    .equals(BigInteger.ONE);
            if (!isPrime) {
                return false;
            }
        }
        return true;
    }
}
