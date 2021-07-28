package com.soares.task.domain.models

import com.google.gson.annotations.SerializedName

class Task (
    @SerializedName("Id")
    var id: Long = 0,

    @SerializedName("Description")
    var description: String = "",

    @SerializedName("PriorityId")
    var priorityId: Int = 0,

    @SerializedName("DueDate")
    var dueDate: String = "",

    @SerializedName("Complete")
    var complete: Boolean = false
)