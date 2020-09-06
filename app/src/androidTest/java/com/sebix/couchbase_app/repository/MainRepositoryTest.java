package com.sebix.couchbase_app.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.persistance.MainDatabase;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(AndroidJUnit4ClassRunner.class)
class MainRepositoryTest {
    //system under test
    private MainDatabase mainDatabase;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @BeforeEach
    public void init(){
        mainDatabase = new MainDatabase(ApplicationProvider.getApplicationContext());
    }

    @Test
    void init_notNull() throws Exception {
        assertNotNull(mainDatabase);
    }

    @Test
    void insertNumbers_getNumber_returnTrue() throws Exception {
        Numbers numbers = new Numbers(1,10);
      //  mainRepository.setNumbers();


    }
}
