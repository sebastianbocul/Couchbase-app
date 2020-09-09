package com.sebix.couchbase_app.repositories;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.utils.InstantExecutorExtension;
import com.sebix.couchbase_app.utils.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(InstantExecutorExtension.class)
class MainRepositoryTest {
    //system under test
    private MainRepository mainRepository;
    private MainDatabase mainDatabase;

    @BeforeEach
    public void init() {
        mainDatabase = mock(MainDatabase.class);
        mainRepository = new MainRepository(mainDatabase);
    }

    @Test
    void init_notNull() {
        assertNotNull(mainDatabase);
        assertNotNull(mainRepository);
    }

    @Test
    void test_getNumbersFromDb() {
        Numbers numbers = new Numbers(1, 10);
        MutableLiveData<Resource<Numbers>> startData = new MutableLiveData<>();
        startData.setValue(Resource.success(numbers));
        when(mainDatabase.getNumbers()).thenReturn(startData);
        //Act
        MutableLiveData<Resource<Numbers>> returnedValue = mainRepository.getNumbersFromDb();
        //Assert
        System.out.println("Returned value: " + returnedValue.getValue().data.toString());
        assertEquals(startData, returnedValue);
    }

    @Test
    void test_setNumbers_getNumbers_returnTrue() {
        Numbers numbers = new Numbers(1, 10);
        MutableLiveData<Resource<Numbers>> startData = new MutableLiveData<>();
        startData.setValue(Resource.success(numbers));
        when(mainDatabase.getNumbers()).thenReturn(startData);
        //after defining getNumbers() function we need to recreate repository
        //its because of the function getNumbers() is used in constructor
        //otherwise it will set mNumbers (livedata) to null and test will crash on .postValue() function;
        mainRepository = new MainRepository(mainDatabase);
        //Act
        mainRepository.setNumbers(numbers);
        MutableLiveData<Resource<Numbers>> returnedValue = mainRepository.getNumbers();
        //Assert
        assertEquals(startData.getValue(), returnedValue.getValue());
    }

    @Test
    void test_setPrimeNumbers_getPrimeNumbers_returnTrue() {
        Numbers numbers = new Numbers(1, 10);
        MutableLiveData<Resource<ArrayList<Integer>>> startData = new MutableLiveData<>();
        ArrayList<Integer> startArray = new ArrayList<>(Arrays.asList(2,3,5,7));
        startData.setValue(Resource.success(startArray));
        //Act
        mainRepository.setPrimeNumbers(Resource.success(startArray));
        MutableLiveData<Resource<ArrayList<Integer>>> returnedValue = mainRepository.getPrimeNumbers();
        //Assert
        assertEquals(startData.getValue(), returnedValue.getValue());
    }

    @Test
    void test_saveNumbers_getNumbers_returnTrue() {
        Numbers numbers = new Numbers(1, 10);
        MutableLiveData<Resource<Numbers>> startData = new MutableLiveData<>();
        startData.setValue(Resource.success(numbers));
        when(mainDatabase.getNumbers()).thenReturn(startData);
        //after defining getNumbers() function we need to recreate
        //its because of the function getNumbers() used in constructor
        mainRepository = new MainRepository(mainDatabase);
        //Act
        mainRepository.saveNumbers(numbers);
        MutableLiveData<Resource<Numbers>> returnedValue = mainRepository.getNumbers();
        //Assert
        assertEquals(startData.getValue(), returnedValue.getValue());
    }

    @Test
    void test_setCancel_getCancel_returnTrue() {
        boolean startCancel = true;
        //Act
        mainRepository.setCancel(startCancel);
        boolean newCancel=mainRepository.getCancel();
        //Assert
        assertEquals(startCancel,newCancel);
    }

}
