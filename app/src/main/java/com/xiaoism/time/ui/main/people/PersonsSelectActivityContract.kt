package com.xiaoism.time.ui.main.people

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.xiaoism.time.model.PersonWithCity

class PersonsSelectActivityContract(val multiChoice: Boolean = true): ActivityResultContract<Unit, List<PersonWithCity>?>() {
    companion object {
        const val PERSON_LIST = "PERSON_LIST"
    }

    override fun createIntent(context: Context, input: Unit?): Intent {
        val intent = Intent(context, PersonSelectionActivity::class.java)
        intent.putExtra(PersonSelectionFragment.MULTI_CHOICE, multiChoice)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<PersonWithCity>? {
        if (resultCode != Activity.RESULT_OK) { return listOf() }

        return intent?.getParcelableArrayListExtra(PERSON_LIST)
    }
}