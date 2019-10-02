package com.github.wakingrufus.magic.engine

import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.state.GameState
import com.github.wakingrufus.magic.state.StateCard

class Event(val operation: (GameState) -> GameState)

val StartGame = Event { gameState -> gameState.startGame().copy(priority = gameState.players.random().first) }
val Keep = Event { it }
val Pass = Event { gameState ->
    gameState.copy(
            playerStateMap = gameState.playerStateMap
                    .mapValues { ps -> ps.value.startGame(gameState.players.first { it.first == ps.key }.second) },
            priority = gameState.players.random().first)
}

fun PlayCard(player: Player, card: StateCard) = Event { gameState ->
    gameState.cast(player, card)
}
