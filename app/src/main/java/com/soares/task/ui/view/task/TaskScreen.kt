package com.soares.task.ui.view.task

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soares.task.domain.models.Task
import com.soares.task.ui.components.DropDownList

@Preview
@Composable
fun TaskScreen(
    task: Task?,
    onChangeDescription: ((String) -> Unit),
    onChangePriorityId: ((Int) -> Unit),
    onChangeDueDate: ((String) -> Unit),
    onChangeComplete: ((Boolean) -> Unit),
    onSaveTask: (() -> Unit)?,
) {
    val descriptionChangeListener: (String) -> Unit = {

    }

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = task?.description ?: "",
            onValueChange = {
                onChangeDescription.invoke(it.trim())
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )
        Spacer(modifier = Modifier.size(8.dp))
        PrioritySelection(task?.priorityId){
            onChangePriorityId.invoke(it)
        }
        Spacer(modifier = Modifier.size(8.dp))
        CheckboxComplete(task?.complete ?: false) { checked ->
            onChangeComplete.invoke(checked)
        }

    }
}

@Composable
private fun PrioritySelection(selected: Int?, onSelectPriority: (Int) -> Unit) {
    val priorityList = arrayListOf(
        "HIGH",
        "MEDIUM",
        "LOW",
    )


    var initialValue = ""
    selected?.let {
        initialValue = priorityList[it]
    }


    val text = remember { mutableStateOf(initialValue) } // initial value
    val isOpen = remember { mutableStateOf(false) } // initial value
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
        priorityList.forEachIndexed() { index, s -> if (s == it) onSelectPriority.invoke(index)}
    }
    Box {
        Column {
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text(text = "Priority") },
                modifier = Modifier.fillMaxWidth()
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = priorityList,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { isOpen.value = true }
                )
        )
    }
}

@Composable
fun CheckboxComplete(checked: Boolean = false, onCheckedChange: (Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(checked) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckedChange(it)
            }
        )
        Text("Complete")
    }

}