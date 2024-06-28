package ren.marinay.epictodolist.ui.tasks

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.Model.Companion.isVoid
import ren.marinay.epictodolist.database.tasks.TaskModel
import ren.marinay.epictodolist.ui.Dialog
import ren.marinay.epictodolist.ui.MainActivity

open class TaskDialog<M : TaskModel?>(layoutRes: Int) : Dialog<M>(layoutRes) {

    protected lateinit var tvContent: TextView
    protected lateinit var tvPointsAttribute: TextView
    protected lateinit var tvCoins: TextView
    protected lateinit var tvProgress: TextView
    protected lateinit var ivIcon: ImageView
    protected lateinit var ivAttribute: ImageView

    override fun findViews() {
        super.findViews()
        tvContent = find(R.id.content)
        tvPointsAttribute = find(R.id.points_attribute)
        tvCoins = find(R.id.coins)
        tvProgress = find(R.id.progress)
        ivIcon = find(R.id.icon)
        ivAttribute = find(R.id.icon_attribute)
    }

    @SuppressLint("SetTextI18n")
    override fun start() {
        super.start()
        val skill = MainActivity.core!!.skillsLogic.findById(model!!.skillId)
        var title = skill.title
        if (title == "") title = MainActivity.ui!!.getString(R.string.task)
        tvTitle?.text = title
        tvContent.text = model!!.content
        ivIcon.setImageResource(skill.iconRes())
        ivIcon.setBackgroundResource(skill.frameRes())

        if (isVoid(skill)) {
            tvPointsAttribute.visibility = View.GONE
            ivAttribute.visibility = View.GONE
        } else {
            tvPointsAttribute.visibility = View.VISIBLE
            ivAttribute.visibility = View.VISIBLE
            ivAttribute.setImageResource(skill.iconAttrRes())
            tvPointsAttribute.text = model!!.reward().attributePoints.toString() + ""
        }

        tvCoins.text = model!!.reward().coins.toString() + ""
        tvProgress.text = model!!.reward().progress.toString() + ""
    }
}