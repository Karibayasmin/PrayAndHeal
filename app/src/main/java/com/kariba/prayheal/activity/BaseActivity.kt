package com.kariba.prayheal.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kariba.prayheal.R

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        progressDialog = ProgressDialog(this@BaseActivity, R.style.AppCompatAlertDialogStyle)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(getString(R.string.please_wait))
    }

    override fun onDestroy() {
        super.onDestroy()
        if(progressDialog !=null && progressDialog.isShowing) progressDialog.cancel()
    }

    override fun onPause() {
        super.onPause()
        if(progressDialog!=null && progressDialog.isShowing) progressDialog.cancel()

    }
}