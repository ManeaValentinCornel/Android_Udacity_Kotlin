package com.vcmanea.sleepapp.database
/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {
//Data Access Object
    @Insert
    fun insert(nightEntity:SleepNightEntity)

    @Update
    fun update(nightEntity: SleepNightEntity)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId= :key")
    fun get(key:Long):SleepNightEntity

    //returing back a List<SleepNight> which is expected, but it is actually LiveData
    //This is one the the features of room that we can get back LifeData
    //Room makes sure taht the LifeData is updated whenever the database is updated
    //The only thing we ahve to do is to get this list of all nights once attached and observer to it, and if the data changes the UI will update itself
    //withou us to habing to get the data again
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNight(): LiveData<List<SleepNightEntity>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight():SleepNightEntity?

    @Delete
    fun delete(nightEntity:SleepNightEntity)

    //delete everything
    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()





}

