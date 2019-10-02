package com.github.wakingrufus.magic.state

import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.card.Effect

data class StackEffect(val controller: Player,
                       val stateCard: StateCard? = null,
                       val image: String? = stateCard?.card?.name,
                       val effect: Effect)