package ren.marinay.epictodolist.database.achievements

import androidx.room.Entity
import ren.marinay.epictodolist.database.Model

@Entity(tableName = "incomplete_achievements")
open class IncompleteAchievementModel : Model {


    var isAchieved = false

    constructor()

    constructor(id: Int, isAchieved: Boolean) : super(id) {
        this.isAchieved = isAchieved
    }
}