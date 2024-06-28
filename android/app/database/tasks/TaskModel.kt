package ren.marinay.epictodolist.database.tasks

import ren.marinay.epictodolist.database.Model
import ren.marinay.epictodolist.util.rewards.RewardUtils

open class TaskModel : Model {

    var content: String? = ""
    var difficulty = 0
    var skillId = -1

    constructor(id: Int, content: String, difficulty: Int, skillId: Int) : super(id) {
        this.content = content
        this.difficulty = difficulty
        this.skillId = skillId
    }

    constructor(task: TaskModel) : this(task.id, task.content!!, task.difficulty, task.skillId)
    constructor()

    fun reward() = RewardUtils.getReward(difficulty)

}