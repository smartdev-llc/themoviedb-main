package io.github.justinlewis.core.event

/**
 * Event data for send or receive with Rx
 */

data class RxEvent<out T : Any?>(
    val code: Int,
    val extraData: T?
)
