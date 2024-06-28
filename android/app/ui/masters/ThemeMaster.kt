package ren.marinay.epictodolist.ui.masters

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.logic.Core.Companion.with
import ren.marinay.epictodolist.storage.products.ThemeStorage
import ren.marinay.epictodolist.ui.MainActivity

class ThemeMaster(private val activity: AppCompatActivity) {

    fun attachTheme() {
        var t = theme
        if (isTryOn) t = tryThemeId
        when (t) {
            ThemeStorage.PEACH_THEME -> activity.setTheme(R.style.PeachTheme)
            ThemeStorage.IRON_THEME -> activity.setTheme(R.style.IronTheme)
            ThemeStorage.WOODY_THEME -> activity.setTheme(R.style.WoodyTheme)
            ThemeStorage.NIGHT_THEME -> activity.setTheme(R.style.NightTheme)
            ThemeStorage.PINK_THEME -> activity.setTheme(R.style.PinkTheme)
            else -> activity.setTheme(R.style.DefaultTheme)
        }
    }

    fun setThemeAndRestart(theme: Int) {
        activity.getSharedPreferences(KEY_THEME, Context.MODE_PRIVATE).edit { putInt(KEY_THEME, theme) }
        with(activity) { core -> core.taskListWidgetLogic.update() }
        isTryOn = false
        MainActivity.ui!!.restart()
    }

    val theme: Int
        get() = getTheme(activity)

    fun tryTheme(theme: Int) {
        isTryOn = true
        tryThemeId = theme
        MainActivity.ui!!.restart()
    }

    companion object {
        const val KEY_THEME = "theme"
        var tryThemeId = ThemeStorage.DEFAULT_THEME
        var isTryOn = false

        fun getTheme(context: Context) = context
            .getSharedPreferences(KEY_THEME, Context.MODE_PRIVATE)
            .getInt(KEY_THEME, ThemeStorage.DEFAULT_THEME)
    }
}