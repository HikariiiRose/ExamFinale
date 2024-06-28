package ren.marinay.epictodolist.ui.tasks.completed

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.tasks.completed.CompletedTaskModel
import ren.marinay.epictodolist.ui.confirming.ConfirmDialog
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.recyclerview.RVViewHolder
import ren.marinay.epictodolist.ui.recyclerview.SwipeRVFragment
import ren.marinay.epictodolist.util.ui.Clicks.set

class CompletedTasksRVFragment(container: ViewGroup) : SwipeRVFragment<CompletedTaskModel, CompletedTasksRVFragment.ViewHolder>(
    R.layout.completed_task_item,
    RecyclerView.VERTICAL,
    container,
    MainActivity.ui!!.dp(80)
) {

    init {
        onEmptyMessage = MainActivity.ui!!.getString(R.string.there_are_no_completed_tasks)
    }

    override fun newViewHolder(v: View) = ViewHolder(v)

    inner class ViewHolder(view: View) : RVViewHolder<CompletedTaskModel>(view) {
        private lateinit var task: CompletedTaskModel
        private var swipeLayout: SwipeLayout = find(R.id.swipe_layout)
        private var rightSide: LinearLayout = find(R.id.right_side)
        private var leftSide: LinearLayout = find(R.id.left_side)
        private var mainLayout: LinearLayout = find(R.id.main_layout)
        private var content: TextView = find(R.id.content)
        private var icon: ImageView = find(R.id.icon)
        private var buttonDelete: ImageView = find(R.id.button_delete)
        private var buttonCopy: ImageView = find(R.id.button_copy)

        override fun setData(m: CompletedTaskModel) {
            this.task = m
            val skill = MainActivity.core!!.skillsLogic.findById(task.skillId)
            content.text = task.content
            icon.setImageResource(skill.iconRes())
            icon.setBackgroundResource(skill.frameRes())
            swipeLayout.showMode = SwipeLayout.ShowMode.PullOut
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, leftSide)
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, rightSide)
            bindSwipeView(this)
        }

        init {
            set(mainLayout) {
                CompletedTaskDialog.open(task)
                closeAllItems()
            }

            set(buttonDelete) {
                ConfirmDialog.open { yes ->
                    if (yes) {
                        MainActivity.core!!.completedTasksLogic.delete(task)
                        deleteItem(adapterPosition)
                    }
                }
                closeAllItems()
            }

            set(buttonCopy) {
                MainActivity.core!!.currentTasksLogic.create(task)
                closeAllItems()
            }
        }
    }
}