package com.example.lab4

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.inputmethod.InputMethodManager
import android.widget.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() ,  SurfaceHolder.Callback {
    var mp : MediaPlayer? = MediaPlayer()
    private var oTime = 0
    private var sTime: Int = 0
    private var eTime: Int = 0
    private var fTime: Int = 5000
    private val hdlr: Handler = Handler()
    private var url: String = ""
    private var uri: Uri = Uri.parse(url)

    private lateinit var playBtn:Button
    private lateinit var pauseBtn:Button
    private lateinit var stopBtn:Button
    private lateinit var forwardBtn:Button
    private lateinit var backwardBtn:Button
    private lateinit var seekbar:SeekBar
    private lateinit var curTime:TextView
    private lateinit var fullTime:TextView
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var surfaceView: SurfaceView
    private lateinit var urlData:EditText
    private lateinit var urlBtn:Button

    private lateinit var chAudio:CheckBox
    private lateinit var chVideo:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mp = MediaPlayer()
        uri = Uri.parse("android.resource://$packageName/${R.raw.test}")
        playBtn =  findViewById(R.id.play)
        pauseBtn =  findViewById(R.id.pause)
        stopBtn =  findViewById(R.id.stop)
        forwardBtn =  findViewById(R.id.forward)
        backwardBtn =  findViewById(R.id.backward)
        seekbar = findViewById(R.id.seekBar)
        curTime = findViewById(R.id.startTime)
        fullTime = findViewById(R.id.fullTime)
        chAudio = findViewById(R.id.chAudio)
        chVideo = findViewById(R.id.chVideo)
        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView.holder
        urlData = findViewById(R.id.urlData)
        urlBtn = findViewById(R.id.urlBtn)

        val mp3 = findViewById<Button>(R.id.mp3).setOnClickListener{
            url = "android.resource://$packageName/${R.raw.test1}"
            uri = Uri.parse(url)
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true
            urlData.isFocusableInTouchMode = true
            urlData.isFocusable = false
            mp!!.release(); mp = null
            mp = MediaPlayer()
            helperLoader()}
        val mp4 = findViewById<Button>(R.id.mp4).setOnClickListener{
            url = "android.resource://$packageName/${R.raw.test}"
            uri = Uri.parse(url)
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true
            urlData.isFocusableInTouchMode = true
            urlData.isFocusable = false
            mp!!.release(); mp = null
            mp = MediaPlayer()
            helperLoader()}

        surfaceHolder.addCallback(this)
        pauseBtn.isEnabled = false

        chAudio.setOnClickListener {
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true
            chVideo.isChecked=false
            chAudio.isChecked=true
            mp!!.release(); mp = null
            mp = MediaPlayer()
            helperLoader()}
        chVideo.setOnClickListener { chAudio.isChecked=false; chVideo.isChecked=true
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true
            mp!!.release(); mp = null
            mp = MediaPlayer()
            helperLoader()}
        urlData.setOnClickListener {
            urlData.isFocusableInTouchMode = true
            urlData.isFocusable = true
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
        urlBtn.setOnClickListener { url = urlData.text.toString(); mp!!.release(); mp = null
            urlData.isFocusableInTouchMode = true
            urlData.isFocusable = false
            uri=Uri.parse(url)
            mp = MediaPlayer(); helperLoader()
        }

        playBtn.setOnClickListener {
                if (mp == null) {
                    mp = MediaPlayer()
                    helperLoader()
                }
                mp?.start()
                eTime = mp!!.duration.toLong().toInt()
                sTime = mp!!.currentPosition.toLong().toInt()

                if (oTime == 0) {
                    seekbar.max = eTime
                    oTime = 1
                }

                fullTime.text = formatTime(eTime)
                curTime.text = formatTime(sTime)

                seekbar.progress = sTime
                hdlr.postDelayed(updateSongTime, 100)
                pauseBtn.isEnabled = true
                playBtn.isEnabled = false
        }
        pauseBtn.setOnClickListener { mp!!.pause()
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true}

        stopBtn.setOnClickListener { mp!!.stop()
            mp!!.release()
            mp = null
            mp = MediaPlayer()
            helperLoader()
            sTime = 0
            seekbar.progress = 0
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true
        }
        forwardBtn.setOnClickListener {
            if (sTime + fTime <= eTime) {
                sTime += fTime
                mp!!.seekTo(sTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Can't jump forward",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        backwardBtn.setOnClickListener {
            if (sTime - fTime > 0) {
                sTime -= fTime
                mp!!.seekTo(sTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Can't jump backward",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp!!.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        helperLoader()
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        Toast.makeText(this@MainActivity, "data", Toast.LENGTH_SHORT).show()
        if (mp == null) {
            mp = MediaPlayer()
        }
        if (url.equals("")) {
            url = "android.resource://$packageName/${R.raw.test1}"
        }
        helperLoader()
    }

    fun helperLoader(){
        try{
            oTime = 0
            if (url.substring(0,4).equals("http:") or url.substring(0,4).equals("https"))
                mp!!.setDataSource(url)
            else
                mp!!.setDataSource(this, uri)
            mp!!.setOnCompletionListener { mp ->
                try {
                    mp!!.pause()
                    sTime = 0
                    mp.seekTo(sTime)
                    seekbar.progress = sTime
                    mp.isLooping = false
                    playBtn.isEnabled = true
                    pauseBtn.isEnabled = false
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            mp!!.prepare()
            try{
                if (chVideo.isChecked) {
                    surfaceView.layoutParams.height =
                        windowManager.defaultDisplay.width * mp!!.videoHeight / mp!!.videoWidth
                    surfaceView.layoutParams.width = windowManager.defaultDisplay.width
                    surfaceHolder = surfaceView.holder
                }else{
                    surfaceView.layoutParams.height = 10
                    surfaceView.layoutParams.width = windowManager.defaultDisplay.width
                    surfaceHolder = surfaceView.holder
                }
                mp!!.setDisplay(surfaceHolder)
                Toast.makeText(this@MainActivity, "load data", Toast.LENGTH_SHORT).show()}
            catch(e: Exception){
                Toast.makeText(this@MainActivity, "show without video", Toast.LENGTH_SHORT).show()
                surfaceView.layoutParams.height = 10
                surfaceView.layoutParams.width = windowManager.defaultDisplay.width
                surfaceHolder = surfaceView.holder
                mp!!.setDisplay(surfaceHolder)
            }
        }
        catch(e: Exception){
            e.printStackTrace()
            Toast.makeText(this@MainActivity, "Cant open url", Toast.LENGTH_SHORT).show()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        surfaceHolder.setFormat(format)
        surfaceHolder.setFixedSize(width, height)
        mp?.setDisplay(surfaceHolder)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mp?.setDisplay(null)
    }
    override fun onDestroy() {
        super.onDestroy()
        mp?.stop()
        mp?.release()
        mp = null
    }
    private val updateSongTime: Runnable = object : Runnable {
        override fun run() {
            if (mp == null){sTime = 0}
            else {sTime = mp!!.currentPosition}
            curTime.text = formatTime(sTime)
            seekbar.progress = sTime
            hdlr.postDelayed(this, 100)
        }
    }
    private fun formatTime(sTime :Int): String{
        return String.format(
            "%d:%d", TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(sTime.toLong())
            ))
    }
}