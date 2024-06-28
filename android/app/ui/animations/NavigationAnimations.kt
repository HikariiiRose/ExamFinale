package ren.marinay.epictodolist.ui.animations

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import ren.marinay.epictodolist.R

object NavigationAnimations {
    lateinit var slideToDown: Animation
    lateinit var slideToUp: Animation

    
    fun init(context: Context) {
        slideToDown = AnimationUtils.loadAnimation(context, R.anim.slide_to_down)
        slideToUp = AnimationUtils.loadAnimation(context, R.anim.slide_to_up)
    }
}