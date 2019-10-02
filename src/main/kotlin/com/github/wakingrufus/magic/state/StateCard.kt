package com.github.wakingrufus.magic.state

import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.card.Card
import java.util.*

data class StateCard(val id: UUID = UUID.randomUUID(), val owner: Player, val card: Card) {
    fun cast(controller: Player): List<StackEffect> {
        return card.spell.cast(controller, this)
    }
}