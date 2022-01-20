package com.xiaoism.time.ui.main.people

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity

class PersonsSelectActivityContract : ActivityResultContract<Intent, List<PersonWithCity>?>() {
    companion object {
        const val PERSON_LIST = "PERSON_LIST"
    }

    override fun createIntent(context: Context, input: Intent): Intent {
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<PersonWithCity>? {
        if (resultCode != Activity.RESULT_OK) {
            return listOf()
        }

        return intent?.getParcelableArrayListExtra(PERSON_LIST)
    }
}