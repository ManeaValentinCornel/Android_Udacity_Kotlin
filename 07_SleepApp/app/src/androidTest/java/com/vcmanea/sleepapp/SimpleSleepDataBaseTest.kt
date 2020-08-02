package com.vcmanea.sleepapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vcmanea.sleepapp.database.SleepDatabase
import com.vcmanea.sleepapp.database.SleepDatabaseDao
import com.vcmanea.sleepapp.database.SleepNightEntity
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SimpleSleepDataBaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //Using an in-memory databasInstrumentationRegistrye because the information stored here disappears when the process is killed
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        sleepDao = db.sleepDatabaseDao
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight(){
        val night=SleepNightEntity()
        sleepDao.insert(night)
        val tonight=sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality,-1)
    }
}

