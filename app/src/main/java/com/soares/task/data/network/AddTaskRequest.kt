package com.soares.task.data.network

import com.google.gson.annotations.SerializedName

data class AddTaskRequest(
    @SerializedName("Description") var description: String = "",
    @SerializedName("PriorityId") var priorityId: Int = 0,
    @SerializedName("DueDate") var dueDate: String = "",
    @SerializedName("Complete") var complete: Boolean = false
) : BaseRequest()