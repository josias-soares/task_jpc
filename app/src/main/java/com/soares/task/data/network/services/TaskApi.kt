package com.soares.task.data.network.services

import com.soares.task.data.network.AddTaskRequest
import com.soares.task.data.network.TaskNetwork
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface TaskApi {
    @POST("/Task")
    suspend fun addTask(@Body request: AddTaskRequest): TaskNetwork?

    @GET("/Task/{id}")
    suspend fun consultTask(@Path(value = "id") id: Long): TaskNetwork?

    @GET("/Task")
    suspend fun consultAllTask(): List<TaskNetwork>?
}