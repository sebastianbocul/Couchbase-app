package com.sebix.couchbase_app.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.utils.Resource;

import java.util.ArrayList;

public class MainRepository {
    private static final String TAG = "MainRepository";
    MutableLiveData<Resource<Numbers>> mNumbers = new MutableLiveData<>();
    MutableLiveData<Resource<ArrayList<Integer>>> mPrimeNumbers = new MutableLiveData<>();
    MainDatabase mMainDatabase;

    public MainRepository(MainDatabase mainDatabase) {
        this.mMainDatabase = mainDatabase;
        mNumbers = mMainDatabase.getNumbers();
    }

    public MutableLiveData<Resource<Numbers>> getNumbers() {
        return mNumbers;
    }

    public void setNumbers(Numbers mNumbers) {
        this.mNumbers.postValue(Resource.success(mNumbers));
    }

    public MutableLiveData<Resource<ArrayList<Integer>>> getPrimeNumbers() {
        return mPrimeNumbers;
    }

    public void setPrimeNumbers(Resource<ArrayList<Integer>> primeNumbersList) {
        this.mPrimeNumbers.postValue(primeNumbersList);
        Log.d("MainFragment", "setmPrimeNumbers: " + primeNumbersList.data.size());
    }

    public void saveNumbers(Numbers numbers) {
        mMainDatabase.saveNumbers(numbers);
    }
}
