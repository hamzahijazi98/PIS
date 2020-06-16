package com.uni.pis.homefrags

import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.uni.pis.R
import kotlinx.android.synthetic.main.activity_help.*


class HelpActivity : AppCompatActivity() {
    private var mediaController: android.widget.MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val videoPath = "android.resource://$packageName/" + R.raw.helpvideo1
        val uri = Uri.parse(videoPath)
        HelpVideoView.setVideoURI(uri)
        mediaController = android.widget.MediaController(this)
        HelpVideoView.setMediaController(mediaController)
        mediaController!!.setAnchorView(HelpVideoView)




    }
}
