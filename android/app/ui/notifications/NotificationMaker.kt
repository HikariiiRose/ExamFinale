package ren.marinay.epictodolist.ui.notifications

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.notifications.NotificationModel
import ren.marinay.epictodolist.database.tasks.current.CurrentTaskModel
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.ui.datetime.DatePickerDialog
import ren.marinay.epictodolist.ui.datetime.TimePickerDialog
import ren.marinay.epictodolist.util.time.TimeMaster
import ren.marinay.epictodolist.util.ui.Clicks.set

class NotificationMaker(container: ViewGroup) : UIFragment(R.layout.notification_maker_fragment, container) {

    private lateinit var tvTime: TextView
    private lateinit var notificationLayout: FrameLayout
    private lateinit var createNotificationLayout: LinearLayout
    private lateinit var editNotificationLayout: LinearLayout
    private lateinit var buttonDeleteNotification: ImageView
    private val timeMaster = TimeMaster()
    private var isChecked = false

    override fun start() {
        set(createNotificationLayout) { openTimeAndDateDialogs() }
        set(buttonDeleteNotification) { uncheckNotification() }
        if (isChecked) {
            checkNotification()
            setTvTime()
        }
    }

    override fun findViews() {
        tvTime = find(R.id.tv_time)
        notificationLayout = find(R.id.notification_layout)
        createNotificationLayout = find(R.id.create_notification_layout)
        editNotificationLayout = find(R.id.edit_notification_layout)
        buttonDeleteNotification = find(R.id.button_delete_notification)
    }

    fun createBy(task: CurrentTaskModel): NotificationModel {
        val notification = NotificationModel()
        if (task.notificationIds!!.size != 0) {
            notification.id = task.notificationIds!![0]
            MainActivity.core!!.notificationsLogic.delete(notification)
            if (isChecked) {
                notification.title = MainActivity.core!!.skillsLogic.findById(task.skillId).title
                notification.content = task.content
                notification.time = timeMaster.timeInMillis
                if (notification.title == "" || notification.title == MainActivity.ui!!.getString(R.string.skill_none)) notification.title = DEFAULT_TITLE
                if (notification.content == "") notification.content = DEFAULT_CONTENT
                return MainActivity.core!!.notificationsLogic.create(notification)
            }
        }
        return NotificationModel()
    }

    fun openTimeAndDateDialogs() {
        TimePickerDialog.open { hour, minute ->
            DatePickerDialog.open { day, month, year ->
                setCalendarDate(day, month, year)
            }
            setCalendarTime(hour, minute)
            checkNotification()
        }
    }

    private fun checkNotification() {
        createNotificationLayout.visibility = View.GONE
        editNotificationLayout.visibility = View.VISIBLE
        setChecked(true)
    }

    private fun uncheckNotification() {
        createNotificationLayout.visibility = View.VISIBLE
        editNotificationLayout.visibility = View.GONE
        setChecked(false)
    }

    private fun setCalendarTime(hour: Int, minute: Int) {
        timeMaster.hour = hour
        timeMaster.minute = minute
        timeMaster.second = 0
        setTvTime()
    }

    private fun setCalendarDate(day: Int, month: Int, year: Int) {
        timeMaster.day = day
        timeMaster.month = month
        timeMaster.year = year
        setTvTime()
    }

    @SuppressLint("SetTextI18n")
    private fun setTvTime() {
        tvTime.text = timeMaster.timeDateText
    }

    private fun setChecked(isChecked: Boolean) {
        this.isChecked = isChecked
    }

    companion object {
        val DEFAULT_TITLE = MainActivity.ui!!.getString(R.string.task)
        val DEFAULT_CONTENT = MainActivity.ui!!.getString(R.string.check_your_task_list)
    }
}