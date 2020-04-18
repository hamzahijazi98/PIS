package com.uni.pis.Events

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.uni.pis.R
import kotlinx.android.synthetic.main.activity_video_invitation.*
import kotlinx.android.synthetic.main.fragment_video_frame.*
import java.lang.Exception
import javax.xml.transform.ErrorListener

class VideoInvitation : AppCompatActivity(), MultiplePermissionsListener {
    private var GALLERY: Int = 100
    private val VIDEO_CAPTURE = 101
    private var mediaController: MediaController? = null
    lateinit var Frag:Fragment
    lateinit  var videoUri:Uri
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_invitation)

        Frag= videoFrame()
        supportFragmentManager.beginTransaction().add(R.id.videoFramecontine, Frag).commit()

        val permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
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
        btn_linkVideoToEvent.setOnClickListener {
            if(videoUri!=null) {
                val intent = Intent(this, MyEventsUploadViedo::class.java)
                intent.putExtra("video", videoUri)
                startActivity(intent)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
try {
    when (requestCode) {
        GALLERY -> {
            if (data != null) {
                videoUri = data.data!!
                val selectedVideoPath: String = getPath(videoUri)
                mediaController = MediaController(this)
                mediaController?.setAnchorView(viewVideo)
                viewVideo.setMediaController(mediaController)
                Frag.viewVideo.setVideoURI(videoUri)
                Frag.viewVideo.requestFocus()
                Frag.viewVideo.start()
            }
        }
        VIDEO_CAPTURE -> {
            videoUri = data!!.data!!
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
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }
            }


        }
    }
}
catch (e:Exception){

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
        startActivityForResult(intent, VIDEO_CAPTURE)
    }
    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?)
    {
        token!!.continuePermissionRequest()
    }

}
