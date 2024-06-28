package ren.marinay.epictodolist.logic.restarting

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ren.marinay.epictodolist.logic.Core

class RestartReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        Core.with(context) { core ->
            core.restartLogic.checkAll()
        }
    }
}