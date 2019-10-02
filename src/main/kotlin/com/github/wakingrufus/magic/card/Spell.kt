package com.github.wakingrufus.magic.card

import com.github.wakingrufus.magic.ActivatedAbilityBuilder
import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.StandardMana
import com.github.wakingrufus.magic.state.StackEffect
import com.github.wakingrufus.magic.state.StateCard

class Spell(val image: String, val effects: List<Effect>) {
    fun cast(controller: Player, stateCard: StateCard): List<StackEffect> {
        return effects.map { StackEffect(controller = controller, stateCard = stateCard, effect = it) }
    }
}

@MagicDsl
class SpellBuilder {
    private var landSpell: LandSpellBuilder? = null
    private var creatureSpellBuilder: CreatureSpellBuilder? = null
    private val effects: MutableList<Effect> = mutableListOf()
    fun effect(effect: Effect) {
        this.effects.add(effect)
    }

    fun land(builder: LandSpellBuilder.() -> Unit) {
        landSpell = LandSpellBuilder().apply(builder)
    }

    fun creature(builder: CreatureSpellBuilder.() -> Unit) {
        creatureSpellBuilder = CreatureSpellBuilder().apply(builder)
    }

    fun build(image: String): Spell {
        val spellEffects = (effects + landSpell?.build(image) + creatureSpellBuilder?.build(image)).filterNotNull()
        return Spell(image = image, effects = spellEffects)
    }
}

class CreatureSpellBuilder {
    val creatureBuilder = CreatureBuilder()

    fun creature(builder: CreatureBuilder.() -> Unit) {
        creatureBuilder.apply(builder)
    }

    fun build(image: String): Effect {
        return putIntoPlay(PermanentBuilder().apply { creature(creatureBuilder) }.build(image))
    }
}

class LandSpellBuilder {
    private val manaAbilities: MutableList<List<StandardMana>> = mutableListOf()
    private val permanentBuilder: PermanentBuilder = PermanentBuilder()
    fun manaAbility(mana: List<StandardMana>) {
        manaAbilities.add(mana)
    }

    fun build(image: String): Effect {
        return putIntoPlay(permanentBuilder.apply {
            activatedAbility(){
                addMana()
            }
        }.build(image))
    }
}