package ren.marinay.epictodolist.ui.skills

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.skills.SkillModel
import ren.marinay.epictodolist.storage.creator.learning.LearnMessageStorage
import ren.marinay.epictodolist.ui.animations.Animations
import ren.marinay.epictodolist.ui.creator.CreatorLearnDialog
import ren.marinay.epictodolist.ui.drag.DragHelper
import ren.marinay.epictodolist.ui.drag.ModelOrderManager
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.util.ui.Clicks.set

class SkillsFragment(container: ViewGroup) : UIFragment(R.layout.skills_fragment, container) {

    private val orderManager = ModelOrderManager<SkillModel>(context, "SkillsFragment")
    private lateinit var buttonCreate: ImageView
    private lateinit var rvContainer: FrameLayout
    private lateinit var skillsRVFragment: SkillsRVFragment

    override fun findViews() {
        buttonCreate = find(R.id.button_create)
        rvContainer = find(R.id.rv_container)
    }

    override fun start() {
        set(buttonCreate) {
            SkillMakerDialog.open {
                skillsRVFragment.addItem(it)
            }
        }
        setSkills()
        CreatorLearnDialog.openIfNotLearned(LearnMessageStorage.SKILLS_NAV)
    }

    fun setSkills() {
        skillsRVFragment = SkillsRVFragment(rvContainer)
        skillsRVFragment.list = orderManager.sorted(MainActivity.core!!.skillsLogic.getSkills())
        skillsRVFragment.replace()
        DragHelper.addDrag(skillsRVFragment, orderManager)
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