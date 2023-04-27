package com.example.finalyearproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.activities.exerciseVideos
import com.example.finalyearproject.model.Yoga

class YogaAdapter(val context: Context, val mList: ArrayList<Yoga>) : RecyclerView.Adapter<YogaAdapter.ViewHolder>(){

    var isPlaying= false;

    var stopPos=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YogaAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.yogalayout, parent, false)
        return YogaAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val video = mList[position]

        holder.exerciseName.text = video.exerciseName.toString()
        holder.playButton.setOnClickListener {
            playVideo(video.videoPath, holder.exerciseVideo, holder.playButton, holder.pauseButton, holder.thumbNail)
        }
        holder.thumbNail.setImageBitmap(getTheThumbnailOfVideo(video.videoPath, context))
        holder.pauseButton.setOnClickListener {

            isPlaying = true
            holder.exerciseVideo.pause()
            stopPos = holder.exerciseVideo.currentPosition
//            holder.thumbNail.visibility = View.VISIBLE

        }
        holder.restartButton.setOnClickListener {

            Restart(video.videoPath, holder.exerciseVideo, holder.thumbNail)

        }

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var exerciseName: TextView = itemView.findViewById(R.id.nameOfExercise)
        var exerciseVideo: VideoView = itemView.findViewById(R.id.videoView)
        var playButton: Button = itemView.findViewById(R.id.playButton)
        var pauseButton: Button = itemView.findViewById(R.id.pauseButton)
        var restartButton : Button = itemView.findViewById(R.id.restartbutton)
        var thumbNail: ImageView = itemView.findViewById(R.id.thumbNail)

    }




    fun Restart(path: String, videoView: VideoView,thumbNail:ImageView){

        val uri = Uri.parse(path)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.setOnPreparedListener { mp ->
            videoView.start()
            thumbNail.visibility = View.GONE





        }


    }
    fun playVideo(path: String, videoView: VideoView, playButton: Button, pauseButton: Button, thumbNail:ImageView) {
        if(isPlaying == true) {
            videoView.resume()
            videoView.seekTo(stopPos)
            isPlaying = false
        }


        else{

            val uri = Uri.parse(path)
            videoView.setVideoURI(uri)
            videoView.requestFocus()
            videoView.setOnPreparedListener { mp ->
                videoView.start()
                thumbNail.visibility = View.GONE

            }
        }

    }

    @SuppressLint("LongLogTag")
    fun getTheThumbnailOfVideo(path: String, context:Context): Bitmap? {
        try {
            val getData = MediaMetadataRetriever() // provides a unified interface for retrieving frame and meta data from an input media file
            val URILink = path//"android.resource://${resources.getResourcePackageName(videoResId)}/$videoResId"
            getData.setDataSource(context, Uri.parse(URILink)) //defines which file should be used by your MediaPlayer for playing
            val bitmap = getData.getFrameAtTime(0)
            getData.release()
            return bitmap
        } catch (e: RuntimeException) {
            Log.e("Exception is as follows : ", "Thumbnail of video has not been added", e)
            return null
        }
    }


    }
