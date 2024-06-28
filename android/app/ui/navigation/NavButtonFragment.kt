package ren.marinay.epictodolist.ui.navigation

import android.view.ViewGroup
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.util.ui.Clicks.set

abstract class NavButtonFragment(layoutRes: Int, container: ViewGroup) : UIFragment(layoutRes, container) {

    var onSelectListener: OnSelectListener? = null

    override fun start() {
        set(view) { select() }
    }

    fun select() {
        onSelectListener?.onSelect()
        activate()
    }

    fun unselect() {
        deactivate()
    }

    protected abstract fun activate()

    protected abstract fun deactivate()

    fun interface OnSelectListener {
        fun onSelect()
    }
}