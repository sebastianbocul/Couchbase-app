package com.sebix.couchbase_app.repositories;

import com.sebix.couchbase_app.models.Numbers;
import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.utils.InstantExecutorExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith({InstantExecutorExtension.class})
class MainRepositoryTest {
    //system under test
    private MainRepository mainRepository;
    private MainDatabase mainDatabase;


    @BeforeEach
    public void init(){
        mainDatabase = mock(MainDatabase.class);
        mainRepository = new MainRepository(mainDatabase);
    }

    @Test
    void init_notNull() throws Exception {
        assertNotNull(mainDatabase);
        assertNotNull(mainRepository);
    }

    @Test
    void insertNumbers_getNumber_returnTrue() throws Exception {
        Numbers numbers = new Numbers(1,10);
      //  mainRepository.setNumbers();
    }
}
