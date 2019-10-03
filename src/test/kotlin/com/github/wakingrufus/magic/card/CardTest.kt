package com.github.wakingrufus.magic.card

import com.github.wakingrufus.magic.AnyMana
import com.github.wakingrufus.magic.Mana
import com.github.wakingrufus.magic.ManaCost
import com.github.wakingrufus.magic.StandardMana
import com.github.wakingrufus.magic.sets.basicLand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CardTest {
    @Test
    fun `test basic land`() {
        val card = CardBuilder("card name").apply {
            basicLand(Mana.G)
        }

        assertThat(card.build("image").convertedManaCost()).isEqualTo(0)
    }

    @Test
    fun `test creature`() {
        val card = CardBuilder("card name").apply {
            creature(ManaCost(listOf(AnyMana(), StandardMana(Mana.R)))) {
                power = 1
                toughness = 1
            }
        }

        assertThat(card.build("image").convertedManaCost()).isEqualTo(2)
    }
}