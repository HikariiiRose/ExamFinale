package ren.marinay.epictodolist.ui.skills

import android.widget.FrameLayout
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.ui.challenges.ChallengesFragment
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.ui.achievements.AchievementsFragment
import ren.marinay.epictodolist.ui.navigation.icon.IconNavigationFragment
import ren.marinay.epictodolist.ui.navigation.NavFragment

class SkillsNav : NavFragment(R.layout.skills_nav) {

    private lateinit var fragmentContainer: FrameLayout
    private lateinit var navigationContainer: FrameLayout
    private lateinit var navigation: IconNavigationFragment

    override fun start() {
        navigation = IconNavigationFragment(IconNavigationFragment.TOP, navigationContainer)
        navigation.replace()
        navigation.addButton(SKILLS, R.drawable.nav_skills) { setNav(SKILLS) }
        navigation.addButton(CHALLENGES, R.drawable.nav_challenges) { setNav(CHALLENGES) }
        navigation.addButton(ACHIEVEMENTS, R.drawable.nav_achievements) { setNav(ACHIEVEMENTS) }
        select(SKILLS)
    }

    override fun findViews() {
        fragmentContainer = find(R.id.container)
        navigationContainer = find(R.id.navigation_container)
    }

    private fun setNav(id: Int) {
        var f: UIFragment? = null

        when (id) {
            SKILLS -> {
                f = SkillsFragment(fragmentContainer)
                showTitle(MainActivity.ui!!.getString(R.string.skills))
            }
            CHALLENGES -> {
                f = ChallengesFragment(fragmentContainer)
                showTitle(MainActivity.ui!!.getString(R.string.challenges))
            }
            ACHIEVEMENTS -> {
                f = AchievementsFragment(fragmentContainer)
                showTitle(MainActivity.ui!!.getString(R.string.achievements))
            }
        }

        f?.replace()
    }

    fun select(id: Int) {
        navigation.select(id)
    }

    companion object {
        const val SKILLS = 0
        const val CHALLENGES = 1
        const val ACHIEVEMENTS = 2
    }
}