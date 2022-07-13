package com.jonapoul.fueltracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RefuelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: RefuelEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<RefuelEntity>): List<Long>

    @Query("DELETE FROM RefuelEntity WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM RefuelEntity")
    fun getAll(): Flow<List<RefuelEntity>>

    @Query("SELECT * FROM RefuelEntity ORDER BY time DESC LIMIT 1;")
    suspend fun getLatest(): RefuelEntity?

    @Query("SELECT * FROM RefuelEntity WHERE id=:id")
    suspend fun getWithId(id: Long): RefuelEntity?
}
