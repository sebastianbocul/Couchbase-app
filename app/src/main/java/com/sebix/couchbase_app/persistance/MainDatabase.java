package com.sebix.couchbase_app.persistance;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.Expression;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;
import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.utils.Constants;
import com.sebix.couchbase_app.utils.Resource;

public class MainDatabase {
    private Database database;
    private Numbers mNumbers = new Numbers();
    private static final String TAG = "MainDatabase";
    private Application mApplication;

    public MainDatabase(Application application) {
        this.mApplication=application;
        Log.d(TAG, "MainDatabase: ");
        init();
    }

    private void init() {
        // Initialize the Couchbase Lite system
        CouchbaseLite.init(mApplication);
        // Get the database (if exists)
        DatabaseConfiguration config = new DatabaseConfiguration();
        try {
            database = new Database(Constants.DB_NAME, config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<Resource<Numbers>> getNumbers() {
        MutableLiveData<Resource<Numbers>> mNumbersLd = new MutableLiveData<>();
        if (Database.exists(Constants.DB_NAME, mApplication.getFilesDir())) {
            if (database != null) {
                Document document = database.getDocument("mydoc");
                if(document!=null){
//                    if(!document.contains("number1") || !document.contains("number2")){
                        mNumbers.setNumber1(document.getInt("number1"));
                        mNumbers.setNumber2(document.getInt("number2"));
                        mNumbersLd.setValue(Resource.success(mNumbers));
//                    }
                }
            }
        }
        Log.d(TAG, "getNumbers: numbers:" + mNumbers.toString());
        return mNumbersLd;
    }

    public void saveNumbers(Numbers numbers) {
        try {
            Log.d(TAG, "saveNumbers: " + numbers.toString());
            MutableDocument mutableDoc = new MutableDocument("mydoc")
                    .setInt("number1", numbers.getNumber1())
                    .setInt("number2", numbers.getNumber2());
            // Save it to the database.
            database.save(mutableDoc);
            Log.d(TAG, "saveNumbers: number saved");
            verifyDatabase(numbers);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public void verifyDatabase(Numbers numbers) {
        try {
            // Create a query to fetch documents of type SDK.
            Query query1 = QueryBuilder.select(SelectResult.all())
                    .from(DataSource.database(database))
                    .where(Expression.property("number1").equalTo(Expression.intValue(numbers.getNumber1())));
            ResultSet result1 = query1.execute();
            Log.i(TAG, "Number1:  " + numbers.getNumber1() + " Number of rows ::  " + result1.allResults().size());
            Query query2 = QueryBuilder.select(SelectResult.all())
                    .from(DataSource.database(database))
                    .where(Expression.property("number2").equalTo(Expression.intValue(numbers.getNumber2())));
            ResultSet result2 = query2.execute();
            Log.i(TAG, "Number2:  " + numbers.getNumber2() + " Number of rows ::  " + result2.allResults().size());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}
