package com.github.wakingrufus.magic

enum class Mana {
    W, U, B, R, G, COLORLESS
}

class ManaPool(val manaMap: Map<Mana, Int> = mapOf()) {

    fun addMana(mana: Mana): ManaPool {
        return ManaPool(manaMap.plus(mana to manaMap.getOrDefault(mana, 0) + 1))
    }

    fun useMana(quantity: Int, mana: Mana): ManaPool? {
        val currentMana = manaMap.getOrDefault(mana, 0)
        return if (currentMana >= quantity) {
            ManaPool(manaMap.plus(mana to manaMap.getOrDefault(mana, 0) - quantity))
        } else {
            null
        }
    }
}


sealed class ManaPaymentMethod
class StandardMana(val mana: Mana) : ManaPaymentMethod()
class AnyMana: ManaPaymentMethod()
class ManaCost(val paymentMethods: List<ManaPaymentMethod>)