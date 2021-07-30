package com.soares.task.data.network.services

import com.soares.task.data.network.TaskNetwork
import retrofit2.http.*


interface TaskApi {
    @POST("/Task")
    suspend fun addTask(@Body request: TaskNetwork): TaskNetwork?

    @PATCH("/Task")
    suspend fun updateTask(@Body request: TaskNetwork): TaskNetwork?

    @DELETE("/Task/{id}")
    suspend fun deleteTask(@Path(value = "id") id: Long): Boolean?

    @GET("/Task/{id}")
    suspend fun consultTask(@Path(value = "id") id: Long): TaskNetwork?

    @GET("/Task")
    suspend fun consultAllTask(): List<TaskNetwork>?
}