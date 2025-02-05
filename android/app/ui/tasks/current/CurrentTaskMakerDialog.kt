package ren.marinay.epictodolist.ui.tasks.current

import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.Model.Companion.isVoid
import ren.marinay.epictodolist.database.notifications.NotificationModel
import ren.marinay.epictodolist.database.products.ProductModel.Companion.isUnlocked
import ren.marinay.epictodolist.database.tasks.current.CurrentTaskModel
import ren.marinay.epictodolist.storage.creator.learning.LearnMessageStorage
import ren.marinay.epictodolist.storage.products.FeaturesStorage
import ren.marinay.epictodolist.ui.creator.CreatorLearnDialog
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.datetime.DatePickerDialog
import ren.marinay.epictodolist.ui.datetime.TimePickerDialog.Companion.open
import ren.marinay.epictodolist.ui.notifications.NotificationsRVFragment
import ren.marinay.epictodolist.ui.products.LockedProductFragment
import ren.marinay.epictodolist.ui.tasks.TaskMakerDialog
import ren.marinay.epictodolist.util.time.TimeMaster
import ren.marinay.epictodolist.util.ui.Clicks.set

class CurrentTaskMakerDialog : TaskMakerDialog<CurrentTaskModel>(R.layout.current_task_maker_dialog) {

    private lateinit var buttonAddNotification: Button
    private lateinit var rvContainer: FrameLayout
    private var notificationsRVFragment: NotificationsRVFragment? = null

    override fun findViews() {
        super.findViews()
        buttonAddNotification = find(R.id.button_add_notification)
        rvContainer = find(R.id.rv_container)
    }

    override fun start() {
        super.start()
        set(buttonAddNotification) {
            val timeMaster = TimeMaster()
            open { hour: Int, minute: Int ->
                timeMaster.hour = hour
                timeMaster.minute = minute
                timeMaster.second = 0
                DatePickerDialog.open { day, month, year ->
                    timeMaster.day = day
                    timeMaster.month = month
                    timeMaster.year = year
                    val notification = NotificationModel()
                    notification.time = timeMaster.timeInMillis
                    notificationsRVFragment?.addItem(notification)
                }
            }
        }
        if (MainActivity.core!!.productsLogic.isNotificationsUnlocked) {
            setNotifications()
        } else {
            buttonAddNotification.visibility = View.GONE
            LockedProductFragment.open(
                MainActivity.core!!.productsLogic.getFeature(FeaturesStorage.NOTIFICATIONS_ID),
                rvContainer
            ) { product ->
                if (isUnlocked(product)) {
                    buttonAddNotification.visibility = View.VISIBLE
                    setNotifications()
                }
            }
        }
    }

    private fun setNotifications() {
        notificationsRVFragment = NotificationsRVFragment(rvContainer)
        val list = ArrayList<NotificationModel>()
        for (id in model!!.notificationIds!!) {
            val notification = MainActivity.core!!.notificationsLogic.findById(id)
            if (!isVoid(notification)) {
                list.add(notification)
            }
        }
        notificationsRVFragment?.list = list
        notificationsRVFragment?.replace()
    }

    override fun done(): CurrentTaskModel {
        super.done()
        for (id in model!!.notificationIds!!) {
            MainActivity.core!!.notificationsLogic.delete(MainActivity.core!!.notificationsLogic.findById(id))
        }
        model!!.notificationIds = ArrayList()
        for (notification in notificationsRVFragment?.list ?: emptyList()) {
            notification.title = MainActivity.core!!.skillsLogic.findById(model!!.skillId).title
            notification.content = model!!.content

            if (notification.title.isNullOrEmpty() || notification.title == MainActivity.ui!!.getString(R.string.skill_none)) {
                notification.title = DEFAULT_TITLE
            }

            if (notification.content.isNullOrEmpty()) {
                notification.content = DEFAULT_CONTENT
            }

            model!!.notificationIds!!.add(MainActivity.core!!.notificationsLogic.create(notification).id)
        }
        return model!!
    }

    fun hideNotifications() {
        rvContainer.visibility = View.GONE
        buttonAddNotification.visibility = View.GONE
    }

    companion object {
        val DEFAULT_TITLE = MainActivity.ui!!.getString(R.string.task)
        val DEFAULT_CONTENT = MainActivity.ui!!.getString(R.string.check_your_task_list)

        
        fun open(onDoneListener: OnDoneListener<CurrentTaskModel>): CurrentTaskMakerDialog {
            val maker = CurrentTaskMakerDialog()
            maker.setModeCreate()
            maker.onDoneListener = onDoneListener
            maker.model = CurrentTaskModel()
            maker.openDialog()
            CreatorLearnDialog.openIfNotLearned(LearnMessageStorage.CURRENT_TASK_MAKER)
            return maker
        }

        
        fun open(task: CurrentTaskModel?, onDoneListener: OnDoneListener<CurrentTaskModel>): CurrentTaskMakerDialog {
            val maker = CurrentTaskMakerDialog()
            maker.setModeEdit()
            maker.onDoneListener = onDoneListener
            maker.model = task
            maker.openDialog()
            return maker
        }
    }
}