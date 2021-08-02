package com.xiaoism.time.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity

class PersonRepository(private val personDao: PersonDao) {
    val allPerson: LiveData<List<PersonWithCity>> = personDao.getAllPerson()

    suspend fun create(person: Person) {
        Log.w("person repo", "create person" + person.toString())
        personDao.create(person)
    }

    fun getPersonsInGroup(group: GroupWithPersons): LiveData<List<PersonWithCity>> {
        return personDao.getPersonWithIds(group.persons.map { it.person.personId })
    }
}