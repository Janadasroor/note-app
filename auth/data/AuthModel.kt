package com.example.myapplication.auth.data

import com.example.myapplication.RoomDb.CodeBlock
import com.example.myapplication.RoomDb.MindMapData
import com.example.myapplication.RoomDb.Note
import com.example.myapplication.RoomDb.NoteType
import com.example.myapplication.RoomDb.TaskItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

data class SqlNote(
    val id: Int,
    val userId: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val alarmTime: Long?, // Can be null
    val type: NoteType?,
    val tasks: List<TaskItem>?, // null if not a task note
    val codeBlocks: List<CodeBlock>?, // only for CODE notes
    val mindMapData: MindMapData?, // only for MIND_MAP notes
    val lineNumbers: Boolean = false,
    val themeType: String = "LIGHT" // Default theme is light
)

fun Note.toSqlNote(userId: String):SqlNote{
    return SqlNote(
        id = id,
        userId =userId,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color,
        alarmTime = alarmTime,
        type = type,
        tasks =tasks,
        codeBlocks =codeBlocks,
        mindMapData = mindMapData,
        lineNumbers = lineNumbers,
        themeType = themeType
    )
}






data class LoginReqModel (
    val email:String,val password:String
)

data class RegisterReqModel (
    val email:String,val password:String,val name:String=""
)

data class  RegisterResModel(
    val msg: String
)

data class LoginResModel(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val name: String,
    val email: String,
    val profilePicture: String,
    val id: String,
    val verified: Int
)

data class VerificationRes(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val name: String,
    val email: String,
    val profilePicture: String,
    val id: String,
    val verified: Int
)



data class  ResendCodeRes(
    val msg: String
)
sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}


sealed class  LoginState(){
    data object  Loading:LoginState()
    data class  Error(val msg:String,val isVerified:Int=1):LoginState()
    data class  Success(val msg:String):LoginState()
}
data class MediaPostResponse(
    val data: List<MediaPost>
)

data class MediaPost(
    val data_id: Int,
    val user_id: String,
    val content_type: String,
    val content_url: String,
    val description: String,
    val created_at: String,
    val thumbnail_url: String,
    val media_metadata: MediaMetadata,
    val visibility: String,
    val shares_count: Int,
    val is_story: Int,
    val story_expiry: String?,
    val name: String,
    val profilePicture: String,
    val like_count: Int,
    val comment_count: Int
)

data class MediaMetadata(
    val duration: Int? = null,
    val width: Int? = null,
    val height: Int? = null,
    @Transient
    val empty: String? = null  // For handling the empty string key case
)
data class  AccessTokRes(
    val accessToken:String
)
data class ResponseData(
    val name:String
)
data class RefreshResponse(
    val accessToken: String,
    val name: String,
    val email: String,
    val profilePicture: String
)
data class NotePushRes(
    val message:String ,
    val affectedRows:String
)
interface RegisterApiService {

    @GET("/user/posts") // Replace with your actual endpoint
    suspend fun getProtectedData(@Header("authorization") authToken: String): Response<List<MediaPost>>
     @POST("refresh") // Replace with your actual endpoint
     suspend fun refreshToken(@Body request:Map<String,String>): Response<AccessTokRes>
//    suspend fun fetchProtectedData(token: String) {
//        val apiService = createRetrofitInstance()
//        val response = apiService.getProtectedData("Bearer $token") // Now it is required
//    }
    @POST("login")
    suspend fun  loginUser(@Body request:LoginReqModel):Response<LoginResModel>
    @POST("send/code")
    suspend fun  resendCode(@Body request: Map<String,String> ):Response<ResendCodeRes>
    @POST("register")
    suspend fun registerUser(@Body request: RegisterReqModel): Response<RegisterResModel>

    @GET("auth/verify/{code}")
    suspend fun verifyEmail(@Path("code") code:String):Response<VerificationRes>

    @POST("api/notes")
    suspend fun pushNotes(@Body request: List<SqlNote>):Response<NotePushRes>

    @GET
    suspend fun  getNotes(@Body request: Map<String, String>):Response<Note>
    interface AuthApiService {
        @POST("refresh")
        suspend fun refreshAccessToken(@Body refreshToken: String): Response<RefreshResponse>
    }
}