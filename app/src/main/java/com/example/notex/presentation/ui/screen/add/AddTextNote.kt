package com.example.notex.presentation.ui.screen.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notex.RoomDb.Note
import com.example.notex.RoomDb.NoteType
import com.example.notex.RoomDb.models.ColorObject
import com.example.notex.RoomDb.viewmodel.NoteViewModel
import com.example.notex.settings.dialogs.ThemeDialog
import com.example.notex.settings.theme.MyAppTheme
import com.example.notex.settings.theme.ThemeType
import com.example.notex.ui.components.NoteColorDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    onSaveNote: (Note) -> Unit
) {
    // Check if there's a selected note for editing
    val existingNote = viewModel.selectedNote
    val isEditMode = existingNote != null
    
    // Initialize title and content from existing note or use default
    var title by remember { 
        mutableStateOf(existingNote?.title ?: "New Text Note") 
    }
    
    var content by remember { 
        mutableStateOf(existingNote?.content ?: "") 
    }
    
    // For line numbering, we need to track the number of lines
    val lines = content.lines()
    val lineCount = lines.size
    
    // Use the existing note's setting or default to true for new notes
    var showLineNumbers by remember { 
        mutableStateOf(existingNote?.lineNumbers ?: true) 
    }
    
    // Initialize color from existing note or use default
    val initialColor = existingNote?.color?.let { Color(it) } ?: Color(0xFF2196F3) // Blue
    val initialColorName = existingNote?.color?.let { "Existing" } ?: "Blue"
    val selectedColor = remember { 
        mutableStateOf(ColorObject(initialColor, initialColorName)) 
    }
    
    // For theme selection - initialize from existing note if available
    var showThemeDialog by remember { mutableStateOf(false) }
    var showColorDialog by remember { mutableStateOf(false) }
    val currentTheme = remember { 
        mutableStateOf(
            existingNote?.themeType?.let { 
                try {
                    ThemeType.valueOf(it)
                } catch (e: Exception) {
                    ThemeType.LIGHT
                }
            } ?: ThemeType.LIGHT
        ) 
    }
    
    // For snackbar messages
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // Scroll state for the content
    val scrollState = rememberScrollState()
    
    // Verify mode when screen is first displayed
    LaunchedEffect(Unit) {
        // Log the current mode for debugging
        println("TextNoteScreen - isEditMode: $isEditMode, selectedNote: ${existingNote?.id}")
    }
    
    // Apply the selected theme to the UI
    MyAppTheme(themeType = currentTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent
                                ),
                                textStyle = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                placeholder = { 
                                    Text(
                                        "Text Note Title",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    ) 
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        actions = {
                            // Toggle line numbers
                            IconButton(onClick = { showLineNumbers = !showLineNumbers }) {
                                Icon(
                                    imageVector = if (showLineNumbers) Icons.Default.VisibilityOff else Icons.Default.FormatListNumbered,
                                    contentDescription = "Toggle Line Numbers"
                                )
                            }
                            
                            // Theme selection
                            IconButton(onClick = { showThemeDialog = true }) {
                                Icon(
                                    imageVector = Icons.Default.BrightnessMedium,
                                    contentDescription = "Select Theme"
                                )
                            }
                            
                            // Color selection
                            IconButton(onClick = { showColorDialog = true }) {
                                Box(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(24.dp)
                                        .background(
                                            color = selectedColor.value.color,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                )
                            }
                            
                            // Save button
                            IconButton(onClick = {
                                if (title.isBlank() && content.isBlank()) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Note is empty")
                                    }
                                } else {
                                    val note = if (isEditMode) {
                                        existingNote!!.copy(
                                            title = title.ifBlank { "Text Note" },
                                            content = content,
                                            color = selectedColor.value.color.toArgb(),
                                            lineNumbers = showLineNumbers,
                                            themeType = currentTheme.value.name
                                        )
                                    } else {
                                        Note(
                                            title = title.ifBlank { "Text Note" },
                                            content = content,
                                            type = NoteType.TEXT_NOTE,
                                            color = selectedColor.value.color.toArgb(),
                                            lineNumbers = showLineNumbers,
                                            themeType = currentTheme.value.name
                                        )
                                    }
                                    
                                    // Save to Room database
                                    if (isEditMode) {
                                        viewModel.updateCodeNote(note)
                                    } else {
                                        viewModel.addTextNote(note)
                                    }
                                    
                                    // Also call the original onSaveNote callback
                                    onSaveNote(note)
                                    
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            if (isEditMode) "Text note updated" else "Text note saved"
                                        )
                                        delay(1000)
                                        navController.popBackStack()
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Save Note"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                },
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Text editor with line numbers
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Line numbers column (if enabled)
                        if (showLineNumbers) {
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .fillMaxHeight()
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .verticalScroll(scrollState)
                                        .padding(end = 8.dp, top = 16.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    for (i in 1..maxOf(1, lineCount)) {
                                        Text(
                                            text = "$i",
                                            style = TextStyle(
                                                color = selectedColor.value.color.copy(alpha = 0.8f),
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Medium
                                            ),
                                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                                        )
                                    }
                                    // Add extra space at the bottom for new lines
                                    Spacer(modifier = Modifier.height(200.dp))
                                }
                            }
                        }
                        
                        // Text content area
                        BasicTextField(
                            value = content,
                            onValueChange = { content = it },
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(scrollState)
                                .padding(
                                    start = if (showLineNumbers) 4.dp else 0.dp,
                                    top = 16.dp, 
                                    bottom = 16.dp
                                ),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                lineHeight = 24.sp
                            )
                        )
                    }
                    
                    // Theme Dialog
                    if (showThemeDialog) {
                        ThemeDialog(
                            currentTheme = currentTheme,
                            onDismiss = { showThemeDialog = false },
                            onThemeSelected = { theme ->
                                currentTheme.value = theme
                            }
                        )
                    }
                    
                    // Color Dialog
                    if (showColorDialog) {
                        NoteColorDialog(
                            selectedColor = selectedColor,
                            onDismiss = { showColorDialog = false }
                        )
                    }
                }
            }
        }
    }
} 