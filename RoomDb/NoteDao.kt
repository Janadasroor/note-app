package com.example.myapplication.RoomDb
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query ("SELECT * FROM note WHERE isSynced =0")
    fun getUnsyncedNotes():List<Note>

    @Query("SELECT  * FROM note WHERE type= :type ORDER BY timestamp DESC")
    fun getNotesByType(type:NoteType):Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id =:id")
    fun getNotesByType(id:Int):Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Update
    suspend fun updateCodeNote(note: Note)
}
