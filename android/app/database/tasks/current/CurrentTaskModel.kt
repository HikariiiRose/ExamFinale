package ren.marinay.epictodolist.database.tasks.current

import androidx.room.Entity
import ren.marinay.epictodolist.database.tasks.TaskModel
import ren.marinay.epictodolist.database.tasks.completed.CompletedTaskModel
import ren.marinay.epictodolist.database.tasks.repeatable.RepeatableTaskModel

@Entity(tableName = "current_tasks")
class CurrentTaskModel : TaskModel {

    var notificationIds: ArrayList<Int>? = ArrayList()

    constructor(
        id: Int,
        content: String,
        difficulty: Int,
        skillId: Int,
        notificationIds: ArrayList<Int>
    ) : super(id, content, difficulty, skillId) {
        this.notificationIds = notificationIds
    }

    constructor(task: RepeatableTaskModel) : super(task)

    constructor(task: CompletedTaskModel) : super(task)

    constructor()
}