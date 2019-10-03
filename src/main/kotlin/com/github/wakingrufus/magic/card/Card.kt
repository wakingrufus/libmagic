package com.github.wakingrufus.magic.card

import com.github.wakingrufus.magic.Mana
import com.github.wakingrufus.magic.ManaCost
import com.github.wakingrufus.magic.ManaPaymentMethod

enum class CardType {
    INSTANT, SORCERY, CREATURE, LAND, PLANESWALKER
}

class SuperType(val name: String)

val Legendary = SuperType("Legendary")
val Basic = SuperType("Basic")

inline class SubType(val name: String)

val Goblin = SubType("Goblin")

data class Card(val name: String,
                val types: kotlin.collections.Set<CardType>,
                val superTypes: List<SuperType>,
                val subTypes: List<SubType>,
                val cost: ManaCost,
                val creatureCard: CreatureCard? = null,
                val spell: Spell,
                val image: String) {
    fun convertedManaCost(): Int {
        return cost.paymentMethods.size
    }
}

data class CreatureCard(
        var power: Int = 0,
        var toughness: Int = 0,
        var permanent: Permanent
)

@MagicDsl
class CardBuilder(val name: String) {
    private var cost: ManaCost = ManaCost(emptyList())
    private val types: MutableSet<CardType> = mutableSetOf()
    val spellBuilder: SpellBuilder = SpellBuilder()

    fun spell(builder: SpellBuilder.() -> Unit) {
        spellBuilder.apply(builder)
    }

    fun cost(costs: List<ManaPaymentMethod>) {
        cost = ManaCost(costs)
    }

    fun land(mana: List<Mana>) {
        spellBuilder.apply {
            land {
                manaAbility(mana = mana)
            }
        }
    }

    fun creature(castingCost: ManaCost, builder: CreatureBuilder.() -> Unit) {
        types.add(CardType.CREATURE)
        cost = castingCost
        spell {
            creature {
                creature(builder)
            }
        }
    }

    fun build(image: String): Card {
        return Card(name = name,
                superTypes = emptyList(),
                types = types.toSet(),
                subTypes = emptyList(),
                cost = cost,
                spell = spellBuilder.build(image),
                image = image)
    }
}

