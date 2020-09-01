package com.sebix.couchbase_app.repositories;

import androidx.lifecycle.MutableLiveData;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.Resource;

import java.util.ArrayList;

public class MainRepository {
    MutableLiveData<Resource<Numbers>> mNumbers = new MutableLiveData<>();
    MutableLiveData<Resource<ArrayList<Integer>>> mOddNumbers = new MutableLiveData<>();

    private static MainRepository instance = null;

    public static MainRepository getInstance() {
        if (instance == null) {
            instance = new MainRepository();
        }
        return instance;
    }

    public MutableLiveData<Resource<Numbers>> getmNumbers() {
        return mNumbers;
    }

    public void setmNumbers(MutableLiveData<Resource<Numbers>> mNumbers) {
        this.mNumbers = mNumbers;
    }

    public MutableLiveData<Resource<ArrayList<Integer>>> getmOddNumbers() {
        return mOddNumbers;
    }

    public void setmOddNumbers(MutableLiveData<Resource<ArrayList<Integer>>> mOddNumbers) {
        this.mOddNumbers = mOddNumbers;
    }
}
