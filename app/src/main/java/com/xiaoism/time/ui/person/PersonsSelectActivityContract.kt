package com.xiaoism.time.ui.person

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.ui.person.PersonSelectionActivity.Companion.EXISTING_MEMBER
import com.xiaoism.time.ui.person.PersonSelectionActivity.Companion.MULTI_CHOICE

class PersonsSelectActivityContract :
    ActivityResultContract<PersonsSelectActivityContract.PersonSelectInput, List<PersonWithCity>?>() {
    companion object {
        const val PERSON_LIST = "PERSON_LIST"
    }

    class PersonSelectInput(
        val multiSelect: Boolean = false,
        val members: List<PersonWithCity>? = null
    )

    override fun createIntent(context: Context, input: PersonSelectInput): Intent {
        val intent = Intent(context, PersonSelectionActivity::class.java)
        intent.putExtra(MULTI_CHOICE, input.multiSelect)
        input.members?.let {
            val members = ArrayList(it)
            intent.putParcelableArrayListExtra(EXISTING_MEMBER, members)
        }
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<PersonWithCity>? {
        if (resultCode != Activity.RESULT_OK) {
            return listOf()
        }

        return intent?.getParcelableArrayListExtra(PERSON_LIST)
    }
}