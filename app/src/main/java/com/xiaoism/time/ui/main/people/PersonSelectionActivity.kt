package com.xiaoism.time.ui.main.people

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.xiaoism.time.R
import com.xiaoism.time.model.Person
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonSelectionActivity : AppCompatActivity(R.layout.activity_person_selection) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<PeopleListFragment>(R.id.person_selection)
            }
        }
    }

    private fun selectMembers(list: ArrayList<Person>) {
        val intent = Intent()
        intent.putParcelableArrayListExtra(PersonsSelectActivityContract.PERSON_LIST, list)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}