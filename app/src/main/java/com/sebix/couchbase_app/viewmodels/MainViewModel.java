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

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    public void setPrimeNumbers(Resource<ArrayList<Integer>> primesNumbers) {
        mMainRepository.setmPrimeNumbers(primesNumbers);
    }

    public void setNumbers(Numbers numbers) {
        mMainRepository.setmNumbers(numbers);
    }



    public void calculateAndUpdate(Numbers numbers) {
        setNumbers(numbers);

        Log.d("MainFragment", "SEND LOADING MESS");
        ArrayList<Integer> primeNumbersList = new ArrayList<Integer>();
        setPrimeNumbers(Resource.calculating(primeNumbersList));

        //TODO:mozna zrobic debug/ production release zeby dodac handlery
        Single<Object> obs = Single.create(emitter -> {
            ArrayList<Integer> list = new ArrayList<>();
            list = CalculatePrimeNumbers.calculate(numbers);
            emitter.onSuccess(list);
            emitter.setCancellable(new Cancellable() {
                @Override
                public void cancel() throws Exception {
                    //clean memory
                }
            });
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

    public void saveNumbers(Numbers numbers){
        mMainRepository.saveNumbers(numbers);
    }
}