
package com.mercerenies.turtletroll

import com.mercerenies.turtletroll.falling.AnvilRunnable
import com.mercerenies.turtletroll.falling.SandAttackRunnable
import com.mercerenies.turtletroll.chicken.ChickenDamageListener
import com.mercerenies.turtletroll.transformed.GhastSpawnerListener
import com.mercerenies.turtletroll.transformed.RavagerSpawnerListener
import com.mercerenies.turtletroll.transformed.DrownedSpawnerListener
import com.mercerenies.turtletroll.durability.DoorDamageListener
import com.mercerenies.turtletroll.durability.ButtonDamageListener
import com.mercerenies.turtletroll.angel.WeepingAngelManager
import com.mercerenies.turtletroll.mimic.MimicListener
import com.mercerenies.turtletroll.cake.CakeListener
import com.mercerenies.turtletroll.cake.CakeEat
import com.mercerenies.turtletroll.egg.EggListener
import com.mercerenies.turtletroll.egg.EggHatch
import com.mercerenies.turtletroll.dripstone.DripstoneManager
import com.mercerenies.turtletroll.gravestone.GravestoneListener
import com.mercerenies.turtletroll.gravestone.BedtimeManager
import com.mercerenies.turtletroll.feature.Feature
import com.mercerenies.turtletroll.feature.CompositeFeature

import org.bukkit.plugin.Plugin
import org.bukkit.event.Listener
import org.bukkit.Material

import kotlin.collections.Iterable

class MainContainer(val plugin: Plugin) {

  // TODO Move this to SandAttackRunnable
  companion object {

    val SAND_ATTACK_TRIGGERS = setOf(
      Material.SAND, Material.GRAVEL, Material.END_STONE,
    )

  }

  val pumpkinManager = PumpkinSlownessManager(plugin)
  val angelManager = WeepingAngelManager(plugin) // Not included in feature list (!!)
  val phantomManager = PetPhantomManager(plugin)
  val mossManager = ContagiousMossManager(plugin)
  val explosiveArrowManager = ExplosiveArrowManager(plugin)
  val dripstoneManager = DripstoneManager(plugin) // Not included in feature list (!!)
  val dragonBombManager = DragonBombManager(plugin)
  val pufferfishRainManager = PufferfishRainManager(plugin)
  val classicLavaManager = ClassicLavaManager(plugin)
  val bedtimeManager = BedtimeManager(plugin)

  val breakEvents = BlockBreakEvents()
  val chickenListener = ChickenDamageListener(plugin)
  val grassListener = GrassPoisonListener()
  val snowListener = SnowListener()
  val endStoneListener = EndStoneListener()
  val ghastListener = GhastSpawnerListener(plugin)
  val ravagerListener = RavagerSpawnerListener(plugin)
  val skeleListener = SkeletonWitherListener()
  val electricListener = ElectricWaterListener(plugin, pumpkinManager)
  val blazeListener = BlazeAttackListener(plugin)
  val zombifyListener = ZombifyTradeListener()
  val forestFireListener = ForestFireListener(plugin)
  val roseListener = WitherRoseListener()
  val doorListener = DoorDamageListener()
  val buttonListener = ButtonDamageListener()
  val levitationListener = LevitationListener()
  val plateListener = PressurePlateFireListener()
  val slabListener = SlowSlabListener()
  //val lightListener = TransformTorchOnSightListener(plugin, pumpkinManager)
  val lightListener = BreakLightOnSightListener(plugin, pumpkinManager)
  val lavaListener = LavaLaunchListener()
  val mimicListener = MimicListener(plugin)
  val cakeListener = CakeListener(plugin, CakeEat.defaultEffects(plugin))
  val bedListener = BedDropListener()
  val eggListener = EggListener(EggHatch.defaultEffects(plugin))
  val eggArrowListener = EggArrowListener()
  val eggDropListener = EggDropListener()
  val witherArmorListener = WitherArmorListener()
  val glassLuckListener = GlassLuckListener()
  val endDirtListener = EndDirtListener()
  val overgrowthListener = OvergrowthListener(plugin, OvergrowthListener::randomWood)
  val endCrystalListener = EndCrystalListener(plugin)
  val pillagerGunListener = PillagerGunListener()
  val fallDamageListener = FallDamageListener()
  val chargedCreeperListener = ChargedCreeperListener()
  val drownedListener = DrownedSpawnerListener(plugin)
  val gravestoneListener = GravestoneListener(plugin)
  val axolotlListener = AxolotlListener()
  val ghastLavaListener = GhastLavaListener(plugin, classicLavaManager.ignorer)

  val anvilRunnable = AnvilRunnable(plugin) // Not included in feature list (!!)
  val ghastBurnRunnable = GhastBurnRunnable(plugin)
  val sandAttackRunnable = SandAttackRunnable(plugin, SAND_ATTACK_TRIGGERS)

  // CancelDropAction is a BlockBreakAction and BedDropListener is a
  // Bukkit event listener, but conceptually they do the same thing,
  // so we want to treat them as one feature.
  private val dropCompositeFeature =
    CompositeFeature(
      breakEvents.cancelDropAction.name,
      breakEvents.cancelDropAction.description,
      listOf(breakEvents.cancelDropAction, bedListener)
    )

  val listeners: List<Listener> =
    listOf(
      breakEvents.listener, chickenListener, grassListener, snowListener,
      ghastListener, ravagerListener, skeleListener, electricListener,
      blazeListener, zombifyListener, forestFireListener, roseListener,
      endStoneListener, doorListener, angelManager, levitationListener,
      buttonListener, plateListener, slabListener, lightListener,
      phantomManager, lavaListener, pumpkinManager, mimicListener,
      bedListener, eggListener, eggArrowListener, eggDropListener,
      witherArmorListener, mossManager, explosiveArrowManager,
      cakeListener, dripstoneManager, glassLuckListener, endDirtListener,
      overgrowthListener, endCrystalListener, dragonBombManager, pufferfishRainManager,
      pillagerGunListener, classicLavaManager, fallDamageListener, chargedCreeperListener,
      drownedListener, gravestoneListener, axolotlListener, bedtimeManager,
      ghastLavaListener,
    )

  val features: List<Feature> =
    listOf(
      chickenListener, grassListener, snowListener,
      ghastListener, ravagerListener, skeleListener,
      blazeListener, zombifyListener, forestFireListener,
      roseListener, endStoneListener, doorListener,
      levitationListener, buttonListener,
      plateListener, slabListener, lightListener,
      phantomManager, lavaListener, pumpkinManager,
      mimicListener, dropCompositeFeature, eggListener,
      eggArrowListener, eggDropListener, witherArmorListener,
      mossManager, explosiveArrowManager, cakeListener,
      glassLuckListener, endDirtListener,
      overgrowthListener, endCrystalListener,
      dragonBombManager, pufferfishRainManager,
      pillagerGunListener, classicLavaManager,
      fallDamageListener, chargedCreeperListener,
      drownedListener, gravestoneListener, axolotlListener,
      bedtimeManager, ghastLavaListener, sandAttackRunnable,
      ghastBurnRunnable,
    ) + (breakEvents.getFeatures() - breakEvents.cancelDropAction)

}