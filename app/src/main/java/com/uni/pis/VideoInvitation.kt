package com.uni.pis

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.PermissionRequest
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_video_invitation.*
import kotlinx.android.synthetic.main.fragment_video_frame.*

class VideoInvitation : AppCompatActivity(), MultiplePermissionsListener {
    private var GALLERY: Int = 100
    private val VIDEO_CAPTURE = 101
    private var mediaController: MediaController? = null
    lateinit var Frag:Fragment
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_invitation)
        Frag= videoFrame ()
        supportFragmentManager.beginTransaction().add(R.id.videoFramecontine, Frag).commit()

        val permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        btn_pick_fromgallery.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY)
        }


        btn_record.setOnClickListener {
            Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(this)
                .check()


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY -> {
                if (data != null) {
                    var contentURI: Uri? = data.data
                    val selectedVideoPath: String = getPath(contentURI)
                    mediaController = MediaController(this)
                    mediaController?.setAnchorView(viewVideo)
                    viewVideo.setMediaController(mediaController)
                    Frag.viewVideo.setVideoURI(contentURI)
                    Frag.viewVideo.requestFocus()
                    Frag.viewVideo.start()
                }
            }



            VIDEO_CAPTURE -> {
                val videoUri = data?.data
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(this, "Video saved to:\n$videoUri", Toast.LENGTH_LONG)
                            .show()
                        mediaController = MediaController(this)
                        mediaController?.setAnchorView(viewVideo)
                        viewVideo.setMediaController(mediaController)
                        Frag.viewVideo.setVideoURI(videoUri)
                        Frag.viewVideo.requestFocus()
                        Frag.viewVideo.start()

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

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, VIDEO_CAPTURE)    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
        token: PermissionToken?
    ) {
    Toast.makeText(this,"Permission Denied ",Toast.LENGTH_LONG).show()
    }
}
