package com.example.myapplication.presentation.ui.screen.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.RoomDb.Note
import com.example.myapplication.RoomDb.NoteType
import com.example.myapplication.RoomDb.TaskItem
import com.example.myapplication.RoomDb.models.ColorObject
import com.example.myapplication.RoomDb.viewmodel.NoteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagementScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    onSaveNote: (Note) -> Unit 
) {
    // Check if there's a selected note for editing
    val existingNote = viewModel.selectedNote
    
    // List of tasks that will be added to the note
    var tasks by remember { 
        mutableStateOf(existingNote?.tasks ?: listOf<TaskItem>()) 
    }
    
    // Initialize color from existing note or use default
    val initialColor = existingNote?.color?.let { Color(it) } ?: Color(0xFF3D5AFE)
    val initialColorName = existingNote?.color?.let { "Existing" } ?: "Indigo"
    val selectedColor = remember { 
        mutableStateOf(ColorObject(initialColor, initialColorName)) 
    }
    
    // For new task input
    var taskDescription by remember { mutableStateOf("") }
    var noteTitle by remember { 
        mutableStateOf(existingNote?.title ?: "New Task List") 
    }
    var showInputField by remember { mutableStateOf(false) }
    
    // Animation states
    val inputFieldAlpha by animateFloatAsState(
        targetValue = if (showInputField) 1f else 0f,
        animationSpec = tween(300)
    )
    
    // Focus requester for the input field
    val focusRequester = remember { FocusRequester() }
    
    // For snackbar messages
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // Flag to track if we're in edit mode
    val isEditMode = existingNote != null
    
    // Filter states
    var showCompletedTasks by remember { mutableStateOf(true) }
    
    // Display tasks filtered according to completion state
    val filteredTasks = if (showCompletedTasks) {
        tasks
    } else {
        tasks.filter { !it.isCompleted }
    }
    
    // Stats
    val completedTasksCount = tasks.count { it.isCompleted }
    val totalTasksCount = tasks.size
    val progressPercentage = if (totalTasksCount > 0) {
        (completedTasksCount.toFloat() / totalTasksCount) * 100
    } else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    OutlinedTextField(
                        value = noteTitle,
                        onValueChange = { noteTitle = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        placeholder = { 
                            Text(
                                "Task List Title",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                },
                actions = {
                    IconButton(onClick = {
                        showCompletedTasks = !showCompletedTasks
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = if (showCompletedTasks) "Showing all tasks" else "Hiding completed tasks"
                            )
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (showCompletedTasks) 
                                    R.drawable.visibility_off 
                                else 
                                    R.drawable.filter_list
                            ),
                            contentDescription = "Toggle completed tasks"
                        )
                    }
                    
                    IconButton(onClick = {
                        if (tasks.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("No tasks to save")
                            }
                        } else {
                            val note = if (isEditMode) {
                                // Update existing note
                                existingNote!!.copy(
                                    title = noteTitle.ifBlank { "Task List" },
                                    tasks = tasks,
                                    color = selectedColor.value.color.toArgb()
                                )
                            } else {
                                // Create new note
                                Note(
                                    title = noteTitle.ifBlank { "Task List" },
                                    type = NoteType.TASK_MANAGEMENT,
                                    tasks = tasks,
                                    color = selectedColor.value.color.toArgb()
                                )
                            }
                            
                            // Save to Room database
                            if (isEditMode) {
                                viewModel.updateCodeNote(note)
                            } else {
                                viewModel.addTaskNote(note)
                            }
                            
                            // Optional: Also call the original onSaveNote callback if needed
                            onSaveNote(note)
                            
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    if (isEditMode) "Task list updated" else "Task list saved"
                                )
                                delay(1000)
                                navController.popBackStack()
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.save),
                            contentDescription = "Save Note"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showInputField = true
                    // Focus on the text field when it appears
                    scope.launch {
                        delay(100) // Small delay to ensure animation starts
                        focusRequester.requestFocus()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Task progress indicator
            if (tasks.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Progress",
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                "$completedTasksCount/$totalTasksCount tasks",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Progress bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progressPercentage / 100)
                                    .height(8.dp)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                        }
                        
                        Text(
                            "${progressPercentage.toInt()}% complete",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Task list
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                if (filteredTasks.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (tasks.isEmpty()) "No tasks yet" else "No incomplete tasks",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = if (tasks.isEmpty()) "Tap + to add a new task" else "All tasks completed!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    itemsIndexed(filteredTasks) { index, task ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            ModernTaskItem(
                                task = task,
                                onTaskToggle = {
                                    // Toggle task completion status
                                    tasks = tasks.toMutableList().apply {
                                        this[tasks.indexOf(task)] = task.copy(isCompleted = !task.isCompleted)
                                    }
                                },
                                onRemoveTask = {
                                    // Remove task from the list
                                    tasks = tasks.toMutableList().apply {
                                        remove(task)
                                    }
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Task removed",
                                            actionLabel = "Undo"
                                        )
                                        if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                            tasks = tasks.toMutableList().apply {
                                                add(index, task)
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
            
            // Input field for new task (shown/hidden with animation)
            AnimatedVisibility(
                visible = showInputField,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = taskDescription,
                            onValueChange = { taskDescription = it },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            placeholder = { Text("Add a new task...") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            maxLines = 1
                        )
                        
                        IconButton(
                            onClick = {
                                if (taskDescription.isNotBlank()) {
                                    // Add task and reset
                                    tasks = tasks + TaskItem(taskDescription, isCompleted = false)
                                    taskDescription = ""
                                    showInputField = false
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Add Task"
                            )
                        }
                        
                        IconButton(
                            onClick = {
                                taskDescription = ""
                                showInputField = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Cancel"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernTaskItem(
    task: TaskItem,
    onTaskToggle: () -> Unit,
    onRemoveTask: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) 
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox icon
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .clickable { onTaskToggle() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        id = if (task.isCompleted) 
                            R.drawable.check_circle 
                        else 
                            R.drawable.radio_button_unchecked
                    ),
                    contentDescription = "Toggle Task",
                    tint = if (task.isCompleted) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Task Description
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                color = if (task.isCompleted) 
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                else 
                    MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Delete button
            IconButton(
                onClick = { onRemoveTask() },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Task",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}