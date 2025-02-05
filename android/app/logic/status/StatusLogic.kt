package ren.marinay.epictodolist.logic.status

import ren.marinay.epictodolist.database.status.StatusModel
import ren.marinay.epictodolist.database.status.StatusRepository
import ren.marinay.epictodolist.logic.Core
import ren.marinay.epictodolist.logic.Logic
import ren.marinay.epictodolist.logic.Observer
import ren.marinay.epictodolist.logic.achievements.AchievementsLogic
import ren.marinay.epictodolist.logic.hero.HeroLogic
import ren.marinay.epictodolist.logic.products.ProductsLogic
import ren.marinay.epictodolist.logic.tasks.current.CurrentTasksLogic

class StatusLogic(core: Core) : Logic(core) {

    private val repository by lazy { StatusRepository.getInstance(context) }
    private var startUsing: Long = 0

    override fun postInit() {
        core.currentTasksLogic
            .observer
            .addListener(CurrentTasksLogic.COMPLETE) { task ->
                addEarnedCoins(task.reward().coins)
            }

        core.productsLogic
            .observer
            .addListener(ProductsLogic.UNLOCK_PART) { product ->
                addWastedCoins(product.price.coins)
                addWastedCrystals(product.price.crystals)
            }

        core.achievementsLogic
            .observer
            .addListener(AchievementsLogic.ACHIEVE) { achievement ->
                addEarnedCoins(achievement.reward.coins)
                addEarnedCrystals(achievement.reward.crystals)
            }

        core.heroLogic
            .observer
            .addListener(HeroLogic.CREATE) {
                update(StatusModel())
            }
    }

    override fun attachRef() {
        ready()
    }

    val observer = Observer<StatusModel>()
    val status: StatusModel
        get() = repository.status

    private fun addEarnedCoins(coins: Int) {
        val status = status
        status.earnedCoins += coins
        update(status)
    }

    private fun addWastedCoins(coins: Int) {
        val status = status
        status.wastedCoins += coins
        update(status)
    }

    private fun addEarnedCrystals(crystals: Int) {
        val status = status
        status.earnedCrystals += crystals
        update(status)
    }

    private fun addWastedCrystals(crystals: Int) {
        val status = status
        status.wastedCrystals += crystals
        update(status)
    }

    val nextId: Int
        get() {
            val status = status
            status.idCounter++
            update(status)
            return status.idCounter
        }

    fun update(status: StatusModel): StatusModel {
        repository.save(status)
        observer.notify(UPDATE, status)
        return status
    }

    fun startTimeUsing() {
        startUsing = System.currentTimeMillis()
    }

    fun endTimeUsing() {
        val status = status
        status.timeInApp += System.currentTimeMillis() - startUsing
        update(status)
    }

    fun updateTimeUsing() {
        endTimeUsing()
        startTimeUsing()
    }

    companion object {
        const val UPDATE = 0
    }
}