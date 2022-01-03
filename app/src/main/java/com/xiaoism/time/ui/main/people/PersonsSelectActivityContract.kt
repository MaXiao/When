package com.xiaoism.time.ui.main.people

import android.app.Activity
import android.app.Person
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.xiaoism.time.ui.main.city.AddCityActivity

class PersonsSelectActivityContract: ActivityResultContract<Unit, List<Person>?>() {
    companion object {
        const val PERSON_LIST = "PERSON_LIST"
    }

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, PersonSelectionActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Person>? {
        if (resultCode != Activity.RESULT_OK) { return listOf() }

        return intent?.getParcelableArrayListExtra(PERSON_LIST)
    }
}