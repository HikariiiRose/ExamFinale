package ren.marinay.epictodolist.ui.tasks.repeatable

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.tasks.repeatable.RepeatableTaskModel
import ren.marinay.epictodolist.storage.creator.learning.LearnMessageStorage
import ren.marinay.epictodolist.storage.products.FeaturesStorage
import ren.marinay.epictodolist.ui.animations.Animations
import ren.marinay.epictodolist.ui.creator.CreatorLearnDialog
import ren.marinay.epictodolist.ui.drag.DragHelper
import ren.marinay.epictodolist.ui.drag.ModelOrderManager
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.products.LockedProductFragment
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.util.ui.Clicks.set

class RepeatableTasksFragment(container: ViewGroup) : UIFragment(R.layout.repeatable_tasks_fragment, container) {

    private val orderManager = ModelOrderManager<RepeatableTaskModel>(context, "RepeatableTasksFragment")
    private lateinit var buttonCreate: ImageView
    private lateinit var unlockContainer: FrameLayout
    private lateinit var rvContainer: FrameLayout
    private lateinit var repeatableTasksRVFragment: RepeatableTasksRVFragment

    override fun findViews() {
        buttonCreate = find(R.id.button_create)
        rvContainer = find(R.id.rv_container)
        unlockContainer = find(R.id.unlock_container)
    }

    override fun start() {
        set(buttonCreate) {
            RepeatableTaskMakerDialog.open {
                repeatableTasksRVFragment.addItem(it)
            }
        }

        if (MainActivity.core!!.productsLogic.isRepeatableTasksUnlocked) {
            setTasks()
        } else {
            buttonCreate.visibility = View.GONE
            LockedProductFragment.open(
                MainActivity.core!!.productsLogic.getFeature(FeaturesStorage.REPEATABLE_TASKS_ID),
                unlockContainer
            ) {
                if (MainActivity.core!!.productsLogic.isRepeatableTasksUnlocked) {
                    setTasks()
                    unlockContainer.visibility = View.GONE
                    buttonCreate.visibility = View.VISIBLE
                }
            }
        }

        CreatorLearnDialog.openIfNotLearned(LearnMessageStorage.REPEATABLE_TASKS_NAV)
    }

    fun setTasks() {
        repeatableTasksRVFragment = RepeatableTasksRVFragment(rvContainer)
        repeatableTasksRVFragment.list = orderManager.sorted(MainActivity.core!!.repeatableTasksLogic.repeatableTasks)
        repeatableTasksRVFragment.replace()
        DragHelper.addDrag(repeatableTasksRVFragment, orderManager)
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