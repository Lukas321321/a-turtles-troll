
package com.mercerenies.turtletroll

import com.mercerenies.turtletroll.feature.AbstractFeature

import org.bukkit.util.Vector
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerEggThrowEvent
import org.bukkit.entity.EntityType
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.Egg
import org.bukkit.entity.Arrow
import org.bukkit.entity.Skeleton

import kotlin.random.Random

class EggArrowListener(val chance: Double = 1.0) : AbstractFeature(), Listener {

  companion object {
    val SKELETONS = setOf(EntityType.SKELETON, EntityType.STRAY)
  }

  override val name = "eggarrow"

  override val description = "Skeletons will sometimes throw eggs"

  @EventHandler
  fun onProjectileLaunch(event: ProjectileLaunchEvent) {
    if (!isEnabled()) {
      return
    }
    val entity = event.getEntity()
    val source = entity.getShooter()
    if ((entity is Arrow) && (source is Entity) && (SKELETONS.contains(source.getType()))) {
      if (Random.nextDouble() < chance) {
        val egg = entity.world.spawn(entity.location, Egg::class.java)
        egg.velocity = entity.velocity
      }
    }
  }

}
