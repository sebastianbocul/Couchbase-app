package com.sebix.couchbase_app.viewmodels;

import com.sebix.couchbase_app.repositories.MainRepository;
import com.sebix.couchbase_app.utils.InstantExecutorExtension;
import com.sebix.couchbase_app.utils.Log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(InstantExecutorExtension.class)
class MainViewModelTest extends Log {
    //system under test
    private MainViewModel mainViewModel;
    private MainRepository mainRepository;

    @BeforeEach
    public void init() {
        mainRepository = mock(MainRepository.class);
        mainViewModel = new MainViewModel(mainRepository);
    }

    @Test
    void init_notNull() {
        assertNotNull(mainViewModel);
        assertNotNull(mainRepository);
    }

    @Test
    void test_getNumbersFromDb() {

    }
//        Numbers numbers = new Numbers(1, 10);
//        MutableLiveData<Resource<Numbers>> startData = new MutableLiveData<>();
//        startData.setValue(Resource.success(numbers));
//        when(mainDatabase.getNumbers()).thenReturn(startData);
//        //Act
//        MutableLiveData<Resource<Numbers>> returnedValue = mainRepository.getNumbersFromDb();
//        //Assert
//        System.out.println("Returned value: " + returnedValue.getValue().data.toString());
//        assertEquals(startData, returnedValue);
//    }

}
