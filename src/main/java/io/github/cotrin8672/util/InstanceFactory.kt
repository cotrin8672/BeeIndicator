package io.github.cotrin8672.util

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.BeeRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Bee

object InstanceFactory {
    private lateinit var beeInstance: Bee
    private lateinit var beeRendererInstance: BeeRenderer

    context(Minecraft)
    fun getBee(): Bee {
        if (!this::beeInstance.isInitialized)
            beeInstance = Bee(EntityType.BEE, level!!)
        return beeInstance
    }

    context(Minecraft)
    fun getBeeRenderer(): BeeRenderer {
        if (!this::beeRendererInstance.isInitialized)
            beeRendererInstance = BeeRenderer(
                EntityRendererProvider.Context(
                    entityRenderDispatcher,
                    itemRenderer,
                    blockRenderer,
                    gameRenderer.itemInHandRenderer,
                    resourceManager,
                    entityModels,
                    font
                )
            )
        return beeRendererInstance
    }
}
