package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity

@Dao
interface PersonDao {
    @Transaction
    @Query("SELECT * from people")
    fun getAllPerson(): LiveData<List<PersonWithCity>>

    @Query("SELECT * from people")
    fun getAll(): LiveData<List<Person>>

    @Transaction
    @Query("SELECT * FROM people WHERE personId IN(:personIds)")
    fun getPersonWithIds(personIds: List<Long>): LiveData<List<PersonWithCity>>

    @Insert
    fun create(people: Person)

    @Query("DELETE from people")
    suspend fun deleteAll()

    @Delete
    fun delete(p: Person)
}