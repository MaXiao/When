package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import javax.inject.Inject

class PersonRepository @Inject constructor(private val personDao: PersonDao) {
    val allPerson: LiveData<List<PersonWithCity>> = personDao.getAllPerson()

    fun create(person: Person) {
        personDao.create(person)
    }

    fun getPersonsInGroup(group: GroupWithPersons): LiveData<List<PersonWithCity>> {
        return personDao.getPersonWithIds(group.persons.map { it.person.personId })
    }

    fun delete(p: PersonWithCity) {
        personDao.delete(p.person)
    }
}