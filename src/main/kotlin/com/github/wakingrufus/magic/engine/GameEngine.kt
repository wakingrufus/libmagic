package com.github.wakingrufus.magic.engine

import com.github.wakingrufus.magic.Deck
import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.state.GameState
import kotlinx.coroutines.channels.Channel

class GameEngine(val players: List<Pair<Player, Deck>>) {
    val outboundEventChannel = Channel<Event>()
    var currentState = GameState(players)
    suspend fun processEvent(player: Player, event: Event): GameState {
        if (player == currentState.priority) {
            currentState = (event.operation(currentState))
            outboundEventChannel.send(event)
        }
        return currentState
    }
}
