package by.tigertosh.shoppinglist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.tigertosh.shoppinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Insert
    suspend fun insertNote(note: NoteItem)
}