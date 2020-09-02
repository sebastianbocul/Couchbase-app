package com.sebix.couchbase_app.repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.Resource;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MainRepository {
    MutableLiveData<Resource<Numbers>> mNumbers = new MutableLiveData<>();
    MutableLiveData<Resource<ArrayList<Integer>>> mPrimeNumbers = new MutableLiveData<>();

    @Inject
    public MainRepository() {
        Log.d("MYLOG", "MainRepository: ");
        mNumbers.postValue(Resource.success(new Numbers(0,10)));
        mPrimeNumbers.postValue(Resource.success(new ArrayList<Integer>()));
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
