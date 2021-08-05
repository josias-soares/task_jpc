package com.soares.task.ui.view

sealed class Screens(val route: String) {
    object Home : Screens("Home")
    object Task : Screens("Task")
}