package com.github.wakingrufus.magic

import com.github.wakingrufus.magic.card.Card

class Deck(format: Format, val cards: List<Card>) {
    fun byCmc(): List<Card> {
        return cards.sortedBy { it.convertedManaCost() }
    }
}

class DeckBuilder(val format: Format) {
    val cards: MutableList<Card> = mutableListOf()
    operator fun plus(card: Card) {
        cards.add(card)
    }

    operator fun plus(cards: List<Card>) {
        this.cards.addAll(cards)
    }

    fun build(): Deck {
        return Deck(format, cards.toList())
    }
    //  fun card()
}

infix fun Int.x(card: Card): List<Card> {
    return (1..this).map { card }
}

fun deck(format: Format, builder: DeckBuilder.() -> Unit): Deck {
    return DeckBuilder(format).apply(builder).build()
}