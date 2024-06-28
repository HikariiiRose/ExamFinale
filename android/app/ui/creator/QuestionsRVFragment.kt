package ren.marinay.epictodolist.ui.creator

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.storage.creator.asking.AskMessage
import ren.marinay.epictodolist.storage.creator.asking.AskMessageStorage
import ren.marinay.epictodolist.ui.recyclerview.RVFragment
import ren.marinay.epictodolist.ui.recyclerview.RVViewHolder
import ren.marinay.epictodolist.util.ui.Clicks.set

class QuestionsRVFragment(
    container: ViewGroup
) : RVFragment<AskMessage, QuestionsRVFragment.ViewHolder>(
    R.layout.question_item,
    RecyclerView.VERTICAL,
    container
) {

    var listener: Listener? = null

    init {
        list = AskMessageStorage.list
    }

    override fun newViewHolder(v: View) = ViewHolder(v)

    inner class ViewHolder(v: View) : RVViewHolder<AskMessage>(v) {
        lateinit var message: AskMessage
        var question: TextView = find(R.id.question)

        override fun setData(m: AskMessage) {
            message = m
            question.text = message.question
        }

        init {
            set(v) {
                listener?.onClick(message)
            }
        }
    }

    fun interface Listener {
        fun onClick(message: AskMessage)
    }
}
