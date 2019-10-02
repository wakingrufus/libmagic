package com.github.wakingrufus.magic.card

import com.github.wakingrufus.magic.Mana
import com.github.wakingrufus.magic.state.testGameState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EffectTest {

    @Test
    fun `test addMana`() {
        val gameState = testGameState()
        val player = gameState.players[0].first

        val playerState = gameState.playerStateMap[player]
        if (playerState != null) {
            val result = addMana(Mana.G).resolve(player, gameState)
            val resultPlayerState = result.playerStateMap[player]
            assertThat(resultPlayerState).`as`("player state found").isNotNull
            resultPlayerState?.run {
                assertThat(manaPool.manaMap[Mana.G]).isEqualTo(1)
            }
        }
    }
}