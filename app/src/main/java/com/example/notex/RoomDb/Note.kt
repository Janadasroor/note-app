package com.example.notex.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "note")
@TypeConverters(NoteConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String="",
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int,
    val alarmTime:Long?=null,
    val isSynced: Boolean = false,
    val type:NoteType?=NoteType.CODE,
    val tasks :List<TaskItem>?=null,
    val codeBlocks:List<CodeBlock>?=null,
    val mindMapData: MindMapData? = null,
    val lineNumbers: Boolean = false,
    val themeType: String = "LIGHT" // Default theme is light
)
//region data classes
data class MindMapData(
    val nodes: List<Node>,
    val edges: List<Edge>
)

data class Node(
    val id: Int,
    val text: String
)

data class Edge(
    val from: Int, // ID of starting node
    val to: Int // ID of target node
)

data class TaskItem(
    val description:String,
    val isCompleted:Boolean
)
data class CodeBlock(
    val description: String,
    val code:String,
    val language: String
)
enum class NoteType{
    CODE, TASK_MANAGEMENT, MIND_MAP, TEXT_NOTE
}
//endregion
class NoteConverter{
    private  val gson=Gson()
    @TypeConverter
    fun fromTaskList(tasks:List<TaskItem>?):String?{
     return  gson.toJson(tasks)
    }

    @TypeConverter

    fun toTaskList(data:String?): List<TaskItem>? {
        return data?.let {
            val listType=object : TypeToken<List<TaskItem>>(){}.type
            gson.fromJson(it,listType )
        }
    }
    @TypeConverter
    fun fromCodeBlockList(codeBlocks: List<CodeBlock>?): String? {
        return gson.toJson(codeBlocks)
    }

    @TypeConverter
    fun toCodeBlockList(data:String?):List<CodeBlock>?{
        return data?.let {
            val listType = object : TypeToken<List<CodeBlock>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    @TypeConverter
    fun fromMindMap(mindMapData: MindMapData ?): String? {
        return gson.toJson(mindMapData)
    }

    @TypeConverter
    fun toMindMap(data:String?):MindMapData?{
        return data?.let {
            val type = object : TypeToken<MindMapData>() {}.type
            gson.fromJson(it, type)
        }
    }
//    @TypeConverter
//    fun fromMindMapData(data: String?): String? {
//        return data
//    }
//
//    @TypeConverter
//    fun toMindMapData(data: String?): String? {
//        return data
//    }
}

