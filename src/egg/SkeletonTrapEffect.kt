
package com.mercerenies.turtletroll.egg

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Skeleton
import org.bukkit.entity.SkeletonHorse
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.inventory.ItemStack

import kotlin.reflect.KClass
import kotlin.random.Random

class SkeletonTrapEffect(
  val count: Int,
  val plugin: Plugin,
) : EggHatchEffect {

  companion object {
    val TICKS_PER_SECOND = 20
  }

  private inner class SpawnHorses(val loc: Location) : BukkitRunnable() {
    override fun run() {
      repeat(count) {
        val horse = loc.world!!.spawn(loc, SkeletonHorse::class.java)
        val skeleton = loc.world!!.spawn(loc, Skeleton::class.java)
        skeleton.equipment?.helmet = ItemStack(Material.IRON_HELMET)
        horse.addPassenger(skeleton)
      }
    }
  }

  override fun onEggHatch(loc: Location) {
    loc.world!!.strikeLightning(loc)
    SpawnHorses(loc).runTaskLater(plugin, (TICKS_PER_SECOND / 3).toLong())
  }

}