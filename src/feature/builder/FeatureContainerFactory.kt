
package com.mercerenies.turtletroll.feature.builder

import com.mercerenies.turtletroll.storage.GlobalDataHolder
import com.mercerenies.turtletroll.feature.container.CompositeFeatureContainer
import com.mercerenies.turtletroll.feature.container.BaseFeatureContainer

fun interface FeatureContainerFactory<out T : BaseFeatureContainer> {

  companion object {

    fun<T : BaseFeatureContainer, U> createComposite(
      factories: Iterable<FeatureContainerFactory<T>>,
      builderState: BuilderState,
      finalBuilder: (List<T>) -> U,
    ): U {
      // This could be an Iterable.map call, but since the factories
      // will often carry side effects, I want to make it very
      // explicit the order in which these are happening.
      val containers: ArrayList<T> = ArrayList()
      for (factory in factories) {
        containers.add(factory.create(builderState))
      }
      return finalBuilder(containers)
    }

  }

  fun create(state: BuilderState): T

}
