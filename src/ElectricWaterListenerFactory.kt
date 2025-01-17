
package com.mercerenies.turtletroll

import com.mercerenies.turtletroll.BlockIgnorer
import com.mercerenies.turtletroll.feature.container.FeatureContainer
import com.mercerenies.turtletroll.feature.container.ListenerContainer
import com.mercerenies.turtletroll.feature.builder.BuilderState
import com.mercerenies.turtletroll.feature.builder.FeatureContainerFactory
import com.mercerenies.turtletroll.feature.HasEnabledStatus

import org.bukkit.Bukkit

// TODO This and ElectricWaterListener into a package
class ElectricWaterListenerFactory(
  private val pumpkinFeatureSupplier: (BuilderState) -> HasEnabledStatus?,
) : FeatureContainerFactory<FeatureContainer> {

  constructor(feature: HasEnabledStatus)
    : this({ _ -> feature })

  constructor(featureId: String)
    : this({ state -> state.getSharedData(featureId, HasEnabledStatus::class) })

  override fun create(state: BuilderState): FeatureContainer {
    var pumpkinFeature = pumpkinFeatureSupplier(state)
    if (pumpkinFeature == null) {
      // Log a warning and use a default value
      Bukkit.getLogger().warning("Could not find pumpkin feature, got null")
      pumpkinFeature = HasEnabledStatus.False
    }
    return ListenerContainer(ElectricWaterListener(state.plugin, pumpkinFeature))
  }

}
