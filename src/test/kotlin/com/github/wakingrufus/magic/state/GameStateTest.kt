package com.github.wakingrufus.magic.state

import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.deck
import com.github.wakingrufus.magic.sets.Core2020
import com.github.wakingrufus.magic.sets.Core2020.GoblinAssailant
import com.github.wakingrufus.magic.standard
import com.github.wakingrufus.magic.x
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

fun testGameState(): GameState {
    val player1 = Player("player 1")
    val player2 = Player("player 2")
    val deck1 = deck(standard) {
        +4 x GoblinAssailant
        +23 x Core2020.Mountain
    }
    return GameState(listOf(player1 to deck1, player2 to deck1))
}

internal class GameStateTest {
    @Test
    fun `test startGame`() {
        val player1 = Player("player 1")
        val player2 = Player("player 2")
        val deck1 = deck(standard) {
            +4 x GoblinAssailant
            +23 x Core2020.Mountain
        }
        val gameState = GameState(listOf(player1 to deck1, player2 to deck1))
        gameState.startGame()
        gameState.playerStateMap.values.forEach {
            assertThat(it.library).hasSize(27)
        }
    }
}