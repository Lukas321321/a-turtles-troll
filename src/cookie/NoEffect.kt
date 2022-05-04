
package com.mercerenies.turtletroll.cookie

import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import org.bukkit.entity.Player

object NoEffect : CookieEffect {

  override fun cancelsDefault(): Boolean = false

  override fun onEat(action: CookieEatenAction) {
    action.player.sendMessage("That cookie tastes pretty good.")
  }

}
