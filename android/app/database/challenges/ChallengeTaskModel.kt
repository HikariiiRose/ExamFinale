package ren.marinay.epictodolist.database.challenges

import ren.marinay.epictodolist.database.Model
import ren.marinay.epictodolist.database.tasks.current.CurrentTaskModel

class ChallengeTaskModel(
    id: Int,

    var currentTask: CurrentTaskModel,

    var isCompleted: Boolean
) : Model(id)