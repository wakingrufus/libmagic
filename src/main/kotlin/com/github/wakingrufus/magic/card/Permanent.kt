package com.github.wakingrufus.magic.card

import com.github.wakingrufus.magic.ActivatedAbility
import com.github.wakingrufus.magic.ActivatedAbilityBuilder
import com.github.wakingrufus.magic.ManaCost

class Permanent(val image: String,
                val activatedAbilities: List<ActivatedAbility>,
                val creatureAttributes: CreatureAttributes? = null)

class PermanentBuilder {
    var creatureBuilder = CreatureBuilder()

    private val activatedAbilities: MutableList<ActivatedAbilityBuilder> = mutableListOf()
    fun activatedAbility(cost: List<ManaCost> = emptyList(), ability: Effect) {
        activatedAbilities.add(ActivatedAbilityBuilder(cost, ability))
    }

    fun creature(creatureBuilder: CreatureBuilder.() -> Unit) {
        this.creatureBuilder.apply(creatureBuilder)
    }
    fun creature(creature: CreatureBuilder) {
        this.creatureBuilder = creature
    }
    fun build(image: String): Permanent {
        return Permanent(image = image,
                activatedAbilities = activatedAbilities.map { it.build(image) },
                creatureAttributes = creatureBuilder.build())
    }
}

data class CreatureAttributes(
        val basePower: Int = 0,
        val baseToughness: Int = 0
)

class CreatureBuilder {
    var power: Int = 0
    var toughness: Int = 0

    fun build(): CreatureAttributes {
        return CreatureAttributes(basePower = power, baseToughness = toughness)
    }
}