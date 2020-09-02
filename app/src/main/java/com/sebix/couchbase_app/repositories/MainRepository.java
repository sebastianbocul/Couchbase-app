package com.sebix.couchbase_app.repositories;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.Resource;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainRepository {
    MutableLiveData<Resource<Numbers>> mNumbers = new MutableLiveData<>();
    MutableLiveData<Resource<ArrayList<Integer>>> mPrimeNumbers = new MutableLiveData<>();

    private static MainRepository instance = null;

    public static MainRepository getInstance() {
        if (instance == null) {
            instance = new MainRepository();
            //temp
            instance.mNumbers.postValue(Resource.success(new Numbers(0,10)));
            instance.mPrimeNumbers.postValue(Resource.success(new ArrayList<Integer>()));
        }
        return instance;
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

    public void setmPrimeNumbers(MutableLiveData<Resource<ArrayList<Integer>>> mPrimeNumbers) {
        this.mPrimeNumbers = mPrimeNumbers;
    }
}
