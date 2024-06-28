package ren.marinay.epictodolist.logic.achievements

import ren.marinay.epictodolist.database.achievements.AchievementModel
import ren.marinay.epictodolist.database.achievements.AchievementsRepository
import ren.marinay.epictodolist.logic.Core
import ren.marinay.epictodolist.logic.Logic
import ren.marinay.epictodolist.logic.Observer
import ren.marinay.epictodolist.logic.hero.HeroLogic
import ren.marinay.epictodolist.logic.products.ProductsLogic
import ren.marinay.epictodolist.logic.skills.SkillsLogic
import ren.marinay.epictodolist.logic.status.StatusLogic
import ren.marinay.epictodolist.logic.tasks.current.CurrentTasksLogic
import ren.marinay.epictodolist.ui.achievements.AchievementDialog

class AchievementsLogic(core: Core) : Logic(core) {

    private var achievements = ArrayList<AchievementModel>()
    private val repository by lazy { AchievementsRepository.getInstance(context) }

    val observer = Observer<AchievementModel>()

    override fun postInit() {
        core.currentTasksLogic
            .observer
            .addListener(CurrentTasksLogic.CREATE) { checkAchievements() }
            .addListener(CurrentTasksLogic.COMPLETE) { checkAchievements() }

        core.heroLogic
            .observer
            .addListener(HeroLogic.UPDATE) { checkAchievements() }

        core.skillsLogic
            .observer
            .addListener(SkillsLogic.IMPROVE) { checkAchievements() }

        core.statusLogic
            .observer
            .addListener(StatusLogic.UPDATE) { checkAchievements() }
        core.productsLogic
            .observer
            .addListener(ProductsLogic.UNLOCK) { checkAchievements() }

        observer.addListener(ACHIEVE) { achievement ->
            AchievementDialog.open(achievement)
        }
    }

    override fun attachRef() {
        repository.liveList.observeForever { list -> setAchievements(list) }
    }

    val countAchievedAchievements: Int
        get() {
            var count = 0
            for (achievement in achievements) if (achievement.isAchieved) count++
            return count
        }

    fun getAchievements(): ArrayList<AchievementModel> {
        return ArrayList(achievements)
    }

    private fun setAchievements(list: ArrayList<AchievementModel>) {
        achievements = list
        sort(achievements, false)
        ready()
    }

    private fun achieveAchievement(achievement: AchievementModel) {
        if (!achievement.isAchieved) {
            achievement.isAchieved = true
            update(achievement)
            observer.notify(ACHIEVE, achievement)
        }
    }

    private fun checkAchievements() {
        try {
            for (a in achievements) if (!a.isAchieved && AchievementModel.isTrue(a)) achieveAchievement(a)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun update(achievement: AchievementModel): AchievementModel {
        for (i in achievements.indices) {
            if (achievement.id == achievements[i].id) {
                achievements[i] = achievement
                break
            }
        }
        repository.save(achievement)
        observer.notify(UPDATE, achievement)
        return achievement
    }

    companion object {
        const val UPDATE = 1
        const val ACHIEVE = 3
    }
}