package com.vcmanea.sleepapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepNightEntity::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    abstract val sleepDatabaseDao: SleepDatabaseDao

    companion object {
        //VOLATILE
        //Annotated with volatile makes sure that the value of Instance is always up to data, and the same to all execution threats
        //A value of a volatile varaible will never be cached, and all write sand reads will be done to and from the main memory
        //Means by changes made from a thread INSTANCE will be visible to other threads immediately
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getDatabase(context: Context): SleepDatabase {
            //Multiple thrads can potentially ask for a database instance at the same time
            //leaveing us with to instances instead of one
            //-> only one thread of execution can enter this block of code at a time, and makes sure the database only get initialised once
            synchronized(this) {
                var instance = INSTANCE
                //SMART CAST is onyl available to local variables not to member variables
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    //MIGRATION -> not covered with our database(Destructive migration)
                    //Normally we would have to provide a migration object with a migration strategy when we create the database.
                    //Migration means when we change the database schema, by changing the numver or the type of columns
                    //we need a way to convert the existing tables and data into the new schema
                    //A migration object is an object which defines how you take all your rows with your old schema and covnert them to rows in the new schema.
                    //If a user updates from a version with a old database schema into a newer version with a new database schema, their sleepData is not lost
                    //In our case we just wipe and rebuild the database instead of migrating
                    INSTANCE=instance
                }
                return instance
            }

        }
    }
}