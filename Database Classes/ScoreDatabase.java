/*
 *  FILE          : AppDatabase.java
 *  PROJECT       : PROG3150 Mobile Application Development A1
 *  PROGRAMMER    : Josh Rogers 8230351
 *  FIRST VERSION : 2019-Feb-01
 *  DESCRIPTION   :
 *    Creates and runs the SQLite server used to save settings and reservations
 */

package com.example.restaurantcalculator;

import android.content.Context;

import androidx.room.*;

@Database(entities = {Scores.class}, version = 1, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {

    private static ScoreDatabase dbInstance;
    public abstract  ScoreDao dataAccess();


    //Function : AppDatabase
    //Parameters : context - the context in which the database is being asked for
    //Returns : An instance of the database, ready to be used
    //Description : Opens and initializes the database, then returns a handle to use it
    //
    public static ScoreDatabase getScoreDatabase(Context context) {
        if (dbInstance == null) {
            synchronized (AppDatabase.class) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), ScoreDatabase.class, "score-database").allowMainThreadQueries().build();
                }
            }
        }
        return dbInstance;
    }


    //Function : destroyInstance
    //Parameters : none
    //Returns : none
    //Description : Safely deletes the reference to the database
    public static void destroyInstance() {
        dbInstance = null;
    }
}
