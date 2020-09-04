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

import java.util.HashMap;

public class MainDatabase {
    Database database;
    private static final String TAG = "MainDatabase";

    public MainDatabase(Application application) {
        Log.d(TAG, "MainDatabase: ");
        init(application);
        createData(application);
    }

    private void createData(Application application) {
        try {
            // Create a new document (i.e. a record) in the database.
            MutableDocument mutableDoc = new MutableDocument()
                    .setInt("number1", 1)
                    .setInt("number2", 10);
            // Save it to the database.
//            if(Database.exists("getting-started",application.getFilesDir())) return;

            database.save(mutableDoc);
            // Update a document.
            Document document = database.getDocument(mutableDoc.getId());
            // Log the document ID (generated by the database) and properties
            Log.i(TAG, "Document ID :: " + document.getId());
            Log.i(TAG, "number1 " + document.getInt("number1"));
            Log.i(TAG, "number2 " + document.getInt("number2"));
            // Create a query to fetch documents of type SDK.
            Query query = QueryBuilder.select(SelectResult.all())
                    .from(DataSource.database(database))
                    .where(Expression.property("number1").equalTo(Expression.intValue(1)));
            ResultSet result = null;
            result = query.execute();
            Log.d(TAG, "Number of rows ::  " + result.allResults().size());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    private void init(Application application) {
        // Initialize the Couchbase Lite system
        CouchbaseLite.init(application);
        // Get the database (and create it if it doesn’t exist).
        DatabaseConfiguration config = new DatabaseConfiguration();
        try {
            database = new Database("getting-started", config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    private void createSampleData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("n1", 1);
        MutableDocument mutableDoc = new MutableDocument("my_doc", map);
        // Save it to the database.
        try {
            database.save(mutableDoc);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public Numbers getNumbers() {
//
////        String docId = DatabaseManager.getSharedInstance().getCurrentUserDocId();
//        Document document = database.getDocument("my_doc");
//        if (document != null) {
//            if (document.getString("number1") != null) {
//                Log.d(TAG, document.getString("n1"));
//            } else Log.d(TAG, "document null: ");
//        }
//        Query query = QueryBuilder
//                .select(SelectResult.all())
//                .from(DataSource.database(database));
//        try {
//            ResultSet rs = query.execute();
//            Log.d(TAG, "Result : " + rs.next().getString("number1"));
////            Log.d(TAG, "Result : " + rs.next().getString("number2"));
//        } catch (CouchbaseLiteException e) {
//            e.printStackTrace();
//        }
        Numbers numbers = new Numbers(1, 2);
        return numbers;
    }
}
