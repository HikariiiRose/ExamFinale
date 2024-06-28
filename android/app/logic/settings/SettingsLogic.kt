package ren.marinay.epictodolist.logic.settings

import ren.marinay.epictodolist.database.settings.SettingsModel
import ren.marinay.epictodolist.database.settings.SettingsRepository
import ren.marinay.epictodolist.logic.Core
import ren.marinay.epictodolist.logic.Logic
import ren.marinay.epictodolist.logic.Observer

class SettingsLogic(core: Core) : Logic(core) {

    private val repository by lazy { SettingsRepository.getInstance(context) }
    private var settings: SettingsModel? = null
    val observer = Observer<SettingsModel>()

    override fun postInit() {}

    override fun attachRef() {
        ready()
    }

    fun getSettings() = SettingsModel(repository.settings)

    fun update(settings: SettingsModel): SettingsModel {
        this.settings = settings
        repository.save(settings)
        observer.notify(UPDATE, settings)
        return settings
    }

    companion object {
        const val UPDATE = 0
    }
}