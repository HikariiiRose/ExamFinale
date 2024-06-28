package ren.marinay.epictodolist.database.achievements

import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.database.achievements.AchievementModel.CurrentCountGetter
import ren.marinay.epictodolist.util.rewards.Reward

class AchievementModel : IncompleteAchievementModel {

    var title = ""
    var description = ""
    var iconRes = R.drawable.icon_default
    var reward = Reward()
    var needCount = 1
    var currentCountCalculator = CurrentCountGetter { 0 }

    fun interface CurrentCountGetter {
        fun getCurrentCount(): Int
    }

    constructor(
        id: Int,
        title: String,
        description: String,
        iconRes: Int,
        reward: Reward,
        needCount: Int,
        isAchieved: Boolean,
        currentCountCalculator: CurrentCountGetter
    ) : super(id, isAchieved) {
        this.title = title
        this.description = description
        this.iconRes = iconRes
        this.reward = reward
        this.needCount = needCount
        this.currentCountCalculator = currentCountCalculator
    }

    constructor()

    companion object {
        fun isTrue(a: AchievementModel): Boolean {
            return a.currentCountCalculator.getCurrentCount() >= a.needCount
        }
    }
}