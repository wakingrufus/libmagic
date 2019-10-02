package com.github.wakingrufus.magic.state

import com.github.wakingrufus.magic.*
import com.github.wakingrufus.magic.card.Permanent
import javafx.beans.property.SimpleObjectProperty
import mu.KLogging
import java.util.*

val gameStateLogger = KLogging().logger("gamestate")

data class PlayerState(
        val player: Player,
        val library: List<StateCard> = listOf(),
        val hand: List<StateCard> = listOf(),
        val graveyard: List<StateCard> = listOf(),
        val exile: List<StateCard> = listOf(),
        val battlefield: List<PermanentState> = listOf(),
        val manaPool: ManaPool = ManaPool()) {
    fun startGame(deck: Deck): PlayerState = this.copy(library = deck.cards.shuffled().map { StateCard(card = it, owner = player) })
    fun addMana(mana: Mana): PlayerState {
        return this.copy(manaPool = this.manaPool.addMana(mana))
    }

    fun putIntoPlay(permanent: Permanent, owner: Player, card: StateCard?, tapped: Boolean = false): PlayerState {
        return this.copy(battlefield = battlefield.plus(PermanentState(card = card, owner = owner, permanent = permanent, tapped = tapped)))
    }

    fun cardInHand(id: UUID): StateCard? {
        return hand.firstOrNull { it.id == id }
    }

    fun play(stateCard: StateCard): PlayerState {
        return this.copy(hand = hand.minus(stateCard))
    }

}

class PlayerTurn(val player: Player, val turn: Turn) {
    fun nextPhase(): PlayerTurn {
        return PlayerTurn(player, turn.copy(turn.phases.subList(1, turn.phases.size)))
    }
}

data class GameState(val players: List<Pair<Player, Deck>>,
                     val playerStateMap: Map<Player, PlayerState> = players.map { it.first to PlayerState(it.first) }.toMap(),
                     val stack: List<StackEffect> = listOf(),
                     val priority: Player = players.random().first) {
    companion object : KLogging()

    val turn = SimpleObjectProperty<Player>()
    val phase = SimpleObjectProperty<TurnPhase>()
    fun startGame(): GameState {
        return players.fold(this) { state, (player, deck) ->
            state.applyToPlayer(player) { it.startGame(deck) }
        }
    }

    fun addMana(player: Player, mana: Mana): GameState {
        return applyToPlayer(player) { it.addMana(mana) }
    }

    fun passPriority(): GameState {
        return this.copy(priority = players.get(players.map { it.first }.indexOf(this.priority) + 1).first)
    }

    fun cast(player: Player, stateCard: StateCard): GameState {
        return applyToPlayer(player) { it.play(stateCard) }.let {
            stateCard.cast(player).fold(it) { gs, se -> gs.addToStack(se) }
        }
    }

    fun putIntoPlay(controller: Player, owner: Player, permanent: Permanent, stateCard: StateCard?, tapped: Boolean): GameState {
        return this.applyToPlayer(controller) {
            it.putIntoPlay(owner = owner, permanent = permanent, card = stateCard, tapped = tapped)
        }
    }

    fun addToStack(stackEffect: StackEffect): GameState {
        logger.info { "putting on stack: $stackEffect" }
        return this.copy(stack = this.stack.plus(stackEffect))
    }

    private fun applyToPlayer(player: Player, transform: (PlayerState) -> PlayerState): GameState {
        return this.copy(playerStateMap = playerStateMap.mapValues { if (it.key == player) transform(it.value) else it.value })
    }

    fun resolve(): GameState {
        return this.stack.firstOrNull()?.let { stackEffect ->
            stackEffect.effect.resolve(stackEffect.controller, this)
        } ?: this
    }
}

class PermanentState(val card: StateCard?,
                     val owner: Player,
                     val tapped: Boolean,
                     val permanent: Permanent)