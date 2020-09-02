package com.sebix.couchbase_app.utils;

import com.sebix.couchbase_app.models.Numbers;

import java.util.ArrayList;

public class CalculatePrimeNumbers {
    public static ArrayList<Integer> calculate(Numbers numbers){
     ArrayList<Integer> primeNumbersList = new ArrayList<>();
        for (int i = numbers.getNumber1(); i < numbers.getNumber2(); i++) {
            boolean isPrime = true;
            if (i == 1)
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
}
