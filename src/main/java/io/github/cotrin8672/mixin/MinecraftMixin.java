package io.github.cotrin8672.mixin;

import io.github.cotrin8672.render.BeeHiveDecorator;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At("HEAD"))
    private void beeindicator$runTick(boolean pRenderLevel, CallbackInfo ci) {
        BeeHiveDecorator.INSTANCE.getTimer().advanceTime(Util.getMillis(), pRenderLevel);
    }
}
