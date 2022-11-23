package com.kariba.prayheal.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kariba.prayheal.R


open class BaseFragment : Fragment() {

    protected lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = ProgressDialog(context, R.style.AppCompatAlertDialogStyle)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(getString(R.string.please_wait))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
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