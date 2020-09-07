package com.sebix.couchbase_app.utils;

import com.sebix.couchbase_app.models.Numbers;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatePrimeNumbersTest {
    @Test
    public void test_input_calculatePrimeNumbersList() throws Exception {
        //Arrange
        Numbers numbers = new Numbers(1, 10);
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(2, 3, 5, 7));
        //Act
        primeNumbers = CalculatePrimeNumbers.calculatePrimeNumbersList(numbers);
        //Assert
        System.out.println("Prime numbers from " + numbers.getNumber1() + " to " + numbers.getNumber2() + " are :" + primeNumbers.toString());
        assertArrayEquals(primeNumbers.toArray(), expected.toArray());
    }

    @Test
    public void test_inputReverseNumbers_calculatePrimeNumbersList() throws Exception {
        //Arrange
        Numbers numbers = new Numbers(10, 1);
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(2, 3, 5, 7));
        //Act
        primeNumbers = CalculatePrimeNumbers.calculatePrimeNumbersList(numbers);
        //Assert
        System.out.println("Prime numbers from " + numbers.getNumber1() + " to " + numbers.getNumber2() + " are :" + primeNumbers.toString());
        assertArrayEquals(primeNumbers.toArray(), expected.toArray());
    }

    private ArrayList<Boolean> set1 = new ArrayList<>(Arrays.asList(false, false, true, true, false, true, false, true, false, false));

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void test_input_calculatePrimeNumbers(int number) throws Exception {
        //Arrange
        //Act
        boolean result = CalculatePrimeNumbers.checkIsPrime(number);
        //Assert
        assertEquals(result, set1.get(number));
    }

    @Test
    public void test_inputNegative_return() throws Exception {
        //Arrange
        int number = -1;
        //Act
        boolean result = CalculatePrimeNumbers.checkIsPrime(number);
        //Assert
        assertEquals(result, true);
    }

    @Test
    public void test_input_9999999_return() throws Exception {
        //Arrange
        int number = 9999999;
        //Act
        boolean result = CalculatePrimeNumbers.checkIsPrime(number);
        //Assert
        assertEquals(result, false);
    }
}