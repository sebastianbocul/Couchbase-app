package com.sebix.couchbase_app.utils;

import com.sebix.couchbase_app.models.Numbers;

import java.util.ArrayList;

public class CalculatePrimeNumbers {
    public static ArrayList<Integer> calculatePrimeNumbersList(Numbers num) {
        Numbers numbers = new Numbers(num.getNumber1(), num.getNumber2());
        if (numbers.getNumber1() > numbers.getNumber2()) {
            int buffor;
            buffor = numbers.getNumber1();
            numbers.setNumber1(numbers.getNumber2());
            numbers.setNumber2(buffor);
        }
        ArrayList<Integer> primeNumbersList = new ArrayList<>();
        for (int i = numbers.getNumber1(); i < numbers.getNumber2(); i++) {
            boolean isPrime = true;
            if (i == 1||i==0)
                isPrime = false;
            else {
                // check to see if the numbers are prime
                for (int j = 2; j <= i / 2; j++) {
                    if (i % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
            // add to array
            if (isPrime) primeNumbersList.add(i);
        }
        return primeNumbersList;
    }

    public static boolean checkIsPrime(int number) {
        boolean isPrime = true;
        if (number == 1||number==0)
            isPrime = false;
        else {
            // check to see if the numbers are prime
            for (int j = 2; j <= number / 2; j++) {
                if (number % j == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        //return result
        return isPrime;
    }
}
