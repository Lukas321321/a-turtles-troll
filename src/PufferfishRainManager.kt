
package com.mercerenies.turtletroll

import org.bukkit.entity.Player
import org.bukkit.entity.PufferFish
import org.bukkit.plugin.Plugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.Listener
import com.mercerenies.turtletroll.feature.container.FeatureContainer
import com.mercerenies.turtletroll.feature.container.ManagerContainer
import com.mercerenies.turtletroll.feature.builder.BuilderState
import com.mercerenies.turtletroll.feature.builder.FeatureContainerFactory

import kotlin.random.Random

class PufferfishRainManager(plugin: Plugin) : ScheduledEventRunnable<PufferfishRainManager.State>(plugin), Listener {

  companion object : FeatureContainerFactory<FeatureContainer> {
    val WARNING_TIME = 5700L
    val DROP_TIME = 6000L

    override fun create(state: BuilderState): FeatureContainer =
      ManagerContainer(PufferfishRainManager(state.plugin))

    fun spawnPufferfishOn(player: Player) {
      val world = player.getWorld()
      val targetLocation = player.getLocation().add(0.0, 15.0, 0.0)
      repeat(5) {
        val thisLocation = targetLocation.clone().add(Random.nextDouble(-1.0, 1.0), 0.0, Random.nextDouble(-1.0, 1.0));
        val pufferfish = world.spawn(thisLocation, PufferFish::class.java)
        pufferfish.setPuffState(2)
      }
    }

    val STATES = listOf(
      Event(State.Idle1, 0L),
      Event(State.Warned, WARNING_TIME),
      Event(State.Idle2, DROP_TIME),
    )

  }

  enum class State {
    Idle1, // Waiting for 11:59
    Warned, // Between 11:59 and 12:00
    Idle2, // Waiting for end of day
  }

  override val name: String = "pufferfish"

  override val description: String = "Pufferfish rain on all players at noon"

  override fun getAllStates() = STATES

  override fun onStateShift(newState: State) {
    when (newState) {
      State.Idle1 -> {
        // No action
      }
      State.Warned -> {
        Bukkit.broadcastMessage("It's raining... It's pouring...")
        Bukkit.broadcastMessage("The pufferfish are soaring...")
      }
      State.Idle2 -> {
        val onlinePlayers = Bukkit.getOnlinePlayers();
        for (player in onlinePlayers) {
          spawnPufferfishOn(player);
        }
      }
    }
  }

  @EventHandler
  fun onEntityDamage(event: EntityDamageEvent) {
    if (!isEnabled()) {
      return
    }

    val entity = event.getEntity()

    // Protect pufferfish death by some effects
    if (entity is PufferFish) {
      if ((event.getCause() == EntityDamageEvent.DamageCause.DROWNING) || (event.getCause() == EntityDamageEvent.DamageCause.DRYOUT) || (event.getCause() == EntityDamageEvent.DamageCause.FALL)) {
        event.setCancelled(true)
      }
    }

  }

}
