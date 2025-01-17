
package com.mercerenies.turtletroll.cookie

import com.mercerenies.turtletroll.gravestone.CustomDeathMessageRegistry

import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import org.bukkit.entity.Player

// The arguments passed to CookieEffect.onEat
data class CookieEatenAction(
  val stack: ItemStack,
  val player: Player,
  val deathRegistry: CustomDeathMessageRegistry,
)
