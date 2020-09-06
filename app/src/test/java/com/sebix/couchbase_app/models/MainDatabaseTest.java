package com.sebix.couchbase_app.models;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.repositories.MainRepository;
import com.sebix.couchbase_app.utils.InstantExecutorExtension;
import com.sebix.couchbase_app.utils.Resource;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith({InstantExecutorExtension.class})
public class MainDatabaseTest {
    private MainDatabase mainDatabase;


//    @BeforeEach
//    public void init(){
//        mainDatabase = mock(MainDatabase.class);
//        mainRepository = new MainRepository(mainDatabase);
//    }



    @BeforeEach
    public void ini2t() {
        MockitoAnnotations.initMocks(this);
        Application application = new Application();
        mainDatabase=new MainDatabase(application);
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
