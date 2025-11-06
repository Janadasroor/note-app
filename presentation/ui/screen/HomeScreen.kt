package com.example.myapplication.presentation.ui.screen

import DrawerContent
import android.app.Application
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.RoomDb.Note
import com.example.myapplication.RoomDb.NoteDatabase
import com.example.myapplication.RoomDb.NoteType
import com.example.myapplication.ui.components.AppBar
import com.example.myapplication.ui.components.dialogs.ConfirmAlertDialog
import com.example.myapplication.ui.components.dialogs.OptionsDialog
import com.example.myapplication.RoomDb.viewmodel.NoteViewModel
import com.example.myapplication.RoomDb.viewmodel.ViewModelFactory
import com.example.myapplication.auth.data.userrepo.UserRepository
import com.example.myapplication.auth.domain.usecase.user.GetLoginInfoUseCase
import com.example.myapplication.auth.domain.usecase.user.LoadPostsUseCase
import com.example.myapplication.auth.domain.usecase.user.LogoutUseCase
import com.example.myapplication.auth.domain.usecase.user.UserUseClass
import com.example.myapplication.auth.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val context = LocalContext.current

    // Mock repository and database
    val userRepo = UserRepository(context) // Use a simple mock implementation for preview
    val database = mockDatabase(context) // Use a mock or simplified database for preview

    // NoteViewModel can be initialized with a mock or test repository
    val noteViewModel:NoteViewModel = viewModel(factory = ViewModelFactory(
        database = database,
        context = context,
        userRepo = userRepo
    ))
    // Create a mock or simplified NavController for preview purposes
    val navController = rememberNavController()

    HomeScreen(
        viewModel = noteViewModel,
        navController = navController,
        userViewModel = UserViewModel(
            userUseClass = UserUseClass(
                getLoginInfoUseCase = GetLoginInfoUseCase(userRepo),
                loadPostsUseCase = LoadPostsUseCase(userRepo),
                logoutUseCase = LogoutUseCase(userRepo)
            ),
            context = context
        )
    )
}

// Simple mock for the database or repository, replace with actual mock logic
fun mockDatabase(context: Context): NoteDatabase {
    return NoteDatabase.getDatabase(context) // Or mock your database for preview
}

//region home ui
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: NoteViewModel, navController: NavController,userViewModel: UserViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


   val notes by viewModel.allNote.collectAsState(initial = emptyList())
    var allNote by remember { mutableStateOf<List<Note>>(emptyList()) }
    var showOptionDialog = remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<NoteType?>(null) }
    var showDeleteDialog= remember { mutableStateOf(false) }
    var deletedNote= remember { mutableStateOf<Note?>(null) }
    // Update allNote when selectedFilter changes
    LaunchedEffect(notes, selectedFilter) {
        allNote = when (selectedFilter) {
            null -> notes
            else -> notes.filter { it.type == selectedFilter }
        }
    }

   ModalNavigationDrawer (
       drawerContent = {
           DrawerContent(
               onClose = {}
               ,userViewModel
               ,navController
           ) },

       drawerState =drawerState,

   ) {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        contentColor =  MaterialTheme.colorScheme.onBackground,

    ){
        padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            //TODO App Bar
            AppBar( showOptionDialog = showOptionDialog,onDrawerButtonClick={
                scope.launch {
                    drawerState.open()
                }
            })

            //TODO Top Body Bar
            TopBodyBar(
                selectedFilter = selectedFilter,
                onFilterSelected = {
                        noteType -> println(noteType?.name)
                    selectedFilter = noteType

                    println(selectedFilter?.name)
                    println(allNote.size)
                }
            )
            //TODO show option dialog before add note
            if(showOptionDialog.value){
                OptionsDialog(
                    onDismiss = {showOptionDialog.value=false},
                    onOptionSelected = {
                        //TODO Manage navigation for Add screens
                            noteType ->
                        //TODO Set the selected note type in viewModel class
                        viewModel.selectedNoteType = noteType
                        // Clear selectedNote when creating a new note
                        viewModel.selectedNote = null
                        println("${ viewModel?.selectedNoteType?.name }")
                        showOptionDialog.value=false
                        when(viewModel.selectedNoteType){
                            NoteType.CODE->  navController.navigate("addCode")
                            NoteType.TASK_MANAGEMENT -> navController.navigate("addTask")
                            NoteType.MIND_MAP ->  navController.navigate("addMindMap")
                            NoteType.TEXT_NOTE -> navController.navigate("addTextNote")
                            null -> {}
                        }

                    },

                    )
            }
            if(showDeleteDialog.value){
                ConfirmAlertDialog(
                    onDismiss = {showDeleteDialog.value=false },
                    onConfirm = {
                        if(deletedNote.value!=null){
                            viewModel.deleteNote(deletedNote.value)
                        }
                        showDeleteDialog.value=false
                    },
                    msg = "Do you want to delete it.",
                    title = "Confirm delete"
                )
            }

            //TODO Display list of notes
            if (allNote.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(allNote, key = { it.id }) { note ->
                        NoteItem(
                            note = note,
                            onEdit = {
                                //TODO Go to Edit Screen
                                viewModel.selectedNote = note

                                when(note.type){
                                    NoteType.CODE -> navController.navigate("editCodeNote")
                                    NoteType.TASK_MANAGEMENT -> navController.navigate("addTask")
                                    NoteType.MIND_MAP -> navController.navigate("editCodeNote")
                                    NoteType.TEXT_NOTE -> navController.navigate("addTextNote")
                                    null -> {}
                                }
                            },
                            onDelete = {
                                showDeleteDialog.value=true
                                deletedNote.value=note
                            },
                            onClick = {
                                //TODO Go to Read Screen
                                viewModel.selectedNote = note
                                when(note.type){
                                    NoteType.CODE -> navController.navigate("readCodeNote")
                                    NoteType.TASK_MANAGEMENT -> {
                                        // For task management notes, navigate to the task management screen
                                        navController.navigate("addTask")
                                    }
                                    NoteType.MIND_MAP -> navController.navigate("addMindMap")
                                    NoteType.TEXT_NOTE -> navController.navigate("addTextNote")
                                    null -> {}
                                }
                            },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            } else {
                // No Notes UI
                EmptyStateUI(
                    navController = navController,
                    onCreateNoteClick = {
                        // Clear selectedNote when creating a new note from empty state
                        viewModel.selectedNote = null
                        showOptionDialog.value = true
                    }
                )
            }
        }
    }
   }


}

//TODO Note item (Card)
@Composable
fun NoteItem(note: Note, onEdit: ()->Unit, onDelete: () -> Unit, onClick: () -> Unit, modifier: Modifier) {
    val noteColor = Color(note.color)
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            focusedElevation = 6.dp
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = noteColor.copy(alpha = 0.3f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            noteColor.copy(alpha = 0.15f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Type Indicator (Left Side)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(getNoteTypeColor(note.type))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(getNoteTypeIcon(note.type)),
                        contentDescription = note.type?.name ?: "Note Type",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }

                Spacer(modifier = Modifier.width(12.dp)) // Increased spacing between icon and text

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Type Label Above Title
                    Text(
                        text = note.type?.name ?: "Unknown",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = getNoteTypeColor(note.type),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                //TODO delete button
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ){
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Note",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

// Function to get color based on note type
@Composable
fun getNoteTypeColor(type: NoteType?): Color {
    return when (type) {
        NoteType.CODE -> Color(0xFF4CAF50) // Green for Code
        NoteType.MIND_MAP -> Color(0xFF03A9F4) // Blue for Mind Map
        NoteType.TASK_MANAGEMENT -> Color(0xFFFFC107) // Yellow for Tasks
        NoteType.TEXT_NOTE -> Color(0xFF9C27B0) // Purple for Text Note
        else -> Color.Gray
    }
}

// Function to get icon based on note type
@Composable
fun getNoteTypeIcon(type: NoteType?): Int {
    return when (type) {
        NoteType.CODE -> R.drawable.code // Code icon
        NoteType.MIND_MAP -> R.drawable.map// Mind Map icon
        NoteType.TASK_MANAGEMENT ->R.drawable.check_circle_outline// Task icon
        NoteType.TEXT_NOTE -> R.drawable.text_note // Text Note icon
        else -> R.drawable.description
    }
}

// Empty State UI
@Composable
fun EmptyStateUI(navController: NavController, onCreateNoteClick: () -> Unit) {
    val animatedAlpha = remember { androidx.compose.animation.core.Animatable(0f) }
    val animatedScale = remember { androidx.compose.animation.core.Animatable(0.8f) }
    
    LaunchedEffect(Unit) {
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
        animatedScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .alpha(animatedAlpha.value)
                .scale(animatedScale.value)
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.event_note),
                    contentDescription = "No notes",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "No notes found",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Create your first note to get started",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onCreateNoteClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_note),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Create Note",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

//TODO Top Body Bar

@Composable
fun TopBodyBar(
    selectedFilter: NoteType?,
    onFilterSelected: (NoteType?) -> Unit
) {
    var scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // All Notes Button
        FilterButton(
            label = "All",
            isSelected = selectedFilter == null,
            color = Color.Gray,
            onClick = { onFilterSelected(null) }
        )

        // Code Notes Button
        FilterButton(
            label = "Code",
            isSelected = selectedFilter == NoteType.CODE,
            color = Color(0xFF4CAF50),
            onClick = { onFilterSelected(NoteType.CODE) }
        )

        // Task Management Button
        FilterButton(
            label = "Tasks",
            isSelected = selectedFilter == NoteType.TASK_MANAGEMENT,
            color = Color(0xFFFFC107),
            onClick = { onFilterSelected(NoteType.TASK_MANAGEMENT) }
        )

        // Mind Map Button
        FilterButton(
            label = "Mind Map",
            isSelected = selectedFilter == NoteType.MIND_MAP,
            color = Color(0xFF03A9F4),
            onClick = { onFilterSelected(NoteType.MIND_MAP) }
        )
        
        // Text Note Button
        FilterButton(
            label = "Text",
            isSelected = selectedFilter == NoteType.TEXT_NOTE,
            color = Color(0xFF9C27B0),
            onClick = { onFilterSelected(NoteType.TEXT_NOTE) }
        )
    }
}

// Reusable Filter Button
@Composable
fun FilterButton(label: String, isSelected: Boolean, color: Color, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) {
        color
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    }
    
    val contentColor = if (isSelected) {
        // Determine if we should use dark or light text based on background color
        if (color.luminance() > 0.5f) Color.Black else Color.White
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
    }
    
    val borderColor = if (isSelected) {
        color
    } else {
        color.copy(alpha = 0.3f)
    }
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        shadowElevation = if (isSelected) 4.dp else 1.dp,
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .height(36.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = contentColor
            )
        }
    }
}

//endregion