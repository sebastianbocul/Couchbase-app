package com.sebix.couchbase_app.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NumbersTest {
    @Test
    void isNotesEqual_identicalProperties_returnTrue() throws Exception {
        //Arrange
        Numbers numbers1 = new Numbers(1,2);
        Numbers numbers2 = new Numbers(1,2);
        //Act
        //Assert
        assertEquals(numbers1,numbers2);
        System.out.println("The numbers are equal!");
    }

    @Test
    void isNotesEqual_differentNumber2_returnFalse() throws Exception {
        //Arrange
        Numbers numbers1 = new Numbers(1,2);
        Numbers numbers2 = new Numbers(1,10);
        //Act
        //Assert
        assertNotEquals(numbers1,numbers2);
        System.out.println("The numbers are not equal!");
    }

    @Test
    void isNotesEqual_differentNumbers_returnFalse() throws Exception {
        //Arrange
        Numbers numbers1 = new Numbers(1,2);
        Numbers numbers2 = new Numbers(5,3);
        //Act
        //Assert
        assertNotEquals(numbers1,numbers2);
        System.out.println("The numbers are not equal!");
    }
}
