/*
 *  FILE          : AppDatabase.java
 *  PROJECT       : PROG3150 Mobile Application Development A1
 *  PROGRAMMER    : Josh Rogers 8230351
 *  FIRST VERSION : 2019-Feb-01
 *  DESCRIPTION   :
 *    Creates and runs the SQLite server used to save settings and reservations
 */

package com.example.brushing0895.dartscoretracker;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.arch.persistence.room.*;
import android.util.Log;

@Database(entities = {Scores.class}, version = 2, exportSchema = false)
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
            synchronized (ScoreDatabase.class) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), ScoreDatabase.class, "score-database").allowMainThreadQueries().addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return dbInstance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2)
    {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {
            database.execSQL("DROP TABLE Scores;");
            database.execSQL("CREATE TABLE Scores (" +
                    "gameID INTEGER NOT NULL," +
                    "scoreID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "dart1 INTEGER NOT NULL," +
                    "dart2 INTEGER NOT NULL," +
                    "dart3 INTEGER NOT NULL," +
                    "scoreAtStart INTEGER NOT NULL);");
        }
    };


    //Function : destroyInstance
    //Parameters : none
    //Returns : none
    //Description : Safely deletes the reference to the database
    public static void destroyInstance() {
        dbInstance = null;
    }
}
