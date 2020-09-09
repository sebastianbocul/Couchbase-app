package com.sebix.couchbase_app.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.repositories.MainRepository;
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
class MainViewModelTest {
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
    void test_getNumbers_returnTrue() {
        //Assign
        Numbers numbers = new Numbers(1, 10);
        MutableLiveData<Resource<Numbers>> numbersLD = new MutableLiveData<>(Resource.success(numbers));
        when(mainRepository.getNumbers()).thenReturn(numbersLD);
        //Act
        MutableLiveData<Resource<Numbers>> returnedData = mainRepository.getNumbers();
        //Assert
        assertEquals(numbersLD, returnedData);
    }

    @Test
    void test_getPrimeNumbers_returnTrue() {
        //Assign
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(2, 3, 5, 7));
        MutableLiveData<Resource<ArrayList<Integer>>> primeNumbersLD = new MutableLiveData<>();
        primeNumbersLD.setValue(Resource.success(arrayList));
        when(mainRepository.getPrimeNumbers()).thenReturn(primeNumbersLD);
        //Act
        MutableLiveData<Resource<ArrayList<Integer>>> returnedData = mainRepository.getPrimeNumbers();
        //Assert
        assertEquals(primeNumbersLD, returnedData);
    }

    @Test
    void test_calculateAndUpdate_returnTrue() {
        //Assign
        Numbers numbers = new Numbers(1, 10);
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(2, 3, 5, 7));
        MutableLiveData<Resource<ArrayList<Integer>>> primeNumbersLD = new MutableLiveData<>();
        primeNumbersLD.setValue(Resource.success(arrayList));
        //calculations are saved directly to mainRepository
        MainDatabase mainDatabase = mock(MainDatabase.class);
        MutableLiveData<Resource<Numbers>> startData = new MutableLiveData<>();
        startData.setValue(Resource.success(numbers));
        when(mainDatabase.getNumbers()).thenReturn(startData);
        MainRepository containerMainRepository = new MainRepository(mainDatabase);
        mainViewModel = new MainViewModel(containerMainRepository);
        //Act
        mainViewModel.calculateAndUpdate(numbers);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MutableLiveData<Resource<ArrayList<Integer>>> returnedData = containerMainRepository.getPrimeNumbers();
        Numbers returnedNumbers = containerMainRepository.getNumbers().getValue().data;
        //Assert
        assertEquals(primeNumbersLD.getValue(), returnedData.getValue());
        assertEquals(numbers, returnedNumbers);
    }
}
