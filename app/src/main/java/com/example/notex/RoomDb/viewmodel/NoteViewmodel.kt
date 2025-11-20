package com.example.notex.RoomDb.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notex.RoomDb.CodeBlock
import com.example.notex.RoomDb.Note
import com.example.notex.RoomDb.NoteDao
import com.example.notex.RoomDb.NoteDatabase
import com.example.notex.RoomDb.NoteType
import com.example.notex.RoomDb.TaskItem
import com.example.notex.auth.data.LoginPrefModel
import com.example.notex.auth.data.RetrofitInstance
import com.example.notex.auth.data.SecureLoginDataStoreServices
import com.example.notex.auth.data.SqlNote
import com.example.notex.auth.data.userrepo.UserRepository
import com.example.notex.auth.data.toSqlNote
import com.example.notex.auth.domain.usecase.user.GetLoginInfoUseCase
import com.example.notex.presentation.ui.screen.add.MindMapConnection
import com.example.notex.presentation.ui.screen.add.MindMapNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NoteViewModel(private  val noteDao: NoteDao,private val context: Context, private val userRepo: UserRepository):ViewModel() {
    @SuppressLint("StaticFieldLeak")
   // val context = getApplication<Application>().applicationContext!!
    val allNote=noteDao.getAllNotes()
    val loginInfoUseCase= GetLoginInfoUseCase(userRepo)
    private val _loginInfo = MutableStateFlow<LoginPrefModel?>(null)
    val loginInfo: StateFlow<LoginPrefModel?>  = _loginInfo

    // Mind map state
    private val _mindMapNodes = mutableStateListOf<MindMapNode>()
    val mindMapNodes: SnapshotStateList<MindMapNode> = _mindMapNodes
    
    private val _mindMapConnections = mutableStateListOf<MindMapConnection>()
    val mindMapConnections: SnapshotStateList<MindMapConnection> = _mindMapConnections

     init{
        setLoginInfo()
    }
    fun setLoginInfo() {
        viewModelScope.launch {
            try {
                val info = loginInfoUseCase() // Invoking the use case
                _loginInfo.value = info // Updating StateFlow
            } catch (e: Exception) {
                _loginInfo.value = null // Handle error
            }
        }
    }
    //TODO Get Notes By Type
    fun getNotesByType(type:NoteType):Flow<List<Note>>{
        return noteDao.getNotesByType(type)
     }
 var selectedNote: Note? = null
 var selectedNoteType: NoteType? = null

   suspend fun trySyncNotes(){

       if (loginInfo.value != null) {
           try {


               withContext(Dispatchers.Main) {
                   Toast.makeText(context, "Syncing now ...", Toast.LENGTH_LONG).show()

               }

               val userId = SecureLoginDataStoreServices(context).getUserID()
               val unSyncedNotes = noteDao.getUnsyncedNotes()

               val sqlNotes: List<SqlNote> = unSyncedNotes.map { note ->
                   note.toSqlNote(userId)
               }
               val result = RetrofitInstance(context).api.pushNotes(
                   sqlNotes
               )


               if (result.isSuccessful) {

                   unSyncedNotes.forEach { note ->
                       noteDao.updateNote(note.copy(isSynced = true))
                   }

                   val message = result.body()?.message
                   val notesCount = result.body()?.affectedRows
                   withContext(Dispatchers.Main) {

                       Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                   }
                   if (message != null) {
                       Log.d("success", message)
                       Log.d("success", message)
                       Log.d("success", message)
                       Log.d("success", message)
                   }
               } else {
                   val errorBody = result.errorBody()?.string() ?: "Unknown error occurred"
                   val errorMessage = try {
                       JSONObject(errorBody).getString("msg")
                   } catch (e: Exception) {
                       "Unknown error occurred"
                   }
                   withContext(Dispatchers.Main) {

                       Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                   }
                   Log.e("success", errorMessage)
                   Log.e("success", errorMessage)
                   Log.e("success", errorMessage)
                   Log.e("success", errorMessage)

               }

           } catch (e: Exception) {
               withContext(Dispatchers.Main) {
                   Toast.makeText(
                       context,
                       "Syncing your notes failed, Error: ${e.message}",
                       Toast.LENGTH_LONG
                   ).show()
               }
           }
       }
       else{
           withContext(Dispatchers.Main) {
               Toast.makeText(
                   context,
                   "You are not logged in yet. ",
                   Toast.LENGTH_LONG
               ).show()
           }

       }

    }

    fun addNote(title: String, content: String,color: Int,type: NoteType=NoteType.TASK_MANAGEMENT){
     println("Note Type is : ${type.name}")
      viewModelScope.launch {
          noteDao.insertNote(
              Note(
                  title = title,
                  content = content,
                  color = color,
                  type = type
              ).copy(isSynced = false)


          )
          trySyncNotes()
      }

    }

    fun addTaskNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteToSave = note.copy(isSynced = false)
            noteDao.insertNote(noteToSave)
            
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Task list saved to database", Toast.LENGTH_SHORT).show()
                trySyncNotes()
            }
        }
    }

    fun addTextNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteToSave = note.copy(isSynced = false)
            noteDao.insertNote(noteToSave)
            
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Text note saved to database", Toast.LENGTH_SHORT).show()
                trySyncNotes()
            }
        }
    }

    fun addCodeNote(title: String, content: String,color: Int,type: NoteType=NoteType.CODE,codeBlocks:List<CodeBlock>){
     println("Note Type is : ${type.name}")
      viewModelScope.launch(Dispatchers.IO) {
          noteDao.insertNote(

              Note(
                  title = title,
                  content = content,
                  color = color,
                  type = type,
                  isSynced = false,
                  codeBlocks = codeBlocks
              )
          )

              trySyncNotes()

      }

    }
   fun updateNote(id: Int,title: String, content: String,color: Int,type: NoteType=NoteType.TASK_MANAGEMENT){
     println("Note Type is : ${type.name} ${id}")

      viewModelScope.launch {
          noteDao.updateNote(
              Note(
                  id =id ,
                  title = title,
                  content = content,
                  color = color,
                  type = type
              ).copy(isSynced = false)
          )
          trySyncNotes()

      }

    }

    fun deleteNote(note: Note?) {
        viewModelScope.launch {
            if (note != null) {
                noteDao.deleteNote(note)
            }
        }
    }

    fun updateCodeNote(note: Note?) {
        viewModelScope.launch {
            if (note != null) {
                noteDao.updateCodeNote(note)
            }
        }
    }
    
    fun addMindMapNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteToSave = note.copy(isSynced = false)
            noteDao.insertNote(noteToSave)
            
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Mind map saved to database", Toast.LENGTH_SHORT).show()
                trySyncNotes()
            }
        }
    }
    
    // Mind map node management functions
    
    fun initializeMindMapNodes(nodes: List<MindMapNode>) {
        _mindMapNodes.clear()
        _mindMapNodes.addAll(nodes)
    }
    
    fun initializeMindMapConnections(connections: List<MindMapConnection>) {
        _mindMapConnections.clear()
        _mindMapConnections.addAll(connections)
    }
    
    fun updateNodePosition(nodeId: Int, position: Offset) {
        val index = _mindMapNodes.indexOfFirst { it.id == nodeId }
        if (index != -1) {
            val node = _mindMapNodes[index]
            val updatedNode = node.copy(position = position)
            _mindMapNodes.removeAt(index)
            _mindMapNodes.add(index, updatedNode)
            Log.d("MindMap", "Node $nodeId position updated to: $position")
        }
    }
    
    fun addNode(node: MindMapNode) {
        _mindMapNodes.add(node)
    }
    
    fun removeNode(nodeId: Int) {
        _mindMapNodes.removeAll { it.id == nodeId }
        // Also remove any connections involving this node
        _mindMapConnections.removeAll { it.fromNodeId == nodeId || it.toNodeId == nodeId }
    }
    
    fun addConnection(connection: MindMapConnection) {
        _mindMapConnections.add(connection)
    }
    
    fun updateNodeText(nodeId: Int, text: String) {
        val index = _mindMapNodes.indexOfFirst { it.id == nodeId }
        if (index != -1) {
            val node = _mindMapNodes[index]
            _mindMapNodes[index] = node.copy(text = text)
        }
    }
}

class ViewModelFactory(private  val database: NoteDatabase, private  val context: Context, private val userRepo: UserRepository,) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(database.noteDao(),context,userRepo) as T
    }
}