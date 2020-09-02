package com.sebix.couchbase_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView mPrimeNumbersTextView;
    private Button mCalculateButton;
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
        mPrimeNumbersTextView=view.findViewById(R.id.prime_numbers);
        mCalculateButton=view.findViewById(R.id.calculate_button);
        setListeners();
        setObservers();
    }

    private void setListeners() {
        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Numbers numbers = new Numbers(1,10);
                mMainViewModel.calculateAndUpdate(numbers);
            }
        });

    }

    private void setObservers() {
        Log.d("MYLOG", "setObservers: ");
        mMainViewModel.getmNumbers().observe(getViewLifecycleOwner(), new Observer<Resource<Numbers>>() {
            @Override
            public void onChanged(Resource<Numbers> numbersResource) {
                Log.d(TAG, "onChanged: " + numbersResource.data.toString());
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
                    Log.d(TAG, "onChanged primeNumbers:" + arrayListResource.data.size());
                    switch (arrayListResource.status) {
                        case SUCCESS: {
                            Log.d(TAG, "onChanged getPrimeNumbers: arraySize: " + arrayListResource.data.size());
                            if(arrayListResource.data==null){
                                mPrimeNumbersTextView.setText("null");
                                return;
                            }
                            mPrimeNumbersTextView.setText(String.valueOf(arrayListResource.data.size()));
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