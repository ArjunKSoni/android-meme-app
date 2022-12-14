package com.example.memeshare

import android.app.DownloadManager.Request
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.wifi.p2p.WifiP2pManager.ServiceResponseListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

import android.widget.Toast
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currntImgUrl: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadme()
    }

    private fun loadme(){
        val progres=findViewById<ProgressBar>(R.id.progressBar)
        progres.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url ="https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(GET, url,null,
            { response ->
                val name=findViewById<ImageView>(R.id.memeImageView)
                Glide.with(this).load(response.getString("url")).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progres.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progres.visibility=View.GONE
                        return false
                    }
                }).into(name)
                currntImgUrl=response.getString("url")
            },
            { Toast.makeText(this,"error",Toast.LENGTH_LONG).show() })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hi check out this cool meme $currntImgUrl")
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun next(view: View) {
        loadme()
    }
}