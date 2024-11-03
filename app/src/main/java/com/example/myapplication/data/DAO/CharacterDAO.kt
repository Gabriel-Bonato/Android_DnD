package com.example.myapplication.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: com.example.myapplication.races.Character)

    @Update
    suspend fun update(character: com.example.myapplication.races.Character)

    @Delete
    suspend fun delete(character: com.example.myapplication.races.Character)

    @Query("SELECT * FROM character_table")
    fun getAllCharacters(): LiveData<List<Character>>

}
