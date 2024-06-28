package ren.marinay.epictodolist.ui.graphs.attributes

import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.ui.MainActivity
import ren.marinay.epictodolist.ui.UIFragment
import ren.marinay.epictodolist.ui.graphs.holo.pie.PieGraph
import ren.marinay.epictodolist.ui.graphs.holo.pie.PieSlice

class AttributeGraphFragment(container: ViewGroup) : UIFragment(R.layout.attribute_graph_fragment, container) {

    private lateinit var graph: PieGraph

    override fun start() {
        val hero = MainActivity.core!!.heroLogic.hero

        if (hero.strength != 0) addSlice(hero.strength.toFloat(), R.color.colorStrength)
        else addSlice(0.3f, R.color.colorStrength)

        if (hero.intellect != 0) addSlice(hero.intellect.toFloat(), R.color.colorIntellect)
        else addSlice(0.3f, R.color.colorIntellect)

        if (hero.creation != 0) addSlice(hero.creation.toFloat(), R.color.colorCreation)
        else addSlice(0.3f, R.color.colorCreation)

        if (hero.health != 0) addSlice(hero.health.toFloat(), R.color.colorHealth)
        else addSlice(0.3f, R.color.colorHealth)

        graph.setInnerCircleRatio(150)
        graph.setPadding(5)
        graph.duration = 2000
        graph.interpolator = AccelerateDecelerateInterpolator()
        graph.animateToGoalValues()
    }

    override fun findViews() {
        graph = find(R.id.pie_graph)
    }

    private fun addSlice(value: Float, color: Int) {
        graph.addSlice(buildSlice(value, color))
    }

    private fun buildSlice(value: Float, color: Int): PieSlice {
        val slice = PieSlice()
        slice.color = MainActivity.ui!!.getColor(color)
        slice.value = 0.1f
        slice.goalValue = value
        return slice
    }
}