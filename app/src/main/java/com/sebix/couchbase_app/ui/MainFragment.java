package com.sebix.couchbase_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sebix.couchbase_app.R;
import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.Resource;
import com.sebix.couchbase_app.viewmodels.MainViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    public MainViewModel mMainViewModel;
    private static final String TAG = "MainFragment";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setObservers();
    }


    private void setObservers() {
        Log.d("MYLOG", "setObservers: ");
        mMainViewModel.getmNumbers().observe(getViewLifecycleOwner(), new Observer<Resource<Numbers>>() {
            @Override
            public void onChanged(Resource<Numbers> numbersResource) {
                Log.d("MYLOG", "onChanged: " + numbersResource.data.getNumber2());
                if (numbersResource != null) {
                    switch (numbersResource.status) {
                        case SUCCESS: {
                            Log.d(TAG, "onChanged: number1: " + numbersResource.data.getNumber1() + " number2: " + numbersResource.data.getNumber2());
                            break;
                        }
                        case ERROR: {
                            Log.d(TAG, "onChanged: error arraysize: " + numbersResource.data.getNumber1() + " number2: " + numbersResource.data.getNumber2());
                            break;
                        }
                        default: {
                            Log.d(TAG, "onChanged: default");
                            break;
                        }
                    }
                }
            }
        });
        mMainViewModel.getmPrimeNumbers().observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<Integer>>>() {
            @Override
            public void onChanged(Resource<ArrayList<Integer>> arrayListResource) {
                if (arrayListResource != null) {
                    switch (arrayListResource.status) {
                        case SUCCESS: {
                            Log.d(TAG, "onChanged getPrimeNumbers: arraySize: " + arrayListResource.data.size());
                            break;
                        }
                        case ERROR: {
                            Log.d(TAG, "onChanged getPrimeNumbers: error arraysize: " + arrayListResource.data.size());
                            break;
                        }
                        default: {
                            Log.d(TAG, "onChanged getPrimeNumbers: default");
                            break;
                        }
                    }
                }
            }
        });
    }
}