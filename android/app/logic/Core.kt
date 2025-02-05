package ren.marinay.epictodolist.logic

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import ren.marinay.epictodolist.logic.achievements.AchievementsLogic
import ren.marinay.epictodolist.logic.appstate.AppStateLogic
import ren.marinay.epictodolist.logic.challenges.ChallengesLogic
import ren.marinay.epictodolist.logic.creator.CreatorLogic
import ren.marinay.epictodolist.logic.dataexport.DataExportLogic
import ren.marinay.epictodolist.logic.dataimport.DataImportLogic
import ren.marinay.epictodolist.logic.hero.HeroLogic
import ren.marinay.epictodolist.logic.notifications.NotificationsLogic
import ren.marinay.epictodolist.logic.products.ProductsLogic
import ren.marinay.epictodolist.logic.restarting.RestartLogic
import ren.marinay.epictodolist.logic.settings.SettingsLogic
import ren.marinay.epictodolist.logic.skills.SkillsLogic
import ren.marinay.epictodolist.logic.sound.SoundLogic
import ren.marinay.epictodolist.logic.status.StatusLogic
import ren.marinay.epictodolist.logic.tasks.completed.CompletedTasksLogic
import ren.marinay.epictodolist.logic.tasks.current.CurrentTasksLogic
import ren.marinay.epictodolist.logic.tasks.repeatable.RepeatableTasksLogic
import ren.marinay.epictodolist.logic.widgets.tasks.TaskListWidgetLogic
import ren.marinay.epictodolist.ui.MainActivity

@SuppressLint("StaticFieldLeak")
class Core private constructor(applicationContext: Context) {

    val applicationContext: Context = applicationContext.applicationContext
    private val onReadyListeners = ArrayList<OnReadyListener>()
    private val logics = ArrayList<Logic>()

    lateinit var statusLogic: StatusLogic
    lateinit var skillsLogic: SkillsLogic
    lateinit var notificationsLogic: NotificationsLogic
    lateinit var heroLogic: HeroLogic
    lateinit var currentTasksLogic: CurrentTasksLogic
    lateinit var repeatableTasksLogic: RepeatableTasksLogic
    lateinit var completedTasksLogic: CompletedTasksLogic
    lateinit var achievementsLogic: AchievementsLogic
    lateinit var productsLogic: ProductsLogic
    lateinit var taskListWidgetLogic: TaskListWidgetLogic
    lateinit var settingsLogic: SettingsLogic
    lateinit var soundLogic: SoundLogic
    lateinit var restartLogic: RestartLogic
    lateinit var creatorLogic: CreatorLogic
    lateinit var challengesLogic: ChallengesLogic
    lateinit var dataExportLogic: DataExportLogic
    lateinit var dataImportLogic: DataImportLogic
    lateinit var appStateLogic: AppStateLogic

    init {
        Log.d("Core", "Core::init")
        initLogic()
        postInitLogic()
        setLogicOnReadyListeners()
        loadData()
    }

    @Synchronized
    fun notifyOnReadyListeners() {
        for (l in onReadyListeners) {
            l.onReady(core!!)
        }
        onReadyListeners.clear()
    }

    private fun addOnReadyListener(onReadyListener: OnReadyListener?) {
        if (onReadyListener != null) {
            onReadyListeners.add(onReadyListener)
            if (isReady) notifyOnReadyListeners()
        }
    }

    fun interface OnReadyListener {
        fun onReady(core: Core)
    }

    private fun initLogic() {
        statusLogic = StatusLogic(this)
        skillsLogic = SkillsLogic(this)
        notificationsLogic = NotificationsLogic(this)
        heroLogic = HeroLogic(this)
        currentTasksLogic = CurrentTasksLogic(this)
        repeatableTasksLogic = RepeatableTasksLogic(this)
        completedTasksLogic = CompletedTasksLogic(this)
        achievementsLogic = AchievementsLogic(this)
        productsLogic = ProductsLogic(this)
        taskListWidgetLogic = TaskListWidgetLogic(this)
        settingsLogic = SettingsLogic(this)
        soundLogic = SoundLogic(this)
        restartLogic = RestartLogic(this)
        creatorLogic = CreatorLogic(this)
        challengesLogic = ChallengesLogic(this)
        dataExportLogic = DataExportLogic(this)
        dataImportLogic = DataImportLogic(this)
        appStateLogic = AppStateLogic(this)
        logics.add(statusLogic)
        logics.add(skillsLogic)
        logics.add(notificationsLogic)
        logics.add(heroLogic)
        logics.add(currentTasksLogic)
        logics.add(repeatableTasksLogic)
        logics.add(completedTasksLogic)
        logics.add(achievementsLogic)
        logics.add(productsLogic)
        logics.add(taskListWidgetLogic)
        logics.add(settingsLogic)
        logics.add(soundLogic)
        logics.add(restartLogic)
        logics.add(creatorLogic)
        logics.add(challengesLogic)
        logics.add(dataExportLogic)
        logics.add(dataImportLogic)
        logics.add(appStateLogic)
    }

    private fun postInitLogic() {
        for (l in logics) l.postInit()
    }

    private fun setLogicOnReadyListeners() {
        for (l in logics) {
            l.setOnReadyListener {
                if (isReady) {
                    notifyOnReadyListeners()
                }
            }
        }
    }

    private fun loadData() {
        attachReferences()
    }

    private val isReady: Boolean
        get() {
            var isReady = true
            for (l in logics) {
                if (!l.isReady) {
                    isReady = false
                    break
                }
            }
            return isReady
        }

    private fun attachReferences() {
        for (l in logics) l.attachRef()
    }

    companion object {
        private var core: Core? = null

        /**
         * DUNGEROS
         * KOSTIL FOR ACHIEVEMENTS AND PRODUCTS STORAGE
         */
        
        val context: Context?
            get() {
                var c: Context? = MainActivity.activity
                if (c == null && core != null) c = core!!.applicationContext
                return c
            }

        fun with(context: Context, onReadyListener: OnReadyListener? = null): Core {
            if (core == null || core!!.applicationContext !== context.applicationContext) core = Core(context)
            core!!.addOnReadyListener(onReadyListener)
            return core!!
        }
        
        fun log(o: Any?) {
            if (o != null) {
                Log.d("EpicCore", o.toString())
            } else {
                Log.d("EpicCore", "null")
            }
        }
    }
}