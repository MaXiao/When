package com.xiaoism.time.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupWithPersons(
    @Embedded val group: Group,
    @Relation(
        entity = Person::class,
        parentColumn = "groupId",
        entityColumn = "personId",
        associateBy = Junction(GroupPersonCrossRef::class)
    )
    val persons: List<PersonWithCity>
) : Parcelable