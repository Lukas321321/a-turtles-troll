
package com.mercerenies.turtletroll.gravestone

import com.mercerenies.turtletroll.ObjectiveContainer
import com.mercerenies.turtletroll.feature.AbstractFeature
import com.mercerenies.turtletroll.feature.container.FeatureContainer
import com.mercerenies.turtletroll.feature.container.ListenerContainer
import com.mercerenies.turtletroll.feature.builder.BuilderState
import com.mercerenies.turtletroll.feature.builder.FeatureContainerFactory
import com.mercerenies.turtletroll.storage.GlobalDataHolder
import com.mercerenies.turtletroll.Constants
import com.mercerenies.turtletroll.ext.*

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.server.PluginEnableEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.ServerLoadEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.scoreboard.ScoreboardManager
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.plugin.Plugin
import org.bukkit.block.Block

import org.json.JSONObject

class DeathScoreboardListener(
  val plugin: Plugin,
  val pluginData: GlobalDataHolder,
) : AbstractFeature(), Listener {

  companion object : FeatureContainerFactory<FeatureContainer> {
    private val SCOREBOARD_NAME = "com.mercerenies.turtletroll.gravestone.DeathScoreboardFeature.SCOREBOARD_NAME"
    private val DATA_KEY = "com.mercerenies.turtletroll.gravestone.DeathScoreboardFeature.DATA_KEY"

    override fun create(state: BuilderState): FeatureContainer =
      ListenerContainer(DeathScoreboardListener(state.plugin, state.dataStore))

  }

  private class DeathObjectiveContainer() : ObjectiveContainer(SCOREBOARD_NAME, "Deaths") {

    override val criteria: String
      get() = "deathCount"

  }

  override val name = "deathscoreboard"

  override val description = "A scoreboard shows how many times each player has died"

  private var container: DeathObjectiveContainer? = null

  private fun loadData() {
    val container = DeathObjectiveContainer()
    this.container = container
    val objective = container.objective
    objective.displaySlot = DisplaySlot.SIDEBAR
  }

  override fun enable() {
    super.enable()
    container?.objective?.displaySlot = DisplaySlot.SIDEBAR
  }

  override fun disable() {
    super.disable()
    container?.objective?.displaySlot = null
  }

  private fun getScoreboardAsJSON(): JSONObject {
    val jsonObject = JSONObject("{}")
    val container = this.container
    if (container != null) {
      val scoreboard = container.scoreboard
      for (key in scoreboard.getEntries()) {
        val value = container.objective.getScore(key).getScore()
        jsonObject.put(key, value)
      }
    }
    return jsonObject
  }

  @EventHandler
  @Suppress("UNUSED_PARAMETER")
  fun onServerLoad(_event: ServerLoadEvent) {
    loadData()
  }

}
