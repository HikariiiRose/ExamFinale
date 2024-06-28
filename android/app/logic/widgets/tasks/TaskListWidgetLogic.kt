package ren.marinay.epictodolist.logic.widgets.tasks

import ren.marinay.epictodolist.appwidget.tasks.TaskListWidget
import ren.marinay.epictodolist.logic.Core
import ren.marinay.epictodolist.logic.Logic
import ren.marinay.epictodolist.logic.products.ProductsLogic
import ren.marinay.epictodolist.logic.settings.SettingsLogic
import ren.marinay.epictodolist.logic.tasks.current.CurrentTasksLogic

class TaskListWidgetLogic(core: Core) : Logic(core) {

    override fun postInit() {
        core.currentTasksLogic
            .observer
            .addListener(CurrentTasksLogic.CREATE) { update() }
            .addListener(CurrentTasksLogic.UPDATE) { update() }
            .addListener(CurrentTasksLogic.DELETE) { update() }

        core.productsLogic
            .observer
            .addListener(ProductsLogic.UNLOCK) { update() }

        core.settingsLogic
            .observer
            .addListener(SettingsLogic.UPDATE) { update() }

        ready()
    }

    override fun attachRef() {}

    fun update() {
        TaskListWidget.update(context)
    }
}