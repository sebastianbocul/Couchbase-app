package com.sebix.couchbase_app.persistance;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;
import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.Constants;
import com.sebix.couchbase_app.utils.Resource;

public class MainDatabase {
    private static final String TAG = "MainDatabase";
    private Database mDatabase;
    private Numbers mNumbers = new Numbers();
    private Application mApplication;

    public MainDatabase(Application application) {
        this.mApplication = application;
        init();
    }

    public void init() {
        // Initialize the Couchbase Lite system
        CouchbaseLite.init(mApplication);
        // Get the database (if exists)
        DatabaseConfiguration config = new DatabaseConfiguration();
        try {
            mDatabase = new Database(Constants.DB_NAME, config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<Resource<Numbers>> getNumbers() {
        MutableLiveData<Resource<Numbers>> mNumbersLd = new MutableLiveData<>();
        if (Database.exists(Constants.DB_NAME, mApplication.getFilesDir())) {
            if (mDatabase != null) {
                Document document = mDatabase.getDocument("mydoc");
                if (document != null) {
                    mNumbers.setNumber1(document.getInt("number1"));
                    mNumbers.setNumber2(document.getInt("number2"));
                    mNumbersLd.setValue(Resource.success(mNumbers));
                    Log.d(TAG, "getNumbers: numbers:" + mNumbers.toString());
                    return mNumbersLd;
                }
            }
        }
        Log.d(TAG, "getNumbers: numbers:" + mNumbers.toString());
        mNumbersLd.setValue(Resource.error("Database empty!", new Numbers()));
        return mNumbersLd;
    }

    public void saveNumbers(Numbers numbers) {
        if(numbers.getNumber1()<0 || numbers.getNumber2()<0){
            return;
        }
        try {
            Log.d(TAG, "saveNumbers: " + numbers.toString());
            MutableDocument mutableDoc = new MutableDocument("mydoc")
                    .setInt("number1", numbers.getNumber1())
                    .setInt("number2", numbers.getNumber2());
            // Save it to the database.
            mDatabase.save(mutableDoc);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}
