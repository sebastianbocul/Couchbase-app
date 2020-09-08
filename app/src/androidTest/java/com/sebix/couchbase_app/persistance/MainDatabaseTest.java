package com.sebix.couchbase_app.persistance;

import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.InstantTaskExecutorRule;
import com.sebix.couchbase_app.utils.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MainDatabaseTest {
    private MainDatabase mainDatabase;
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        mainDatabase = new MainDatabase(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void test_init_notNull() throws Exception {
        assertNotNull(mainDatabase);
    }

    @Test
    public void test_saveNumbers_getNumbers_returnTrue() throws Exception {
        //Arrange
        Numbers numbersExpected = new Numbers(1, 100);
        MutableLiveData<Resource<Numbers>> numberLD;
        assertNotNull(mainDatabase);
        //Act
        mainDatabase.saveNumbers(numbersExpected);
        Thread.sleep(100);
        numberLD = mainDatabase.getNumbers();
        System.out.println(numberLD);
        Thread.sleep(100);
        Numbers numbers = numberLD.getValue().data;
        //Assert
        System.out.println("numbers from db: " + numbers.toString() + "   expected: " + numbersExpected);
        Assert.assertEquals(numbers, numbersExpected);
    }

    @Test
    public void test_saveNumbersNegative_getNumbers_returnFalse() throws Exception {
        //Arrange
        //init numbers to make sure database !=null
        mainDatabase.saveNumbers(new Numbers(10, 10));
        Numbers numbersExpected = new Numbers(-5, 100);
        MutableLiveData<Resource<Numbers>> numberLD;
        assertNotNull(mainDatabase);
        //Act
        mainDatabase.saveNumbers(numbersExpected);
        Thread.sleep(100);
        numberLD = mainDatabase.getNumbers();
        System.out.println(numberLD);
        Thread.sleep(100);
        Numbers numbers = numberLD.getValue().data;
        //Assert
        System.out.println("numbers from db: " + numbers.toString() + "   expected: " + numbersExpected);
        Assert.assertNotEquals(numbers, numbersExpected);
    }
}
