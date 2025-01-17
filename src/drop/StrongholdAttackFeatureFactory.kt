
package com.mercerenies.turtletroll.drop

import com.mercerenies.turtletroll.Weight
import com.mercerenies.turtletroll.BlockIgnorer
import com.mercerenies.turtletroll.feature.HasEnabledStatus
import com.mercerenies.turtletroll.feature.CompositeFeature
import com.mercerenies.turtletroll.feature.container.DropFeatureContainer
import com.mercerenies.turtletroll.feature.container.AbstractDropFeatureContainer
import com.mercerenies.turtletroll.feature.container.ListenerContainer
import com.mercerenies.turtletroll.feature.builder.BuilderState
import com.mercerenies.turtletroll.feature.builder.FeatureContainerFactory
import com.mercerenies.turtletroll.drop.nearby.StrongholdSilverfishAttackAction

import org.bukkit.inventory.ItemStack
import org.bukkit.Material

object StrongholdAttackFeatureFactory : FeatureContainerFactory<DropFeatureContainer> {

  override fun create(state: BuilderState): DropFeatureContainer = object : AbstractDropFeatureContainer() {

    private val strongholdAttackAction = StrongholdSilverfishAttackAction().asFeature(
      "stronghold",
      "Breaking stone bricks will always result in a silverfish attack",
    )

    override val features = listOf(strongholdAttackAction)

    override val preRules = listOf(strongholdAttackAction)

  }

}
