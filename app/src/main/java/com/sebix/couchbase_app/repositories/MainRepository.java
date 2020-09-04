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
//        Log.d(TAG, "MainRepository: " + mNumbers.getValue().data.toString());
//        mPrimeNumbers.postValue(Resource.success(new ArrayList<Integer>()));
    }

    public MutableLiveData<Resource<Numbers>> getmNumbers() {
        return mNumbers;
    }

    public void setmNumbers(Numbers mNumbers) {
        this.mNumbers.postValue(Resource.success(mNumbers));
    }

    public MutableLiveData<Resource<ArrayList<Integer>>> getmPrimeNumbers() {
        return mPrimeNumbers;
    }

    public void setmPrimeNumbers(Resource<ArrayList<Integer>> mPrimeNumbersList) {
        Log.d("MainFragment", "setmPrimeNumbers: " + mPrimeNumbersList.data.isEmpty());
        this.mPrimeNumbers.postValue(mPrimeNumbersList);
        Log.d("MainFragment", "setmPrimeNumbers: " + mPrimeNumbersList.data.size());
    }

    public void saveNumbers(Numbers numbers) {
        mMainDatabase.saveNumbers(numbers);
    }
}
