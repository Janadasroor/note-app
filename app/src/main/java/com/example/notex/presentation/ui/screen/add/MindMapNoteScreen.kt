package com.example.notex.presentation.ui.screen.add

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.notex.RoomDb.Edge
import com.example.notex.RoomDb.MindMapData
import com.example.notex.RoomDb.Node
import com.example.notex.RoomDb.Note
import com.example.notex.RoomDb.NoteType
import com.example.notex.RoomDb.models.ColorObject
import com.example.notex.RoomDb.viewmodel.NoteViewModel
import com.example.notex.settings.theme.MyAppTheme
import com.example.notex.settings.theme.ThemeType
import com.example.notex.ui.components.NoteColorDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class MindMapNode(
    val id: Int,
    val text: String,
    var position: Offset,
    val color: Color = Color(0xFF4CAF50)
)

data class MindMapConnection(
    val fromNodeId: Int,
    val toNodeId: Int,
    val color: Color = Color.Gray
)

enum class MindMapMode {
    MOVE, CONNECT, ADD, EDIT, DELETE, ZOOM
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindMapNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    onSave: (Note) -> Unit
) {
    // Check if there's a selected note for editing
    val existingNote = viewModel.selectedNote
    val isEditMode = existingNote != null && existingNote.type == NoteType.MIND_MAP
    
    // State for title
    var title by remember { mutableStateOf(existingNote?.title ?: "New Mind Map") }
    
    // State for zoom and pan
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    
    // Initialize the ViewModel with existing data if editing
    LaunchedEffect(existingNote) {
        Log.d("MindMap", "LaunchedEffect triggered with existingNote: $existingNote")
        if (isEditMode && existingNote?.mindMapData != null) {
            Log.d("MindMap", "Initializing nodes and connections from existing note")
            // Convert existing nodes to MindMapNodes with positions
            val screenWidth = 1000f // Default canvas width
            val screenHeight = 800f // Default canvas height
            
            val initialNodes = existingNote.mindMapData.nodes.map { node ->
                // Position nodes in a circle initially
                val angle = (node.id.toFloat() / existingNote.mindMapData.nodes.size) * 2 * Math.PI
                val x = (screenWidth / 2 + 200 * Math.cos(angle)).toFloat()
                val y = (screenHeight / 2 + 200 * Math.sin(angle)).toFloat()
                
                MindMapNode(
                    id = node.id,
                    text = node.text,
                    position = Offset(x, y)
                )
            }
            
            val initialConnections = existingNote.mindMapData.edges.map { edge ->
                MindMapConnection(
                    fromNodeId = edge.from,
                    toNodeId = edge.to
                )
            }
            
            viewModel.initializeMindMapNodes(initialNodes)
            viewModel.initializeMindMapConnections(initialConnections)
        } else {
            Log.d("MindMap", "Initializing empty nodes and connections for new mind map")
            // Initialize with empty lists for new mind maps
            viewModel.initializeMindMapNodes(emptyList())
            viewModel.initializeMindMapConnections(emptyList())
        }
    }
    
    // Add logging to verify recomposition and state values
    Log.d("MindMap", "Composable recomposed with nodes: ${viewModel.mindMapNodes.map { it.position }}")

    // Use the ViewModel's state
    val nodes = viewModel.mindMapNodes
    val connections = viewModel.mindMapConnections
    
    // State for node being edited
    var editingNode by remember { mutableStateOf<MindMapNode?>(null) }
    var newNodeText by remember { mutableStateOf("") }
    
    // State for connection being created
    var connectionStartNode by remember { mutableStateOf<MindMapNode?>(null) }
    var currentPointerPosition by remember { mutableStateOf(Offset.Zero) }
    
    // State for tracking node being dragged
    var draggedNodeId by remember { mutableStateOf<Int?>(null) }
    
    // State for UI mode
    var currentMode by remember { mutableStateOf(MindMapMode.MOVE) }
    
    // For color selection
    var showColorDialog by remember { mutableStateOf(false) }
    val selectedColor = remember { 
        mutableStateOf(
            ColorObject(
                existingNote?.color?.let { Color(it) } ?: Color(0xFF2196F3),
                existingNote?.color?.let { "Existing" } ?: "Blue"
            )
        )
    }
    
    // For theme
    val currentTheme = remember { mutableStateOf(
        existingNote?.themeType?.let { 
            try { ThemeType.valueOf(it) } 
            catch (e: Exception) { ThemeType.DARK }
        } ?: ThemeType.DARK
    ) }
    
    // For snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // Apply the selected theme to the UI
    MyAppTheme(themeType = currentTheme) {
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
                                    "Mind Map Title",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                ) 
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        // Mode selection: Move, Connect, Add
                        IconButton(
                            onClick = { currentMode = MindMapMode.MOVE },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (currentMode == MindMapMode.MOVE) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.PanTool,
                                contentDescription = "Move Mode",
                                tint = if (currentMode == MindMapMode.MOVE) 
                                    MaterialTheme.colorScheme.onPrimary 
                                else 
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        IconButton(
                            onClick = { currentMode = MindMapMode.CONNECT },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (currentMode == MindMapMode.CONNECT) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                contentDescription = "Connect Mode",
                                tint = if (currentMode == MindMapMode.CONNECT) 
                                    MaterialTheme.colorScheme.onPrimary 
                                else 
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        IconButton(
                            onClick = { currentMode = MindMapMode.ADD },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (currentMode == MindMapMode.ADD) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Mode",
                                tint = if (currentMode == MindMapMode.ADD) 
                                    MaterialTheme.colorScheme.onPrimary 
                                else 
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        // Zoom mode button - using a custom icon from resources
                        IconButton(
                            onClick = { currentMode = MindMapMode.ZOOM },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (currentMode == MindMapMode.ZOOM) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            // Use map icon for zoom functionality
                            Icon(
                                imageVector = Icons.Default.ZoomIn,
                                contentDescription = "Zoom Mode",
                                tint = if (currentMode == MindMapMode.ZOOM) 
                                    MaterialTheme.colorScheme.onPrimary 
                                else 
                                    MaterialTheme.colorScheme.onSurfaceVariant
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
                            if (title.isBlank()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Please enter a title")
                                }
                                return@IconButton
                            }
                            
                            if (nodes.isEmpty()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Add at least one node")
                                }
                                return@IconButton
                            }
                            
                            // Convert MindMapNodes to Nodes
                            val noteNodes = nodes.map { 
                                Node(id = it.id, text = it.text) 
                            }
                            
                            // Convert MindMapConnections to Edges
                            val noteEdges = connections.map { 
                                Edge(from = it.fromNodeId, to = it.toNodeId) 
                            }
                            
                            val mindMapData = MindMapData(
                                nodes = noteNodes,
                                edges = noteEdges
                            )
                            
                            val note = if (isEditMode) {
                                existingNote!!.copy(
                                    title = title,
                                    color = selectedColor.value.color.toArgb(),
                                    mindMapData = mindMapData,
                                    themeType = currentTheme.value.name
                                )
                            } else {
                                Note(
                                    title = title,
                                    color = selectedColor.value.color.toArgb(),
                                    type = NoteType.MIND_MAP,
                                    mindMapData = mindMapData,
                                    themeType = currentTheme.value.name
                                )
                            }
                            
                            // Save to database
                            viewModel.addMindMapNote(note)
                            
                            // Also call the original onSave callback
                            onSave(note)
                            
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    if (isEditMode) "Mind map updated" else "Mind map saved"
                                )
                                delay(1000)
                                navController.popBackStack()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save Mind Map"
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
                    .background(MaterialTheme.colorScheme.background)
                    .pointerInput(Unit) {
                        // Track pointer position for connection line
                        detectDragGestures { change, _ ->
                            if (currentMode == MindMapMode.CONNECT && connectionStartNode != null) {
                                // Update current pointer position for drawing the connection line
                                val canvasX = (change.position.x - offset.x) / scale
                                val canvasY = (change.position.y - offset.y) / scale
                                currentPointerPosition = Offset(canvasX, canvasY)
                            }
                        }
                    }
            ) {
                // Canvas for mind map with zoom and pan support
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .pointerInput(currentMode) {
                            when (currentMode) {
                                MindMapMode.ADD -> {
                                    detectTapGestures { tapOffset ->
                                        // Convert tap position to canvas coordinates considering zoom and pan
                                        val canvasX = (tapOffset.x - offset.x) / scale
                                        val canvasY = (tapOffset.y - offset.y) / scale
                                        val canvasOffset = Offset(canvasX, canvasY)
                                        
                                        val newId = if (nodes.isEmpty()) 1 else nodes.maxOf { it.id } + 1
                                        val newNode = MindMapNode(
                                            id = newId,
                                            text = "",
                                            position = canvasOffset,
                                            color = selectedColor.value.color
                                        )
                                        
                                        // Add node to ViewModel
                                        viewModel.addNode(newNode)
                                        
                                        // Set up for editing
                                        editingNode = newNode
                                        newNodeText = ""
                                    }
                                }
                                MindMapMode.ZOOM -> {
                                    detectTransformGestures { _, pan, zoom, _ ->
                                        // Update scale with zoom gesture - limit to 200% (2.0f)
                                        scale = (scale * zoom).coerceIn(0.5f, 2.0f)
                                        
                                        // Update offset with pan gesture
                                        offset = Offset(
                                            x = offset.x + pan.x,
                                            y = offset.y + pan.y
                                        )
                                    }
                                }
                                MindMapMode.MOVE -> {
                                    // Only allow panning if not dragging a node
                                    if (draggedNodeId == null) {
                                        detectDragGestures { change, dragAmount ->
                                            change.consume()
                                            offset = Offset(
                                                x = offset.x + dragAmount.x,
                                                y = offset.y + dragAmount.y
                                            )
                                        }
                                    }
                                }
                                MindMapMode.CONNECT -> {
                                    // Track pointer position for connection line
                                    detectDragGestures { change, _ ->
                                        if (connectionStartNode != null) {
                                            // Update current pointer position for drawing the connection line
                                            val canvasX = (change.position.x - offset.x) / scale
                                            val canvasY = (change.position.y - offset.y) / scale
                                            currentPointerPosition = Offset(canvasX, canvasY)
                                        }
                                    }
                                }
                                else -> {} // Other modes handled by node interactions
                            }
                        }
                ) {
                    // Apply zoom and pan transformations to the canvas
                    translate(offset.x, offset.y) {
                        scale(scale) {
                            // Draw connections
                            connections.forEach { connection ->
                                val fromNode = nodes.find { it.id == connection.fromNodeId }
                                val toNode = nodes.find { it.id == connection.toNodeId }
                                
                                if (fromNode != null && toNode != null) {
                                    // Draw arrow from fromNode to toNode
                                    drawLine(
                                        color = connection.color,
                                        start = fromNode.position,
                                        end = toNode.position,
                                        strokeWidth = 2f / scale, // Adjust stroke width for zoom
                                        cap = StrokeCap.Round
                                    )
                                    
                                    // Draw arrow head
                                    val angle = kotlin.math.atan2(
                                        toNode.position.y - fromNode.position.y,
                                        toNode.position.x - fromNode.position.x
                                    )
                                    val arrowLength = 15f
                                    val arrowAngle = Math.PI / 6 // 30 degrees
                                    
                                    val arrowPoint1 = Offset(
                                        toNode.position.x - arrowLength * kotlin.math.cos(angle - arrowAngle).toFloat(),
                                        toNode.position.y - arrowLength * kotlin.math.sin(angle - arrowAngle).toFloat()
                                    )
                                    
                                    val arrowPoint2 = Offset(
                                        toNode.position.x - arrowLength * kotlin.math.cos(angle + arrowAngle).toFloat(),
                                        toNode.position.y - arrowLength * kotlin.math.sin(angle + arrowAngle).toFloat()
                                    )
                                    
                                    val arrowPath = Path().apply {
                                        moveTo(toNode.position.x, toNode.position.y)
                                        lineTo(arrowPoint1.x, arrowPoint1.y)
                                        lineTo(arrowPoint2.x, arrowPoint2.y)
                                        close()
                                    }
                                    
                                    drawPath(
                                        path = arrowPath,
                                        color = connection.color,
                                        style = Stroke(width = 2f / scale) // Adjust stroke width for zoom
                                    )
                                }
                            }
                            
                            // Draw temporary connection line if in connect mode
                            if (currentMode == MindMapMode.CONNECT && connectionStartNode != null) {
                                drawLine(
                                    color = Color.LightGray,
                                    start = connectionStartNode!!.position,
                                    end = currentPointerPosition,
                                    strokeWidth = 2f / scale, // Adjust stroke width for zoom
                                    cap = StrokeCap.Round
                                )
                                
                                // Draw a small circle at the end of the line to indicate where it will connect
                                drawCircle(
                                    color = Color.LightGray,
                                    radius = 5f / scale,
                                    center = currentPointerPosition
                                )
                            }
                        }
                    }
                }
                
                // Draw nodes on top of canvas with zoom and pan applied
                nodes.forEach { node ->
                    val screenPosition = Offset(
                        x = node.position.x * scale + offset.x,
                        y = node.position.y * scale + offset.y
                    )
                    
                    MindMapNodeUI(
                        node = node,
                        currentMode = currentMode,
                        isSelected = connectionStartNode == node,
                        scale = scale,
                        screenPosition = screenPosition,
                        onNodeDragStart = {
                            if (currentMode == MindMapMode.MOVE) {
                                draggedNodeId = node.id
                            }
                        },
                        onNodeDragEnd = {
                            draggedNodeId = null
                        },
                        onNodeMove = { newOffset ->
                            if (currentMode == MindMapMode.MOVE) {
                                // Convert screen position to canvas position
                                val canvasX = (newOffset.x - offset.x) / scale
                                val canvasY = (newOffset.y - offset.y) / scale
                                val canvasOffset = Offset(canvasX, canvasY)
                                
                                // Update node position in ViewModel
                                viewModel.updateNodePosition(node.id, canvasOffset)
                                
                                // Force recomposition
                                draggedNodeId = node.id
                            }
                        },
                        onNodeClick = {
                            when (currentMode) {
                                MindMapMode.CONNECT -> {
                                    if (connectionStartNode == null) {
                                        // Start connection
                                        connectionStartNode = node
                                        currentPointerPosition = node.position
                                    } else if (connectionStartNode != node) {
                                        // Complete connection
                                        val newConnection = MindMapConnection(
                                            fromNodeId = connectionStartNode!!.id,
                                            toNodeId = node.id
                                        )
                                        // Add connection to ViewModel
                                        viewModel.addConnection(newConnection)
                                        connectionStartNode = null
                                    }
                                }
                                MindMapMode.EDIT -> {
                                    editingNode = node
                                    newNodeText = node.text
                                }
                                MindMapMode.DELETE -> {
                                    // Remove node using ViewModel
                                    viewModel.removeNode(node.id)
                                }
                                else -> {} // Other modes don't need node click handling
                            }
                        },
                        onNodeLongPress = {
                            editingNode = node
                            newNodeText = node.text
                        }
                    )
                }
                
                // Mode selection buttons at bottom
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { currentMode = MindMapMode.EDIT },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (currentMode == MindMapMode.EDIT) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Mode",
                            tint = if (currentMode == MindMapMode.EDIT) 
                                MaterialTheme.colorScheme.onPrimary 
                            else 
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    IconButton(
                        onClick = { currentMode = MindMapMode.DELETE },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (currentMode == MindMapMode.DELETE) 
                                MaterialTheme.colorScheme.error 
                            else 
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Mode",
                            tint = if (currentMode == MindMapMode.DELETE) 
                                MaterialTheme.colorScheme.onError 
                            else 
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    IconButton(
                        onClick = { 
                            connectionStartNode = null
                            currentMode = MindMapMode.MOVE 
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Cancel",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Reset zoom and pan button
                    IconButton(
                        onClick = { 
                            scale = 1f
                            offset = Offset.Zero
                        }
                    ) {
                        // Use content_copy icon for reset zoom functionality
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset Zoom",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Help text based on current mode
                Text(
                    text = when (currentMode) {
                        MindMapMode.MOVE -> "Drag nodes to move them or drag canvas to pan"
                        MindMapMode.CONNECT -> "Tap two nodes to connect them"
                        MindMapMode.ADD -> "Tap anywhere to add a new node"
                        MindMapMode.EDIT -> "Tap a node to edit its text"
                        MindMapMode.DELETE -> "Tap a node to delete it"
                        MindMapMode.ZOOM -> "Pinch to zoom (max 200%), drag to pan"
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                
                // Node editing dialog
                if (editingNode != null) {
                    Dialog(onDismissRequest = { editingNode = null }) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Node Text",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                OutlinedTextField(
                                    value = newNodeText,
                                    onValueChange = { newNodeText = it },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                
                                Spacer(modifier = Modifier.height(24.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    TextButton(onClick = { editingNode = null }) {
                                        Text("Cancel")
                                    }
                                    
                                    Spacer(modifier = Modifier.width(8.dp))
                                    
                                    Button(onClick = {
                                        if (newNodeText.isNotBlank()) {
                                            // Update node text in ViewModel
                                            viewModel.updateNodeText(editingNode!!.id, newNodeText)
                                        }
                                        editingNode = null
                                    }) {
                                        Text("Save")
                                    }
                                }
                            }
                        }
                    }
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

    // Add logging to track mode changes
    LaunchedEffect(currentMode) {
        Log.d("MindMap", "Current mode changed to: $currentMode")
    }

    // Add logging to track node position updates
    nodes.forEach { node ->
        Log.d("MindMap", "Node ${node.id} position: ${node.position}")
    }
}

@Composable
fun MindMapNodeUI(
    node: MindMapNode,
    currentMode: MindMapMode,
    isSelected: Boolean,
    scale: Float,
    screenPosition: Offset,
    onNodeMove: (Offset) -> Unit,
    onNodeClick: () -> Unit,
    onNodeLongPress: () -> Unit,
    onNodeDragStart: () -> Unit = {},
    onNodeDragEnd: () -> Unit = {}
) {
    val nodeSize = (80 * scale).coerceAtLeast(40f).dp
    
    Box(
        modifier = Modifier
            .offset(
                x = screenPosition.x.dp - (nodeSize / 2),
                y = screenPosition.y.dp - (nodeSize / 2)
            )
            .size(nodeSize)
            .clip(CircleShape)
            .background(
                if (isSelected) node.color.copy(alpha = 0.7f) 
                else node.color.copy(alpha = 0.5f)
            )
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else node.color,
                shape = CircleShape
            )
            .clickable { onNodeClick() }
            .pointerInput(currentMode) {
                if (currentMode == MindMapMode.MOVE) {
                    detectDragGestures(
                        onDragStart = { onNodeDragStart() },
                        onDragEnd = { onNodeDragEnd() },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val newPosition = Offset(
                                x = screenPosition.x + dragAmount.x,
                                y = screenPosition.y + dragAmount.y
                            )
                            Log.d("MindMap", "Dragging node to new position: $newPosition")
                            onNodeMove(newPosition)
                        }
                    )
                }
                
                detectTapGestures(
                    onLongPress = { onNodeLongPress() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Log.d("MindMap", "Rendering node ${node.id} at screen position: $screenPosition")
        Text(
            text = node.text,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = (12 * scale).coerceAtLeast(8f).sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp)
        )
    }
} 