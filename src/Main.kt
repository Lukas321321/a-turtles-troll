
package com.mercerenies.turtletroll

import com.mercerenies.turtletroll.falling.AnvilRunnable
import com.mercerenies.turtletroll.falling.SandAttackRunnable
import com.mercerenies.turtletroll.feature.FeatureManager
import com.mercerenies.turtletroll.feature.CompositeFeature
import com.mercerenies.turtletroll.recipe.StoneRecipeDeleter
import com.mercerenies.turtletroll.recipe.AnvilRecipeFeature
import com.mercerenies.turtletroll.recipe.AngelRecipeFeature
import com.mercerenies.turtletroll.recipe.DripstoneRecipeFeature

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
  val recipeDeleter = StoneRecipeDeleter(Bukkit.getServer())
  val anvilRunnable = AnvilRunnable(this)
  val ghastBurnRunnable = GhastBurnRunnable(this)
  val sandAttackRunnable = SandAttackRunnable(this, SAND_ATTACK_TRIGGERS)

  val anvilRecipeFeature = AnvilRecipeFeature(this)
  val angelRecipeFeature = AngelRecipeFeature(this)
  val dripstoneRecipeFeature = DripstoneRecipeFeature(this)

  val mainContainer = MainContainer(this)

  val anvilFeature = CompositeFeature(
    anvilRunnable.name,
    anvilRunnable.description,
    listOf(anvilRunnable, anvilRecipeFeature),
  )

  val angelFeature = CompositeFeature(
    mainContainer.angelManager.name,
    mainContainer.angelManager.description,
    listOf(mainContainer.angelManager, angelRecipeFeature),
  )

  val dripstoneFeature = CompositeFeature(
    mainContainer.dripstoneManager.name,
    mainContainer.dripstoneManager.description,
    listOf(mainContainer.dripstoneManager, dripstoneRecipeFeature),
  )

  val featureManager = FeatureManager(
    listOf(recipeDeleter, anvilFeature, angelFeature, dripstoneFeature, sandAttackRunnable, ghastBurnRunnable) + mainContainer.features
  )

  companion object {

    val SAND_ATTACK_TRIGGERS = setOf(
      Material.SAND, Material.GRAVEL, Material.END_STONE,
    )

  }

  override fun onEnable() {

    // Initialize all listeners
    for (listener in mainContainer.listeners) {
      Bukkit.getPluginManager().registerEvents(listener, this)
    }

    recipeDeleter.removeRecipes()
    anvilRecipeFeature.addRecipes()
    angelRecipeFeature.addRecipes()
    dripstoneRecipeFeature.addRecipes()
    mainContainer.explosiveArrowManager.addRecipes()
    anvilRunnable.register()
    sandAttackRunnable.register()
    ghastBurnRunnable.register()
    mainContainer.pufferfishRainManager.register()
    mainContainer.angelManager.register()
    mainContainer.phantomManager.register()
    mainContainer.pumpkinManager.register()
    mainContainer.mossManager.register()
    mainContainer.dripstoneManager.register()
    mainContainer.dragonBombManager.register()
    mainContainer.classicLavaManager.register()
    mainContainer.bedtimeManager.register()

    // Setup command
    this.getCommand("turtle")!!.setExecutor(featureManager)
    this.getCommand("turtle")!!.setTabCompleter(featureManager)

  }

  override fun onDisable() {
    recipeDeleter.addRecipes()
    anvilRecipeFeature.removeRecipes()
    angelRecipeFeature.removeRecipes()
    dripstoneRecipeFeature.removeRecipes()
    mainContainer.explosiveArrowManager.removeRecipes()
    try {
      anvilRunnable.cancel()
    } catch (_: IllegalStateException) {}
    try {
      sandAttackRunnable.cancel()
    } catch (_: IllegalStateException) {}
    try {
      ghastBurnRunnable.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.pufferfishRainManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.angelManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.phantomManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.pumpkinManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.mossManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.dripstoneManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.dragonBombManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.classicLavaManager.cancel()
    } catch (_: IllegalStateException) {}
    try {
      mainContainer.bedtimeManager.cancel()
    } catch (_: IllegalStateException) {}

    // Remove command
    this.getCommand("turtle")?.setExecutor(null)
    this.getCommand("turtle")?.setTabCompleter(null)

  }

}
