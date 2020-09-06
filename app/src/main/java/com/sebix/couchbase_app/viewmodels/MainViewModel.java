package com.sebix.couchbase_app.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.repositories.MainRepository;
import com.sebix.couchbase_app.utils.CalculatePrimeNumbers;
import com.sebix.couchbase_app.utils.Constants;
import com.sebix.couchbase_app.utils.Resource;

import java.util.ArrayList;
import java.util.Observable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private MainRepository mMainRepository;
    private boolean mCancel = false;

    @ViewModelInject
    public MainViewModel(MainRepository mMainRepository) {
        Log.d(TAG, "MainViewModel: ");
        this.mMainRepository = mMainRepository;
    }

    public MutableLiveData<Resource<Numbers>> getNumbers() {
        return mMainRepository.getNumbers();
    }

    public void setNumbers(Numbers numbers) {
        mMainRepository.setNumbers(numbers);
    }

    public MutableLiveData<Resource<ArrayList<Integer>>> getPrimeNumbers() {
        return mMainRepository.getPrimeNumbers();
    }

    public void setPrimeNumbers(Resource<ArrayList<Integer>> primesNumbers) {
        mMainRepository.setPrimeNumbers(primesNumbers);
    }

    public void setCancel(boolean cancel) {
        mCancel = cancel;
    }

    public void calculateAndUpdate(Numbers numbers) {
        setNumbers(numbers);
        Log.d(TAG, "SEND LOADING MESS");
        ArrayList<Integer> primeNumbersList = new ArrayList<Integer>();
        setPrimeNumbers(Resource.calculating(primeNumbersList));
        ///////////////new
        mCancel = false;
        Log.d(TAG, "calculateAndUpdate: " + mCancel);
        Single<Object> obs = Single.create(emitter -> {
            Numbers listNumbers = new Numbers(numbers.getNumber1(), numbers.getNumber2());
            if (listNumbers.getNumber1() > listNumbers.getNumber2()) {
                int buffor;
                buffor = listNumbers.getNumber1();
                listNumbers.setNumber1(listNumbers.getNumber2());
                listNumbers.setNumber2(buffor);
            }
            ArrayList<Integer> listPrime = new ArrayList<>();
            for (int i = listNumbers.getNumber1(); i < listNumbers.getNumber2(); i++) {
                if (!mCancel) {
                    if (CalculatePrimeNumbers.checkIsPrime(i)) {
                        listPrime.add(i);
                    }
                } else {
                    emitter.isDisposed();
                    emitter.onSuccess(new ArrayList<>());
                }
            }
            emitter.onSuccess(listPrime);
        })
                .delaySubscription(Constants.timeDelay, Constants.timeUnit)
                .subscribeOn(Schedulers.io());
        SingleObserver<Object> singleObserver = new SingleObserver<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull Object o) {
                Log.d("MainFragment", "onSuccess: RxJava" + o.toString());
                setPrimeNumbers(Resource.success((ArrayList<Integer>) o));
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        };
        obs.subscribe(singleObserver);
    }

    public void saveNumbers(Numbers numbers) {
        mMainRepository.saveNumbers(numbers);
    }
}