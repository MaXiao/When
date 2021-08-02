package com.xiaoism.time.model

import androidx.room.Entity

@Entity(primaryKeys = ["groupId", "personId"])
data class GroupPersonCrossRef(val groupId: Long, val personId: Long)