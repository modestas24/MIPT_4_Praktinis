package com.example.mipt_4_praktinis.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NoteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun add(entity: NoteEntity)

    @Query("Select * from `note-table` where id=:id")
    abstract fun find(id:Long): Flow<NoteEntity>

    @Query("Select * from `note-table`")
    abstract fun findAll(): Flow<List<NoteEntity>>

    @Update
    abstract suspend fun update(entity: NoteEntity)

    @Delete
    abstract suspend fun delete(entity: NoteEntity)
}