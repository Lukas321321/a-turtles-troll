
package com.mercerenies.turtletroll.event

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakEventListener(private val actions: List<Weight<BlockBreakAction>>) : Listener {

  @EventHandler
  fun onBlockBreak(event: BlockBreakEvent) {
    val validActions = actions.filter { it.value.shouldTrigger(event) }
    if (!validActions.isEmpty()) {
      val action = sample(validActions)
      action.trigger(event)
    }
  }

}
