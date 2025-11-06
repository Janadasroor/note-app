package com.example.myapplication.TEST


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.RoomDb.Edge
import com.example.myapplication.RoomDb.MindMapData
import com.example.myapplication.RoomDb.Node
import com.example.myapplication.RoomDb.Note
import com.example.myapplication.RoomDb.NoteType

@Preview
@Composable
fun MindMapNoteScreen(
//    navController: NavController,
//    viewModel: NoteViewModel,
//    onSave: (Note) -> Unit, // Callback to save the note
) {
    var title by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.Gray) }
    var nodeText by remember { mutableStateOf("") }

    // Using remember to store nodes and edges to avoid recomposing unnecessarily
    val nodes = remember { mutableStateListOf<Node>() }
    val edges = remember { mutableStateListOf<Edge>() }

    // UI for adding nodes
    Column(modifier = Modifier.padding(16.dp)) {
        // Title Input with Box
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            //elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Color Picker with Box
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            //  elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { color = Color.Blue }) {
                    Text("Select Color")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Node with Box
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            //     elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = nodeText,
                        onValueChange = { nodeText = it },
                        label = { Text("Node Text") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        if (nodeText.isNotEmpty()) {
                            val newNode = Node(id = nodes.size + 1, text = nodeText)
                            nodes.add(newNode) // Add node directly to remember list
                            nodeText = "" // Reset node text
                        }
                    }) {
                        Text("Add Node")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display added nodes
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            //      elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(nodes) { node ->
                        Text(text = "Node: ${node.text}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Edge (connect nodes) with Box
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            //  elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var fromNode by remember { mutableStateOf(1) } // Default to node 1
                    var toNode by remember { mutableStateOf(2) } // Default to node 2
                    var expanded by remember { mutableStateOf(false) }

                    // From node and To node picker (can be dropdown or number)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { expanded = !expanded }) {
                            Text("Select Nodes")
                        }

                        if (expanded) {
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                nodes.forEach { node ->
                                    DropdownMenuItem(onClick = {
                                        fromNode = node.id
                                        expanded = false // Close dropdown
                                    }, text = {Text("From Node: ${node.text}")})
                                    DropdownMenuItem(onClick = {
                                        toNode = node.id
                                        expanded = false // Close dropdown
                                    }, text = { Text("To Node: ${node.text}") })
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        if (fromNode != toNode) {
                            val newEdge = Edge(from = fromNode, to = toNode)
                            edges.add(newEdge) // Add edge directly to remember list
                        }
                    }) {
                        Text("Add Edge")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display added edges with Box
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            //    elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(edges) { edge ->
                        Text(text = "Edge: ${edge.from} -> ${edge.to}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save button with Box
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            //  elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(onClick = {
                    val mindMapData = MindMapData(nodes = nodes, edges = edges)
                    val note = Note(
                        title = title,
                        color = color.toArgb(),
                        type = NoteType.MIND_MAP,
                        mindMapData = mindMapData
                    )
                    //onSave(note) // Save the note using the onSave callback
                }) {
                    Text("Save Note")
                }
            }
        }
    }
}
