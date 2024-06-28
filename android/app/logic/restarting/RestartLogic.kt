package ren.marinay.epictodolist.logic.restarting

import android.app.PendingIntent
import android.content.Intent
import ren.marinay.epictodolist.App
import ren.marinay.epictodolist.logic.Core
import ren.marinay.epictodolist.logic.Logic
import ren.marinay.epictodolist.logic.tasks.repeatable.RepeatableTasksScheduler
import ren.marinay.epictodolist.logic.challenges.ChallengeChecker
import ren.marinay.epictodolist.logic.notifications.NotificationSender

class RestartLogic(core: Core) : Logic(core) {

    init {
        ready()
    }

    override fun postInit() {}

    override fun attachRef() {}

    fun checkAll() {
        App.runOnBackgroundThread {
            checkNotifications()
            checkRepeatableTasks()
            checkActiveChallenges()
        }
    }

    private fun checkNotifications() {
        for (notification in core.notificationsLogic.getNotifications()) {
            if (!isAlarmExists(notification.id, NotificationSender::class.java)) {
                core.notificationsLogic.installNotification(notification)
            }
        }
    }

    private fun checkRepeatableTasks() {
        for (task in core.repeatableTasksLogic.repeatableTasks) {
            if (!isAlarmExists(task.id, RepeatableTasksScheduler::class.java)) {
                core.repeatableTasksLogic.installRepeatableTask(task)
            }
        }
    }

    private fun checkActiveChallenges() {
        for (challenge in core.challengesLogic.activeChallenges) {
            if (!isAlarmExists(challenge.id, ChallengeChecker::class.java)) {
                core.challengesLogic.installChallengeChecker(challenge)
            }
        }
    }

    private fun isAlarmExists(id: Int, cls: Class<*>?) = PendingIntent.getBroadcast(
        context,
        id,
        Intent(context, cls),
        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
    ) != null
}