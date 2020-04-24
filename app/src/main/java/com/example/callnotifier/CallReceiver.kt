package com.example.callnotifier

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CallReceiver: IncomingCallReceiver() {
    override fun onIncomingCallReceived(ctx: Context?, number: String?, start: Date?) {
        Log.i("foobar", "phonenumber" + number)
        val communicator = ServerCommunicator()
        GlobalScope.launch {
            communicator.ping(number!!)
        }
    }

    override fun onIncomingCallAnswered(ctx: Context?, number: String?, start: Date?) {
        TODO("Not yet implemented")
    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        TODO("Not yet implemented")
    }

    override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {
        TODO("Not yet implemented")
    }

    override fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        TODO("Not yet implemented")
    }

    override fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        TODO("Not yet implemented")
    }
}