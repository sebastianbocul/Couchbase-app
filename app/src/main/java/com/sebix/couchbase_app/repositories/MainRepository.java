package com.sebix.couchbase_app.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.utils.Resource;

import java.util.ArrayList;

public class MainRepository {
    private static final String TAG = "MainRepository";
    private MutableLiveData<Resource<Numbers>> mNumbers;
    private MutableLiveData<Resource<ArrayList<Integer>>> mPrimeNumbers = new MutableLiveData<>();
    private MainDatabase mMainDatabase;
    private boolean mCancel = false;

    public MainRepository(MainDatabase mainDatabase) {
        this.mMainDatabase = mainDatabase;
        this.mNumbers = getNumbersFromDb();
    }

    public MutableLiveData<Resource<Numbers>> getNumbersFromDb() {
        return mMainDatabase.getNumbers();
    }

    public MutableLiveData<Resource<Numbers>> getNumbers() {
        return mNumbers;
    }

    public void setNumbersLD(MutableLiveData<Resource<Numbers>> mNumbers) {
        this.mNumbers = mNumbers;
    }

    public void setNumbers(Numbers mNumbers) {
        this.mNumbers.postValue(Resource.success(mNumbers));
    }

    public MutableLiveData<Resource<ArrayList<Integer>>> getPrimeNumbers() {
        return mPrimeNumbers;
    }

    public void setPrimeNumbers(Resource<ArrayList<Integer>> primeNumbersList) {
        this.mPrimeNumbers.postValue(primeNumbersList);
        Log.d(TAG, "setmPrimeNumbers: " + primeNumbersList.data.size());
    }

    public void saveNumbers(Numbers numbers) {
        setNumbers(numbers);
        mMainDatabase.saveNumbers(numbers);
    }

    public void setCancel(boolean cancel) {
        mCancel = cancel;
    }

    public boolean getCancel() {
        return mCancel;
    }
}
