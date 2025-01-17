
package com.mercerenies.turtletroll.falling

import com.mercerenies.turtletroll.Constants
import com.mercerenies.turtletroll.feature.container.FeatureContainer
import com.mercerenies.turtletroll.feature.container.RunnableContainer
import com.mercerenies.turtletroll.feature.builder.BuilderState
import com.mercerenies.turtletroll.feature.builder.FeatureContainerFactory

import org.bukkit.entity.Player
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.plugin.Plugin

class SandAttackRunnable(
  plugin: Plugin,
  val targetBlocks: Set<Material> = DEFAULT_TRIGGERS,
) : FallingObjectRunnable(plugin) {

  companion object : FeatureContainerFactory<FeatureContainer> {

    val DEFAULT_TRIGGERS = setOf(
      Material.SAND, Material.GRAVEL, Material.END_STONE,
    )

    override fun create(state: BuilderState): FeatureContainer =
      RunnableContainer(SandAttackRunnable(state.plugin))

  }

  override val name = "sandattack"

  override val description = "Sand drops on players' heads when standing on certain block types"

  override val maxDropHeight = 15

  override val blockToDrop = Material.SAND

  override val taskPeriod = Constants.TICKS_PER_SECOND.toLong()

  override fun shouldDropOn(player: Player): Boolean {
    val blockUnderneath = player.location.clone().add(0.0, -1.0, 0.0).block.type
    return targetBlocks.contains(blockUnderneath) && super.shouldDropOn(player)
  }

  override fun canDropThroughBlock(block: Block): Boolean = true

}
