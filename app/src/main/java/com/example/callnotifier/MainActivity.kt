package com.example.callnotifier

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext.checkSelfPermission(Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission has not been granted, therefore prompt the user to grant permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG),
                    1);
            }
        }
        //CallReceiver()
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
       telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)

        outlinedTextField.editText?.setText(ServerCommunicator.DEFAULT_IP)
    }

    fun ping(view: View) {
        val inputText = outlinedTextField.editText?.text.toString()
        val communicator = ServerCommunicator()
        GlobalScope.launch {
            try {
                communicator.ping("0123456", inputText)
            } catch (e: Exception) {
                notifyUserOfException(e, view)
            }

        }
    }

    private fun notifyUserOfException(e: Exception, view: View) {
        Snackbar.make(view, "Error ${e.message}", Snackbar.LENGTH_SHORT)
            .show();
    }

    private val listener = object: PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            Log.i("Statechange", "phonenumber" + phoneNumber)
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                Toast.makeText(applicationContext, "ringin number" + phoneNumber, Toast.LENGTH_SHORT).show()
                val communicator = ServerCommunicator()
                GlobalScope.launch {
                    communicator.ping(phoneNumber!!)
                }
            }
        }
    }
}
