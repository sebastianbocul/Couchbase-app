package com.sebix.couchbase_app.repositories;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.utils.InstantExecutorExtension;
import com.sebix.couchbase_app.utils.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({InstantExecutorExtension.class})
class MainRepositoryTest {
    //system under test
    private MainRepository mainRepository;
    private MainDatabase mainDatabase;


    @BeforeEach
    public void init(){
        mainDatabase = mock(MainDatabase.class);
        mainRepository = new MainRepository(mainDatabase);
    }

    @Test
    void init_notNull() throws Exception {
        assertNotNull(mainDatabase);
        assertNotNull(mainRepository);
    }

    @Test
    void test_getNumbersFromDb() throws Exception {
        Numbers numbers = new Numbers(1,10);
        final MutableLiveData<Resource<Numbers>> startData = new MutableLiveData<>();
        startData.setValue(Resource.success(numbers));
        when(mainDatabase.getNumbers()).thenReturn(startData);
        //Act
        final MutableLiveData<Resource<Numbers>> returnedValue = mainRepository.getNumbersFromDb();
        //Assert
        System.out.println("Returned value: " + returnedValue.getValue().data.toString());
        assertEquals(startData, returnedValue);
    }

    @Test
    void test_setNumbers_getNumbers_returnTrue () throws Exception {
        Numbers numbers = new Numbers(1,10);
        final MutableLiveData<Resource<Numbers>> startData = new MutableLiveData<>();
        startData.setValue(Resource.success(numbers));
        when(mainDatabase.getNumbers()).thenReturn(startData);
        //Act
        mainRepository.setNumbersLD(mainRepository.getNumbersFromDb());
        final MutableLiveData<Resource<Numbers>> returnedValue = mainRepository.getNumbers();
        //Assert
        System.out.println("Returned value: " + returnedValue.getValue().data.toString());
        assertEquals(startData, returnedValue);
    }
}
