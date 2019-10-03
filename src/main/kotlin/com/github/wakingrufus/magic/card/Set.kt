package com.github.wakingrufus.magic.card

@DslMarker
annotation class MagicDsl

class Set(val name: String, val cards: List<Card>)

fun set(name: String, builder: SetBuilder.() -> Unit): Set {
    return SetBuilder(name).apply(builder).build()
}

@MagicDsl
open class SetBuilder(val name: String, val cards: MutableList<Card> = mutableListOf()) {
    fun card(card: Card) {
        cards.add(card)
    }

    fun reprint(card: Card, image: String): Card {
        return card.copy(image = image).also {
            this@SetBuilder.cards.add(it)
        }
    }

    fun card(name: String, image: String, card: CardBuilder.() -> Unit): Card {
        return CardBuilder(name).apply(card).build(image).apply {
            this@SetBuilder.cards.add(this)
        }
    }

    fun build(): Set {
        return Set(name, cards.toList())
    }
}