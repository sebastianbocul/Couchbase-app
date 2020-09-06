package com.sebix.couchbase_app.models;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.repositories.MainRepository;
import com.sebix.couchbase_app.utils.InstantExecutorExtension;
import com.sebix.couchbase_app.utils.Resource;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.mock;

@ExtendWith({InstantExecutorExtension.class})
public class MainDatabaseTest {
    private MainDatabase mMainDatabase;

    @BeforeEach
    public void init(){
        mMainDatabase = mock(MainDatabase.class);
    }

    @Test
    public void name() throws Exception {
        //Arrange
        Numbers numbersExpected = new Numbers(1,100);
        MutableLiveData<Resource<Numbers>> numberLD;

        //Act
        mMainDatabase.saveNumbers(numbersExpected);
        numberLD=mMainDatabase.getNumbers();
        Numbers numbers = numberLD.getValue().data;
        //Assert
        System.out.println("numbers from db: " + numbers.toString() + "   expected: "+ numbersExpected);
        Assert.assertEquals(numbers,numbersExpected);
    }
}
