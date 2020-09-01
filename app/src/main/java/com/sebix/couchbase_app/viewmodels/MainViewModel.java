package com.sebix.couchbase_app.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.repositories.MainRepository;
import com.sebix.couchbase_app.utils.Resource;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private MainRepository mMainRepository;

    public MainViewModel() {
        mMainRepository = MainRepository.getInstance();
    }

    public MutableLiveData<Resource<Numbers>> getmNumbers() {
        return mMainRepository.getmNumbers();
    }

    public MutableLiveData<Resource<ArrayList<Integer>>> getmOddNumbers() {
        return mMainRepository.getmOddNumbers();
    }

    public void setOddNumbers(MutableLiveData<Resource<ArrayList<Integer>>> oddsNumbers) {
        mMainRepository.setmOddNumbers(oddsNumbers);
    }

    private void calculateAndUpdate(Numbers numbers) {
        MutableLiveData<Resource<ArrayList<Integer>>> oddNumbers = new MutableLiveData<>();
        ArrayList<Integer> oddNumbersList = new ArrayList<>();
        oddNumbers.postValue(Resource.calculating(oddNumbersList));
        setOddNumbers(oddNumbers);

        //handelr 3s zeby jak za dlugo to błąd wywalilo
        oddNumbersList = calculateOddNumbers(numbers);

        oddNumbers.postValue(Resource.success(oddNumbersList));
    }

    private ArrayList<Integer> calculateOddNumbers(Numbers numbers) {
        ArrayList<Integer> oddNumbersList = new ArrayList<>();
        for (int i = numbers.getNumber1(); i <= numbers.getNumber2(); i++) {
            oddNumbersList.add(i);
        }
        return oddNumbersList;
    }
}