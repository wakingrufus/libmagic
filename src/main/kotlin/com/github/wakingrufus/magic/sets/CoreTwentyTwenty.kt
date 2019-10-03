package com.github.wakingrufus.magic.sets

import com.github.wakingrufus.magic.AnyMana
import com.github.wakingrufus.magic.Mana
import com.github.wakingrufus.magic.ManaCost
import com.github.wakingrufus.magic.StandardMana
import com.github.wakingrufus.magic.card.SetBuilder

object Core2020 : SetBuilder("Core Set 2020") {
    val GoblinAssailant = card("Goblin Assailant", "image") {
        creature(ManaCost(listOf(AnyMana(), StandardMana(Mana.R)))) {
            this.power = 2
            this.toughness = 2
        }
    }
    val Plains = reprint(BasicLands.Plains, "image")
    val Island = reprint(BasicLands.Island, "image")
    val Swamp = reprint(BasicLands.Swamp, "image")
    val Mountain = reprint(BasicLands.Mountain, "image")
    val Forest = reprint(BasicLands.Forest, "image")
}