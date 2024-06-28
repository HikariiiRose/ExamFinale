package ren.marinay.epictodolist.logic.appstate

import ren.marinay.epictodolist.database.achievements.IncompleteAchievementModel
import ren.marinay.epictodolist.database.challenges.ChallengeModel
import ren.marinay.epictodolist.database.creator.CreatorModel
import ren.marinay.epictodolist.database.hero.HeroModel
import ren.marinay.epictodolist.database.notifications.NotificationModel
import ren.marinay.epictodolist.database.products.IncompleteProductModel
import ren.marinay.epictodolist.database.settings.SettingsModel
import ren.marinay.epictodolist.database.skills.SkillModel
import ren.marinay.epictodolist.database.status.StatusModel
import ren.marinay.epictodolist.database.tasks.completed.CompletedTaskModel
import ren.marinay.epictodolist.database.tasks.current.CurrentTaskModel
import ren.marinay.epictodolist.database.tasks.repeatable.RepeatableTaskModel

class AppState(
    val epicVersion: Int,
    val achievements: List<IncompleteAchievementModel>,
    val challenges: List<ChallengeModel>,
    val creator: CreatorModel,
    val hero: HeroModel,
    val notifications: List<NotificationModel>,
    val products: List<IncompleteProductModel>,
    val settings: SettingsModel,
    val skills: List<SkillModel>,
    val status: StatusModel,
    val completedTasks: List<CompletedTaskModel>,
    val currentTasks: List<CurrentTaskModel>,
    val repeatableTasks: List<RepeatableTaskModel>,
    val currentTasksOrder: List<Int>,
    val repeatableTasksOrder: List<Int>,
    val challengesOrder: List<Int>,
    val skillsOrder: List<Int>
)