package ren.marinay.epictodolist.ui.achievements

import android.view.ViewGroup
import android.widget.FrameLayout
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.storage.creator.learning.LearnMessageStorage
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.ui.animations.Animations.hideView
import ren.marinay.epictodolist.ui.animations.Animations.showView
import ren.marinay.epictodolist.ui.creator.CreatorLearnDialog

class AchievementsFragment(container: ViewGroup) : UIFragment(R.layout.achievements_fragment, container) {

    private lateinit var rvContainer: FrameLayout
    private lateinit var achievementsRVFragment: AchievementsRVFragment

    override fun findViews() {
        rvContainer = find(R.id.rv_container)
    }

    override fun start() {
        setAchievements()
        CreatorLearnDialog.openIfNotLearned(LearnMessageStorage.ACHIEVEMENTS_NAV)
    }

    fun setAchievements() {
        achievementsRVFragment = AchievementsRVFragment(rvContainer)
        achievementsRVFragment.list = MainActivity.core!!.achievementsLogic.getAchievements()
        achievementsRVFragment.replace()
    }

    override fun remove() {
        hideView(container) { super.remove() }
    }

    override fun replace() {
        hideView(container) { super.replace() }
    }

    override fun add() {
        super.add()
        showView(container, null)
    }
}