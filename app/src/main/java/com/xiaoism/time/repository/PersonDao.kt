package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.xiaoism.time.model.City
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity

@Dao
interface PersonDao {
    @Transaction
    @Query("SELECT * from people")
    fun getAllPerson(): LiveData<List<PersonWithCity>>

    @Transaction
    @Query("SELECT * FROM people WHERE personId IN(:personIds)")
    fun getPersonWithIds(personIds: List<Long>): LiveData<List<PersonWithCity>>

    @Transaction
    @Query("SELECT * FROM `people` WHERE personId = :personId")
    fun getPerson(personId: Long): LiveData<PersonWithCity>

    @Insert
    fun create(people: Person)

    @Query("DELETE from people")
    fun deleteAll()

    @Delete
    fun delete(p: Person)

    @Query("UPDATE people SET cityId = :cityId WHERE personId = :personId")
    fun updateCity(personId: Long, cityId: String)

    @Query("UPDATE people SET cityId = :name WHERE personId = :personId")
    fun updateName(personId: Long, name: String)
}