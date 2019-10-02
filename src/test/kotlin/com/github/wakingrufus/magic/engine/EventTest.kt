package com.github.wakingrufus.magic.engine

import com.github.wakingrufus.magic.Deck
import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.sets.Core2020
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

internal class EventTest {
    @Test
    fun `test StartGame`() = runBlocking {
        val player1 = Player("player 1")
        val player2 = Player("player 2")
        val deck1 = Deck(listOf(Core2020.GoblinAssailant))
        val engine = GameEngine(players = listOf(player1 to deck1, player2 to deck1))
//        engine.action(player1, StartGame)
//        assertThat(engine.currentState.value.priority).isNotNull
    }
}
