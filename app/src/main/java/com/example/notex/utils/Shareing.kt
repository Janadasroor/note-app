package com.example.notex.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.notex.RoomDb.Note
import java.io.File


fun sharePdf(context: Context, fileName: String,note: Note){
    val file=createPdfWithHeaderFooter(context,fileName,note)

    if(file!=null&&file.exists()){
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val intent= Intent(Intent.ACTION_SEND).apply {

            type="application/pdf"
            putExtra(Intent.EXTRA_STREAM,uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent,"Share pdf via"))
    }


}


//region  file provider
//file_paths.xml

//<?xml version="1.0" encoding="utf-8"?>
//<paths>
//<external-files-path name="files" path="." />
//</paths>

//file provider
//<provider
//android:name="androidx.core.content.FileProvider"
//android:authorities="${applicationId}.fileprovider"
//android:exported="false"
//android:grantUriPermissions="true">
//<meta-data
//android:name="android.support.FILE_PROVIDER_PATHS"
//android:resource="@xml/file_paths" />
//</provider>

//endregion