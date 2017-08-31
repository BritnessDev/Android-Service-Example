// IRemoteService.aidl
package com.example.louis383.servicetest;

// Declare any non-default types here with import statements

interface IRemoteService {

    int getPid();
    void fireNotification(String message);

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
