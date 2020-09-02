package com.sebix.couchbase_app.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.repositories.MainRepository;
import com.sebix.couchbase_app.utils.Resource;

import java.util.ArrayList;


public class MainViewModel extends ViewModel {
    private MainRepository mMainRepository;

    @ViewModelInject
    public MainViewModel(MainRepository mMainRepository) {
        Log.d("MYLOG", "MainViewModel: ");
        this.mMainRepository = mMainRepository;
    }

    public MutableLiveData<Resource<Numbers>> getmNumbers() {
        return mMainRepository.getmNumbers();
    }

    public MutableLiveData<Resource<ArrayList<Integer>>> getmPrimeNumbers() {
        return mMainRepository.getmPrimeNumbers();
    }

    public void setPrimeNumbers(MutableLiveData<Resource<ArrayList<Integer>>> primesNumbers) {
        mMainRepository.setmPrimeNumbers(primesNumbers);
    }

    public void setNumbers(Numbers numbers) {
        mMainRepository.setmNumbers(numbers);
    }

    private void calculateAndUpdate(Numbers numbers) {
        setNumbers(numbers);
        MutableLiveData<Resource<ArrayList<Integer>>> primeNumbers = new MutableLiveData<>();
        ArrayList<Integer> primeNumbersList = new ArrayList<>();
        primeNumbers.postValue(Resource.calculating(primeNumbersList));
        setPrimeNumbers(primeNumbers);
        //handelr 3s zeby jak za dlugo to błąd wywalilo
        primeNumbersList = calculatePrimeNumbers(numbers);
        primeNumbers.postValue(Resource.success(primeNumbersList));
    }

    private ArrayList<Integer> calculatePrimeNumbers(Numbers numbers) {
        ArrayList<Integer> primeNumbersList = new ArrayList<>();
        for (int i = numbers.getNumber1(); i <= numbers.getNumber2(); i++) {
            primeNumbersList.add(i);
        }
        return primeNumbersList;
    }
}