package io.github.cotrin8672

import io.github.cotrin8672.render.BeeHiveDecorator
import net.minecraft.world.item.Items
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BeeIndicator.MOD_ID)
object BeeIndicator {
    const val MOD_ID = "beeindicator"

    init {
        MOD_BUS.addListener(this::onRegisterItemDecorationsEvent)
    }

    @SubscribeEvent
    fun onRegisterItemDecorationsEvent(event: RegisterItemDecorationsEvent) {
        event.register(Items.BEE_NEST, BeeHiveDecorator)
        event.register(Items.BEEHIVE, BeeHiveDecorator)
    }
}
