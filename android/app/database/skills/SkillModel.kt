package ren.marinay.epictodolist.database.skills

import androidx.room.Entity
import ren.marinay.epictodolist.util.icons.IconHelper
import ren.marinay.epictodolist.util.progress.ImprovableModel

@Entity(tableName = "skills")
class SkillModel : ImprovableModel {

    var title: String? = ""
    var attribute = -1
    var iconId = -1

    constructor(
        id: Int,
        title: String,
        attribute: Int,
        iconId: Int,
        progress: Int
    ) : super(id, progress) {
        this.title = title
        this.attribute = attribute
        this.iconId = iconId
    }

    constructor()

    fun iconRes() = IconHelper.getIcon(iconId)

    fun frameRes() = IconHelper.getFrame(attribute)

    fun iconAttrRes() = IconHelper.getIconAttribute(attribute)

}