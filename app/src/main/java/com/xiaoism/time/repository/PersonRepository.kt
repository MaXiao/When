package com.xiaoism.time.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import javax.inject.Inject

class PersonRepository @Inject constructor(private val personDao: PersonDao) {
    val allPerson: LiveData<List<PersonWithCity>> = personDao.getAllPerson()
    private val all: LiveData<List<Person>> = personDao.getAll()

    fun create(person: Person) {
        Log.w("person repo", "create person" + person.toString())
//        personDao.create(person)
        InsertPersonAsyncTask(personDao).execute(person)
    }

    fun getPersonsInGroup(group: GroupWithPersons): LiveData<List<PersonWithCity>> {
        return personDao.getPersonWithIds(group.persons.map { it.person.personId })
    }

    fun delete(p: PersonWithCity) {
        Log.d("person repo", p.person.toString())
        personDao.delete(p.person)
    }

    fun getAllMember(): LiveData<List<Person>> {
        return personDao.getAll();
    }

    fun getAllItems(): LiveData<List<Person>> {
        return all;
    }

    private class InsertPersonAsyncTask(val personDao: PersonDao) :
        AsyncTask<Person, Unit, Unit>() {
        override fun doInBackground(vararg p: Person?) {
            personDao.create(p[0]!!)
        }
    }
}