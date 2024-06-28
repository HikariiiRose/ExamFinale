package ren.marinay.epictodolist.ui.navigation

import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.messages.MessageFragment
import ren.marinay.epictodolist.ui.UIFragment

abstract class NavFragment(layoutRes: Int) : UIFragment(layoutRes, MainActivity.navContainer!!) {

    fun showTitle(title: String) {
        if (MainActivity.core!!.settingsLogic.getSettings().showTitleNav) {
            MessageFragment.show(title, 1)
        }
    }
}