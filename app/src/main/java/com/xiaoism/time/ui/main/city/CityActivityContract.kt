package com.xiaoism.time.ui.main.city

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.xiaoism.time.model.City


class CityActivityContract : ActivityResultContract<Unit, City?>() {
    companion object {
        const val CITY = "CITY_CONTRACT"
    }

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, AddCityActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): City? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }

        return intent?.getParcelableExtra<City>(CITY);
    }
}