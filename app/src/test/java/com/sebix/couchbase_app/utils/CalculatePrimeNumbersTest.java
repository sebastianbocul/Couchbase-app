package com.sebix.couchbase_app.utils;

import com.sebix.couchbase_app.models.Numbers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatePrimeNumbersTest {
    @Test
    public void calculate() throws Exception {
        //Arrange
        Numbers numbers = new Numbers(1,10);
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(2,3,5,7));
        //Act
        primeNumbers=CalculatePrimeNumbers.calculate(numbers);
        //Assert
        System.out.println("Prime numbers from 1 to 10 are: " + primeNumbers.toString());
        assertArrayEquals(primeNumbers.toArray(),expected.toArray());
    }
}