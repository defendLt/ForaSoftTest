package com.platdmit.forasofttest.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<DbModel> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbElement: DbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(dbElements: List<DbModel>)

    @Update
    suspend fun update(dbElement: DbModel)

    @Update
    suspend fun updateAll(dbElements: List<DbModel>)

    @Delete
    suspend fun delete(dbElement: DbModel)
}