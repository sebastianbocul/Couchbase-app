package com.sebix.couchbase_app.di;

import android.app.Application;

import com.sebix.couchbase_app.persistance.MainDatabase;
import com.sebix.couchbase_app.repositories.MainRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
class AppModules {

    @Singleton
    @Provides
    public static MainDatabase mainDatabase(Application application) {return new MainDatabase(application);}

    @Singleton
    @Provides
    public static MainRepository mainRepository(MainDatabase mainDatabase){
        return new MainRepository(mainDatabase);
    }
}
