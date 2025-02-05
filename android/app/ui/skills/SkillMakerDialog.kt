package ren.marinay.epictodolist.ui.skills

import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.skills.SkillModel
import ren.marinay.epictodolist.storage.creator.learning.LearnMessageStorage
import ren.marinay.epictodolist.ui.spinner.CommonSpinnerAdapter
import ren.marinay.epictodolist.ui.spinner.CommonSpinnerItem
import ren.marinay.epictodolist.ui.creator.CreatorLearnDialog
import ren.marinay.epictodolist.ui.icons.IconDialog
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.MakerDialog
import ren.marinay.epictodolist.util.icons.IconHelper.getFrame
import ren.marinay.epictodolist.util.icons.IconHelper.getIcon
import ren.marinay.epictodolist.util.ui.Clicks.set

class SkillMakerDialog : MakerDialog<SkillModel>(R.layout.skill_maker_dialog) {

    private lateinit var icon: ImageView
    private lateinit var inputTitle: EditText
    private lateinit var spinnerAttributes: Spinner

    override fun findViews() {
        super.findViews()
        inputTitle = find(R.id.input_title)
        icon = find(R.id.icon)
        spinnerAttributes = find(R.id.spinner_attribute)
    }

    override fun start() {
        super.start()
        spinnerAttributes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                model!!.attribute = spinnerAttributes.selectedItemPosition
                icon.setBackgroundResource(getFrame(model!!.attribute))
                MainActivity.ui!!.hideKeyboard()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        set(icon) {
            IconDialog.open { iconId ->
                setIcon(iconId)
            }
        }

        initAttributesList()
        inputTitle.setText(model!!.title)
        spinnerAttributes.setSelection(model!!.attribute)
        icon.setImageResource(model!!.iconRes())
        icon.contentDescription = model!!.iconId.toString()
    }

    override fun done(): SkillModel {
        model!!.title = inputTitle.text.toString()
        model!!.iconId = icon.contentDescription.toString().toInt()
        model!!.attribute = spinnerAttributes.selectedItemPosition
        return if (modeCreate) MainActivity.core!!.skillsLogic.create(model!!)
        else MainActivity.core!!.skillsLogic.update(model!!)
    }

    private fun initAttributesList() {
        val list = ArrayList<CommonSpinnerItem>()
        list.add(CommonSpinnerItem(MainActivity.ui!!.getString(R.string.strength), R.drawable.ic_strength))
        list.add(CommonSpinnerItem(MainActivity.ui!!.getString(R.string.intellect), R.drawable.ic_intellect))
        list.add(CommonSpinnerItem(MainActivity.ui!!.getString(R.string.creation), R.drawable.ic_creation))
        list.add(CommonSpinnerItem(MainActivity.ui!!.getString(R.string.health), R.drawable.ic_health))
        spinnerAttributes.adapter = CommonSpinnerAdapter(context, list)
    }

    private fun setIcon(iconId: Int) {
        icon.setImageResource(getIcon(iconId))
        icon.contentDescription = iconId.toString() + ""
    }

    companion object {
        fun open(onDoneListener: OnDoneListener<SkillModel>) {
            val maker = SkillMakerDialog()
            maker.setModeCreate()
            maker.onDoneListener = onDoneListener
            maker.model = SkillModel(-1, "", 0, 0, 0)
            maker.openDialog()
            CreatorLearnDialog.openIfNotLearned(LearnMessageStorage.SKILL_MAKER)
        }

        fun open(skill: SkillModel?, onDoneListener: OnDoneListener<SkillModel>) {
            val maker = SkillMakerDialog()
            maker.setModeEdit()
            maker.onDoneListener = onDoneListener
            maker.model = skill
            maker.openDialog()
        }
    }
}