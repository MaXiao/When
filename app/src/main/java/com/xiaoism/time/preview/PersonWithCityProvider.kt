package com.xiaoism.time.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity

class PersonWithCityProvider : PreviewParameterProvider<PersonWithCity> {
    override val values: Sequence<PersonWithCity> =
        sequenceOf(
            PersonWithCity(
                Person(personId = 0, name = "Xiao", cityId = "1816670"),
                CityData.BEIJING
            ),
            PersonWithCity(
                Person(personId = 0, name = "Qiuzao", cityId = "10103951"),
                CityData.TORONTO
            )
        )

}