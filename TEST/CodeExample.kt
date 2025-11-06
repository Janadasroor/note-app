package com.example.myapplication.TEST

class CodeExample {
    val codeExample:String="package com.example.myapplication.data\n" +
            "import androidx.room.*\n" +
            "import kotlinx.coroutines.flow.Flow\n" +
            "\n" +
            "\n" +
            "@Dao\n" +
            "interface NoteDao {\n" +
            "    @Query(\"SELECT * FROM note ORDER BY timestamp DESC\")\n" +
            "    fun getAllNotes(): Flow<List<Note>>\n" +
            "\n" +
            "    @Query(\"SELECT  * FROM note WHERE type= :type ORDER BY timestamp DESC\")\n" +
            "    fun getNotesByType(type:NoteType):Flow<List<Note>>\n" +
            "\n" +
            "    @Insert(onConflict = OnConflictStrategy.REPLACE)\n" +
            "    suspend fun insertNote(note: Note)\n" +
            "\n" +
            "    @Delete\n" +
            "    suspend fun deleteNote(note: Note)\n" +
            "\n" +
            "    @Update\n" +
            "    suspend fun updateNote(note: Note)\n" +
            "}\n"
    val description:String="Kotlin is a modern, statically-typed programming language developed by JetBrains, designed to be fully interoperable with Java. It is concise, expressive, and aims to reduce boilerplate code, making development faster and more efficient. Kotlin supports both object-oriented and functional programming paradigms, offering features like null safety, extension functions, and coroutines for asynchronous programming. It is widely used for Android development, server-side applications, and multiplatform projects. Kotlin's seamless integration with Java allows developers to leverage existing Java libraries and frameworks. Its growing popularity stems from its simplicity, safety, and versatility, making it a preferred choice for modern software development."
}