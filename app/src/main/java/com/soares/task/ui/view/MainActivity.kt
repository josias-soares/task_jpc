package com.soares.task.ui.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.google.gson.Gson
import com.soares.task.domain.models.Task
import com.soares.task.ui.view.task.TaskScreen
import com.soares.task.ui.viewmodel.MainViewModel
import com.soares.task.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                Scaffold() {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screens.Home.route
                        ) {
                            composable(Screens.Home.route) {
                                HomeActivityScreen(this@MainActivity.mainViewModel, navController)
                            }
                            composable(
                                route = "${Screens.Task.route}/{$ARG_TASK_ID}",
                                arguments = listOf(navArgument(ARG_TASK_ID) {
                                    type = NavType.LongType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getLong(ARG_TASK_ID)?.let { arg ->
                                    val task =
                                        mainViewModel.tasks.value?.first { it.id == arg }
                                    TaskActivityScreen(
                                        task,
                                        this@MainActivity.taskViewModel,
                                        navController
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }


    @Composable
    private fun HomeActivityScreen(viewModel: MainViewModel, navController: NavController) {

        val tasks = arrayListOf(
            Task(1, "description 1", 2, "01/12/2021", false),
            Task(2, "description 2", 0, "02/12/2021", true),
            Task(3, "description 3", 1, "01/11/2021", false),
            Task(4, "description 4", 2, "20/12/2021", true),
            Task(5, "description 1", 2, "01/12/2021", false),
            Task(6, "description 2", 0, "02/12/2021", true),
            Task(7, "description 3", 1, "01/11/2021", false),
            Task(8, "description 4", 2, "20/12/2021", true),
            Task(9, "description 1", 2, "01/12/2021", false),
            Task(10, "description 2", 0, "02/12/2021", true),
            Task(11, "description 3", 1, "01/11/2021", false),
            Task(12, "description 4", 2, "20/12/2021", true),
            Task(13, "description 1", 2, "01/12/2021", false),
            Task(14, "description 2", 0, "02/12/2021", true),
            Task(15, "description 3", 1, "01/11/2021", false),
            Task(16, "description 4", 2, "20/12/2021", true),
            Task(17, "description 1", 2, "01/12/2021", false),
            Task(18, "description 2", 0, "02/12/2021", true),
            Task(19, "description 3", 1, "01/11/2021", false),
        )
        viewModel.setTasks(tasks)

        HomeScreen(
            tasks = tasks,
            onAddTask = { navController.navigate("task") },
            onRemoveTask = { viewModel.deleteTask(it) },
            onEditTask = {
                val taskString = Gson().toJson(it)
                navController.navigate("${Screens.Task.route}/${it.id}")
            },
            onCompleteTask = { viewModel.completeTask(it) },
        )
    }

    @Composable
    fun TaskActivityScreen(task: Task?, viewModel: TaskViewModel, navController: NavController) {
        viewModel.apply {
            createdTask.observe(this@MainActivity, Observer {
                navController.navigateUp()
            })
        }

        TaskScreen(
            task = task,
            onChangeDescription = { viewModel.changeDescription(it) },
            onChangePriorityId = { viewModel.changePriorityId(it) },
            onChangeDueDate = { viewModel.changeDueDate(it) },
            onChangeComplete = { viewModel.changeComplete(it) },
            onSaveTask = { viewModel.saveTask() }
        )
    }
}