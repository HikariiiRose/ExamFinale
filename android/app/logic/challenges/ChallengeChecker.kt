package ren.marinay.epictodolist.logic.challenges

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ren.marinay.epictodolist.database.Model.Companion.isVoid
import ren.marinay.epictodolist.logic.Core

class ChallengeChecker : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Core.with(context) { core ->
            val challengeId = intent.getIntExtra(CHALLENGE_ID, -1)
            val challenge = core.challengesLogic.findById(challengeId)
            if (!isVoid(challenge) && challenge.isActive) {
                core.challengesLogic.checkChallengeDay(challenge)
            }
        }
    }

    companion object {
        const val CHALLENGE_ID = "challenge_id"
    }
}