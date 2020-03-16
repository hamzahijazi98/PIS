package com.uni.pis

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_video_invitation.*

class VideoInvitation : AppCompatActivity() {
    private var GALLERY: Int = 100
    private val VIDEO_CAPTURE = 101
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_invitation)

        btn_pick_fromgallery.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY)
        }
        btn_record.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(intent, VIDEO_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY -> {
                if (data != null) {
                    var contentURI: Uri? = data.data
                    val selectedVideoPath: String = getPath(contentURI)
                    viewVideo.setVideoURI(contentURI)
                    viewVideo.requestFocus()
                    viewVideo.start()
                }
            }
            VIDEO_CAPTURE -> {
                val videoUri = data?.data
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(this, "Video saved to:\n" + videoUri, Toast.LENGTH_LONG)
                            .show()
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this, "Failed to record video", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    }

    private fun getPath(contUri: Uri?): String {
        var contentresolver: ContentResolver? = null
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            contUri?.let {
                contentresolver?.query(it, projection, null, null, null)
            }
        if (cursor != null) {
            var column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } else
            return ""

    }
}
