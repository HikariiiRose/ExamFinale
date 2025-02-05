package ren.marinay.epictodolist.storage.products

import ren.marinay.epictodolist.database.products.ProductModel
import ren.marinay.epictodolist.logic.products.ProductType

object ProductsStorage {

    operator fun get(id: Int, type: Int) = when (type) {
        ProductType.COSTUME -> CostumesStorage[id]
        ProductType.WEAPON -> WeaponStorage.get(id)
        ProductType.THEME -> ThemeStorage.get(id)
        ProductType.LOCATION -> LocationsStorage[id]
        ProductType.FEATURE -> FeaturesStorage[id]
        else -> ProductModel()
    }

    val list: ArrayList<ProductModel>
        get() {
            val products = ArrayList<ProductModel>()
            products.addAll(CostumesStorage.list)
            products.addAll(WeaponStorage.list)
            products.addAll(ThemeStorage.list)
            products.addAll(LocationsStorage.list)
            products.addAll(FeaturesStorage.list)
            return products
        }
}