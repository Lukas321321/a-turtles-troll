
package com.mercerenies.turtletroll

import com.mercerenies.turtletroll.feature.HasEnabledStatus
import com.mercerenies.turtletroll.feature.CompositeFeature
import com.mercerenies.turtletroll.feature.container.DropFeatureContainer
import com.mercerenies.turtletroll.feature.container.AbstractDropFeatureContainer
import com.mercerenies.turtletroll.feature.container.ListenerContainer
import com.mercerenies.turtletroll.feature.builder.BuilderState
import com.mercerenies.turtletroll.feature.builder.FeatureContainerFactory
import com.mercerenies.turtletroll.drop.ReplaceDropsAction
import com.mercerenies.turtletroll.drop.filter
import com.mercerenies.turtletroll.drop.asFeature
import com.mercerenies.turtletroll.drop.nearby.BeeAttackAction
import com.mercerenies.turtletroll.recipe.MelonRecipeDeleter
import com.mercerenies.turtletroll.recipe.MelonRecipeFeature

import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import org.bukkit.Bukkit

object MelompkinFeatureFactory : FeatureContainerFactory<DropFeatureContainer> {

  override fun create(state: BuilderState): DropFeatureContainer = object : AbstractDropFeatureContainer() {

    private val replaceMelonsAction = ReplaceDropsAction(ItemStack(Material.PUMPKIN)).filter {
      it.block.type == Material.MELON
    }.asFeature("replacemelons", "...")
    private val replacePumpkinsAction = ReplaceDropsAction(ItemStack(Material.MELON_SLICE, 4)).filter {
      it.block.type == Material.PUMPKIN
    }.asFeature("replacepumpkins", "...")

    private val melonPumpkinsFeature = CompositeFeature(
      "melompkinsdrops",
      "Melons and pumpkins swap drops",
      listOf(replaceMelonsAction, replacePumpkinsAction),
    )

    private val melonRecipeFeature = MelonRecipeFeature(state.plugin)
    private val melonRecipeDeleter = MelonRecipeDeleter(Bukkit.getServer())
    private val carvePumpkinListener = CarvePumpkinListener()

    private val melompkinFeature = CompositeFeature(
      "melompkin",
      "Several features of melons and pumpkins are interchanged",
      listOf(melonRecipeFeature, melonRecipeDeleter, carvePumpkinListener, melonPumpkinsFeature),
    )

    override val listeners = listOf(carvePumpkinListener)

    override val features = listOf(melompkinFeature)

    override val recipes = listOf(melonRecipeFeature)

    override val recipeDeleters = listOf(melonRecipeDeleter)

    override val preRules = listOf(replaceMelonsAction, replacePumpkinsAction)

  }

}
