package com.sebix.couchbase_app.persistance;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.repository.InstantTaskExecutorRule;
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
    public void ini2t() {
        mainDatabase = new MainDatabase(ApplicationProvider.getApplicationContext());
        Application application = new Application();

//        mainDatabase=new MainDatabase(application);
    }


    @Test
    void init_notNull() throws Exception {
        assertNotNull(mainDatabase);
    }

    @Test
    public void name() throws Exception {
        //Arrange
        Numbers numbersExpected = new Numbers(1,100);
        MutableLiveData<Resource<Numbers>> numberLD;
        mainDatabase.init();
        assertNotNull(mainDatabase);
        //Act
        mainDatabase.saveNumbers(numbersExpected);
        Thread.sleep(1000);
        numberLD=mainDatabase.getNumbers();
        System.out.println(numberLD);
        Thread.sleep(1000);
        Numbers numbers = numberLD.getValue().data;
        //Assert
        System.out.println("numbers from db: " + numbers.toString() + "   expected: "+ numbersExpected);
        Assert.assertEquals(numbers,numbersExpected);
    }

}
