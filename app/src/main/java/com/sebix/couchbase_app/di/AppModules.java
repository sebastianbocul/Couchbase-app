package com.sebix.couchbase_app.di;

import com.sebix.couchbase_app.repositories.MainRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
class AppModules {

    //example
    @Singleton
    @Provides
    public static MainRepository mainRepository(){
        return new MainRepository();
    }

    //we will need to create database here
}
