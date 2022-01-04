package com.loopin.ipos.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.loopin.ipos.R
import com.loopin.ipos.Utils.Internet
import com.loopin.ipos.Utils.IposConst
import java.util.ArrayList


class SplashActivity : AppCompatActivity() {

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val iPosSplash = findViewById<ImageView>(R.id.iPosSplash)

        val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.repeat)

        iPosSplash.startAnimation(animation)
        IposConst.sharedPreferences = getSharedPreferences(IposConst.sp_name, Context.MODE_PRIVATE)
        IposConst.editor = IposConst.sharedPreferences.edit()
        storagePermission()
    }

    private fun goAhead() {

        if (!Internet.isNetAvailable(this)){
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Internet Not Found!")
            dialog.setMessage("\nPlease check your internet connection?")
            dialog.setIcon(R.drawable.ic_no_internet)
            dialog.setPositiveButton(" OK "){dialogInterface , which ->
                finish()
            }

            val alertDialog: AlertDialog = dialog.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                if (IposConst.sharedPreferences.contains(IposConst.Keys.VEND_ID)){
                    //val homeIntent = Intent(this, HomeActivity::class.java)
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }else {
                    val loginIntent = Intent(this, LoginActivity::class.java)
                    startActivity(loginIntent)
                    finish()
                }
            }, 3000)
        }
    }

    private fun storagePermission(): Boolean {

        val readExternal = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeExternal = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val permList = ArrayList<String>()

        if (readExternal != PackageManager.PERMISSION_GRANTED) {
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (writeExternal != PackageManager.PERMISSION_GRANTED) {
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this@SplashActivity, permList.toTypedArray(), 111)
            return false
        } else {
            goAhead()
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111) {
            run {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goAhead()
                } else {
                    goAhead()
                }
            }
        }
    }
}
