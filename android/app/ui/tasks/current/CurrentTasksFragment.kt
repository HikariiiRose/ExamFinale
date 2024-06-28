package ren.marinay.epictodolist.ui.tasks.current

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.appwidget.tasks.TaskListWidget.Companion.update
import ren.marinay.epictodolist.database.tasks.current.CurrentTaskModel
import ren.marinay.epictodolist.storage.creator.learning.LearnMessageStorage
import ren.marinay.epictodolist.ui.animations.Animations
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.ui.creator.CreatorLearnDialog
import ren.marinay.epictodolist.ui.drag.DragHelper
import ren.marinay.epictodolist.ui.drag.ModelOrderManager
import ren.marinay.epictodolist.util.ui.Clicks.set

class CurrentTasksFragment(container: ViewGroup) : UIFragment(R.layout.current_tasks_fragment, container) {

    private val orderManager = ModelOrderManager<CurrentTaskModel>(context, "CurrentTasksFragment")
    private lateinit var buttonCreate: ImageView
    private lateinit var rvContainer: FrameLayout
    private lateinit var currentTasksRVFragment: CurrentTasksRVFragment

    override fun findViews() {
        buttonCreate = find(R.id.button_create)
        rvContainer = find(R.id.rv_container)
    }

    override fun start() {
        set(buttonCreate) {
            CurrentTaskMakerDialog.open { task: CurrentTaskModel ->
                MainActivity.core!!.currentTasksLogic.create(task)
                currentTasksRVFragment.addItem(task)
            }
        }
        setTasks()
        CreatorLearnDialog.openIfNotLearned(LearnMessageStorage.CURRENT_TASKS_NAV)
    }

    private fun setTasks() {
        currentTasksRVFragment = CurrentTasksRVFragment(rvContainer)
        currentTasksRVFragment.list = orderManager.sorted(MainActivity.core!!.currentTasksLogic.currentTasks)
        currentTasksRVFragment.replace()
        DragHelper.addDrag(currentTasksRVFragment, orderManager) { update(context) }
    }

    override fun remove() {
        Animations.hideView(container) { super.remove() }
    }

    override fun replace() {
        Animations.hideView(container) { super.replace() }
    }

    override fun add() {
        super.add()
        Animations.showView(container, null)
    }
}