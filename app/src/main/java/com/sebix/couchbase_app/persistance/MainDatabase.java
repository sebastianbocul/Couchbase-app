package com.sebix.couchbase_app.persistance;

import android.app.Application;
import android.util.Log;

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

public class MainDatabase {
    Database database;
    Numbers numbers = new Numbers();
    private static final String TAG = "MainDatabase";

    public MainDatabase(Application application) {
        Log.d(TAG, "MainDatabase: ");
        init(application);
    }

    private void init(Application application) {
        // Initialize the Couchbase Lite system
        CouchbaseLite.init(application);
        // Get the database (if exists)
        DatabaseConfiguration config = new DatabaseConfiguration();
        try {
            database = new Database(Constants.DB_NAME, config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        if (Database.exists(Constants.DB_NAME, application.getFilesDir())) {
            if (database != null) {
                Document document = database.getDocument("mydoc");
                numbers.setNumber1(document.getInt("number1"));
                numbers.setNumber2(document.getInt("number2"));
                Log.d(TAG, "init: numbers1" + numbers.getNumber1());
                Log.d(TAG, "init: numbers2" + numbers.getNumber2());
            }
        }
    }

    public Numbers getNumbers() {
        Log.d(TAG, "getNumbers: numbers:" + numbers.toString());
        return numbers;
    }

    public void saveNumbers(Numbers numbers) {
        this.numbers = numbers;
        try {
            Log.d(TAG, "saveNumbers: " + numbers.toString());
            MutableDocument mutableDoc = new MutableDocument("mydoc")
                    .setInt("number1", numbers.getNumber1())
                    .setInt("number2", numbers.getNumber2());
            // Save it to the database.
            database.save(mutableDoc);
            Log.d(TAG, "saveNumbers: number saved");
            verifyDatabase();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public void verifyDatabase() {
        try {
            // Create a query to fetch documents of type SDK.
            Query query = QueryBuilder.select(SelectResult.all())
                    .from(DataSource.database(database))
                    .where(Expression.property("number1").equalTo(Expression.intValue(numbers.getNumber1())));
            ResultSet result = query.execute();
            Log.i(TAG, "Number:  " + numbers.getNumber1() + " Number of rows ::  " + result.allResults().size());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}
