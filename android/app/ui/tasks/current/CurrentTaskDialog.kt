package ren.marinay.epictodolist.ui.tasks.current

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.Model.Companion.isVoid
import ren.marinay.epictodolist.database.notifications.NotificationModel
import ren.marinay.epictodolist.database.tasks.current.CurrentTaskModel
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.tasks.TaskDialog
import ren.marinay.epictodolist.util.time.TimeMaster

class CurrentTaskDialog : TaskDialog<CurrentTaskModel>(R.layout.current_task_dialog) {

    private lateinit var tvNotificationTime: TextView
    private lateinit var notificationTimeLayout: LinearLayout

    private fun setNotifications() {
        val notifications = ArrayList<NotificationModel>()
        for (id in model!!.notificationIds!!) {
            val notification = MainActivity.core!!.notificationsLogic.findById(id)
            if (!isVoid(notification)) {
                notifications.add(notification)
            }
        }
        if (notifications.size == 0) {
            notificationTimeLayout.visibility = View.GONE
        } else {
            notificationTimeLayout.visibility = View.VISIBLE
            val notificationsText = StringBuilder()
            for (i in notifications.indices) {
                val notification = notifications[i]
                val timeMaster = TimeMaster()
                timeMaster.timeInMillis = notification.time
                notificationsText.append(" - ")
                notificationsText.append(timeMaster.timeDateText)
                if (i != notifications.size - 1) {
                    notificationsText.append("\n")
                }
            }
            tvNotificationTime.text = notificationsText.toString()
        }
    }

    override fun findViews() {
        super.findViews()
        tvNotificationTime = find(R.id.notification_time)
        notificationTimeLayout = find(R.id.notification_time_layout)
    }

    override fun start() {
        super.start()
        setNotifications()
    }

    companion object {
        fun open(task: CurrentTaskModel?) {
            val dialog = CurrentTaskDialog()
            dialog.model = task
            dialog.openDialog()
            //CreatorLearnDialog.openIfNotLearned(LearnMessageStorage.CURRENT_TASK_DIALOG);
        }
    }
}