
package com.mercerenies.turtletroll

import com.mercerenies.turtletroll.falling.AnvilRunnable
import com.mercerenies.turtletroll.feature.FeatureManager
import com.mercerenies.turtletroll.recipe.StoneRecipeDeleter

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
  val recipeDeleter = StoneRecipeDeleter(Bukkit.getServer())
  val anvilRunnable = AnvilRunnable()
  val listenerManager = AllPluginListeners(this)
  val featureManager = FeatureManager(
    listOf(recipeDeleter, anvilRunnable) + listenerManager.getFeatures()
  )

  override fun onEnable() {
    for (listener in listenerManager) {
      Bukkit.getPluginManager().registerEvents(listener, this)
    }
    recipeDeleter.removeRecipes()
    anvilRunnable.register(this)
    listenerManager.angelManager.register()
    listenerManager.angelManager.disable() // Disabled by default (due to lag)
    this.getCommand("turtle")!!.setExecutor(featureManager)
    this.getCommand("turtle")!!.setTabCompleter(featureManager)
  }

  override fun onDisable() {
    recipeDeleter.addRecipes()
    try {
      anvilRunnable.cancel()
    } catch (_: IllegalStateException) {}
    try {
      listenerManager.angelManager.cancel()
    } catch (_: IllegalStateException) {}
    this.getCommand("turtle")?.setExecutor(null)
    this.getCommand("turtle")?.setTabCompleter(null)
  }

}
