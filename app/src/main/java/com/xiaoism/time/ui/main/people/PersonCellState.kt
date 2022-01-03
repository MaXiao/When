package com.xiaoism.time.ui.main.people

import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity

data class PersonCellState(
    val person: PersonWithCity,
    val selected: Boolean
)