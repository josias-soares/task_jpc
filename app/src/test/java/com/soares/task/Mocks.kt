package com.soares.task

import com.soares.task.domain.models.Task

var taskMock = Task(1, "description", 2, "01/12/2021", false)
var newTaskMock = Task(id = 0, "description", 2, "01/12/2021", false)

var listTaskMock = arrayListOf(
    Task(1, "description 1", 2, "01/12/2021", false),
    Task(2, "description 2", 3, "02/12/2021", true),
    Task(3, "description 3", 1, "01/11/2021", false),
    Task(4, "description 4", 2, "20/12/2021", true)
)