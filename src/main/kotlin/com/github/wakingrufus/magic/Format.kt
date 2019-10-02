package com.github.wakingrufus.magic

import com.github.wakingrufus.magic.card.Set
import com.github.wakingrufus.magic.sets.Core2020

class Format(val name: String, val deckSize: Int, val sets: List<Set>)

fun format(name: String, builder: FormatBuilder.() -> Unit): Format {
    return FormatBuilder(name).apply(builder).build()
}

class FormatBuilder(val name: String) {
    val sets: MutableList<Set> = mutableListOf()
    var deckSize = 60
    fun build(): Format {
        return Format(name, deckSize, sets.toList())
    }
}

val standard = format("Standard") {
    deckSize = 60
    sets.add(Core2020.build())
}