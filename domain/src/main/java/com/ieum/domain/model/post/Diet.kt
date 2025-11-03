package com.ieum.domain.model.post

data class Diet(
    val amountEaten: AmountEaten,
    val mealContent: String?,
) {
    companion object {
        val DEFAULT = Diet(
            amountEaten = AmountEaten.BARELY,
            mealContent = null,
        )
    }
}
