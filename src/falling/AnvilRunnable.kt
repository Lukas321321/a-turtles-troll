
package com.mercerenies.turtletroll.falling

import com.mercerenies.turtletroll.Constants
import com.mercerenies.turtletroll.feature.container.FeatureContainer
import com.mercerenies.turtletroll.feature.container.RunnableContainer
import com.mercerenies.turtletroll.feature.builder.BuilderState
import com.mercerenies.turtletroll.feature.builder.FeatureContainerFactory

import org.bukkit.entity.Player
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.plugin.Plugin

import kotlin.collections.HashMap

class AnvilRunnable(plugin: Plugin) : FallingObjectRunnable(plugin) {
  private var memory = HashMap<Player, Location>()

  override val name = "anvil"

  override val description = "Drops an anvil on players who forget to move"

  override val minDropHeight = 7

  override val maxDropHeight = 15

  override val blockToDrop = Material.ANVIL

  override val taskPeriod = 10L * Constants.TICKS_PER_SECOND.toLong()

  override fun shouldDropOn(player: Player): Boolean {
    val loc = player.location
    val prevLoc = memory[player]
    if (prevLoc != null) {
      if ((loc.x == prevLoc.x) && (loc.y == prevLoc.y) && (loc.z == prevLoc.z) && (loc.world == prevLoc.world)) {
        return super.shouldDropOn(player)
      }
    }
    return false
  }

  override fun updatePlayer(player: Player) {
    super.updatePlayer(player)
    memory[player] = player.location
  }

}
