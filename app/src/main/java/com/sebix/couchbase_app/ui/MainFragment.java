package com.sebix.couchbase_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sebix.couchbase_app.R;
import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.Resource;
import com.sebix.couchbase_app.viewmodels.MainViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    public MainViewModel mMainViewModel;
    private TextView mPrimeNumbersTextView, mNumber1, mNumber2;
    private Button mCalculateButton;
    private FloatingActionButton mSaveButton;
    private ProgressBar mProgressBar;
    private ImageView mLogo;

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
        mPrimeNumbersTextView = view.findViewById(R.id.prime_numbers);
        mCalculateButton = view.findViewById(R.id.calculate_button);
        mSaveButton = view.findViewById(R.id.save_button);
        Log.d(TAG, "onViewCreated");
        mProgressBar = getActivity().findViewById(R.id.progress_bar);
        mNumber1 = view.findViewById(R.id.number1);
        mNumber2 = view.findViewById(R.id.number2);
        mLogo = view.findViewById(R.id.logo_small);
        setListeners();
        setObservers();
    }

    private void setListeners() {
        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCalculateButton.getText().toString().equals(getString(R.string.cancel_button))) {
                    Log.d(TAG, "onClick: cancel ");
                    mMainViewModel.setCancel(true);
                    return;
                }
                Log.d(TAG, "onClick: calculate ");
                if (mNumber1.getText() == null || mNumber1.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mNumber2.getText() == null || mNumber2.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int number1 = Integer.parseInt(mNumber1.getText().toString().trim());
                int number2 = Integer.parseInt(mNumber2.getText().toString().trim());
                Numbers numbers = new Numbers(number1, number2);
                mMainViewModel.calculateAndUpdate(numbers);
            }
        });
        mSaveButton.setOnClickListener(v -> {
            if (mNumber1.getText() == null || mNumber1.getText().length() == 0) {
                Toast.makeText(getActivity(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mNumber2.getText() == null || mNumber2.getText().length() == 0) {
                Toast.makeText(getActivity(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            int number1 = Integer.parseInt(mNumber1.getText().toString().trim());
            int number2 = Integer.parseInt(mNumber2.getText().toString().trim());
            Log.d(TAG, "onClick calculate :  n1:" + number1 + "   n2:" + number2 + "    -  " + (number1 - number2));
            Numbers numbers = new Numbers(number1, number2);
            mMainViewModel.saveNumbers(numbers);
            Toast.makeText(getContext(), "Data saved!", Toast.LENGTH_SHORT).show();
        });
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "This is demonstration version :)", 1000).show();
            }
        });
    }

    private void setObservers() {
        Log.d(TAG, "setObservers: ");
        mMainViewModel.getNumbers().observe(getViewLifecycleOwner(), new Observer<Resource<Numbers>>() {
            @Override
            public void onChanged(Resource<Numbers> numbersResource) {
                Log.d(TAG, "onChanged: " + numbersResource.data.toString());
                if (numbersResource != null) {
                    switch (numbersResource.status) {
                        case SUCCESS: {
                            mNumber1.setText(String.valueOf(numbersResource.data.getNumber1()));
                            mNumber2.setText(String.valueOf(numbersResource.data.getNumber2()));
                            Log.d(TAG, "onChanged: number1: " + numbersResource.data.getNumber1() + " number2: " + numbersResource.data.getNumber2());
                            break;
                        }
                        case ERROR: {
                            Toast.makeText(getContext(), numbersResource.message, Toast.LENGTH_SHORT).show();
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
        mMainViewModel.getPrimeNumbers().observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<Integer>>>() {
            @Override
            public void onChanged(Resource<ArrayList<Integer>> arrayListResource) {
                if (arrayListResource != null) {
                    if (arrayListResource.data == null) {
                        mPrimeNumbersTextView.setText("null");
                        return;
                    }
                    Log.d(TAG, "onChanged primeNumbers:" + arrayListResource.data.size());
                    switch (arrayListResource.status) {
                        case SUCCESS: {
                            mCalculateButton.setText(R.string.calculate_button);
                            Log.d(TAG, "onChanged getPrimeNumbers: arraySize: " + arrayListResource.data.size());
                            mProgressBar.setVisibility(View.GONE);
                            mPrimeNumbersTextView.setText(String.valueOf(arrayListResource.data.size()));
                            break;
                        }
                        case ERROR: {
                            mCalculateButton.setText(R.string.calculate_button);
                            Log.d(TAG, "onChanged getPrimeNumbers: error arraysize: " + arrayListResource.data.size());
                            break;
                        }
                        case CALCULATING: {
                            mCalculateButton.setText(R.string.cancel_button);
                            mProgressBar.setVisibility(View.VISIBLE);
                            break;
                        }
                        default: {
                            mCalculateButton.setText(R.string.calculate_button);
                            Log.d(TAG, "onChanged getPrimeNumbers: default");
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if (mNumber1.getText() == null) {
            return;
        }
        if (mNumber2.getText() == null) {
            return;
        }
        int number1;
        int number2;
        if (mNumber1.getText().length() == 0) {
            number1 = 0;
        } else {
            number1 = Integer.parseInt(mNumber1.getText().toString().trim());
        }
        if (mNumber2.getText().length() == 0) {
            number2 = 0;
        } else {
            number2 = Integer.parseInt(mNumber2.getText().toString().trim());
        }
        Numbers numbers = new Numbers(number1, number2);
        mMainViewModel.setNumbers(numbers);
        super.onDetach();
    }
}