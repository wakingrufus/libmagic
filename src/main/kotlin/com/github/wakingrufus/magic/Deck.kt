package com.github.wakingrufus.magic

import com.github.wakingrufus.magic.card.Card

class Deck(val cards: List<Card>) {
    fun byCmc(): List<Card> {
        return cards.sortedBy { it.convertedManaCost() }
    }
}

class DeckBuilder(format: Format){
    val cards: MutableList<Card> = mutableListOf()
  //  fun card()
}