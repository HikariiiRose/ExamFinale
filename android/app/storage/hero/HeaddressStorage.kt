package ren.marinay.epictodolist.storage.hero

import ren.marinay.epictodolist.R
import ren.marinay.epictodolist.storage.products.CostumesStorage

object HeaddressStorage {

    
    operator fun get(gender: Int, id: Int) = when (gender) {
        0 -> when (id) {
            CostumesStorage.WARRIOR_OF_CHAOS -> R.drawable.man_headdress_3
            CostumesStorage.THE_HEALER -> R.drawable.man_headdress_4
            CostumesStorage.HERMIT -> R.drawable.man_headdress_5
            CostumesStorage.ELF -> R.drawable.man_headdress_6
            else -> R.drawable.ic_void
        }
        1 -> when (id) {
            CostumesStorage.WARRIOR_OF_CHAOS -> R.drawable.woman_headdress_3
            CostumesStorage.THE_HEALER -> R.drawable.woman_headdress_4
            CostumesStorage.HERMIT -> R.drawable.woman_headdress_5
            CostumesStorage.ELF -> R.drawable.woman_headdress_6
            else -> R.drawable.ic_void
        }
        else -> R.drawable.ic_void
    }
}