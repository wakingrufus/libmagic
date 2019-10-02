package com.github.wakingrufus.magic.sets

import com.github.wakingrufus.magic.Mana
import com.github.wakingrufus.magic.card.CardBuilder
import com.github.wakingrufus.magic.card.SetBuilder

object BasicLands : SetBuilder("BasicLands") {
    val Plains = card("Plains", "image") {
        basicLand(Mana.W)
    }
    val Island = card("Island", "image") {
        basicLand(Mana.U)
    }
    val Swamp = card("Swamp", "image") {
        basicLand(Mana.B)
    }
    val Mountain = card("Mountain", "image") {
        basicLand(Mana.R)
    }
    val Forest = card("Forest", "image") {
        basicLand(Mana.G)
    }
}

fun CardBuilder.basicLand(mana: Mana) {
    land(listOf(mana))
}