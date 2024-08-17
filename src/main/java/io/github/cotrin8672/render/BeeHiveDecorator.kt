package io.github.cotrin8672.render

import io.github.cotrin8672.util.InstanceFactory
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.neoforged.neoforge.client.IItemDecorator
import org.joml.Quaternionf

object BeeHiveDecorator : IItemDecorator {
    val timer = DeltaTracker.Timer(3f, 0L) { value ->
        value.coerceAtLeast(Minecraft.getInstance().level?.tickRateManager()?.millisecondsPerTick() ?: 0f)
    }
    private val translate = arrayOf(
        7f to 6f,
        4f to 12f,
        10f to 12f
    )

    override fun render(guiGraphics: GuiGraphics, font: Font, stack: ItemStack, xOffset: Int, yOffset: Int): Boolean {
        with(guiGraphics) {
            with(Minecraft.getInstance()) {
                val numBee = stack.components.get(DataComponents.BEES)?.size ?: 0
                for (i in 1..numBee) {
                    renderBee(xOffset + translate[i - 1].first, yOffset + translate[i - 1].second)
                }
            }
            with(stack) {
                val honeyLevel = stack.components.get(DataComponents.BLOCK_STATE)?.get(BlockStateProperties.LEVEL_HONEY)
                renderHoneyLevelIndicator(xOffset, yOffset, honeyLevel ?: 0)
            }
        }

        return true
    }

    context(Minecraft, GuiGraphics)
    private fun renderBee(
        x: Float,
        y: Float,
    ) {
        pose().withPoseContext {
            translate(x, y, 160f)
            scale(6f, -6f, 6f)
            mulPose(Quaternionf(0.0, 1.0, 0.0, Math.toRadians(-120.0)).normalize())

            val partialTicks = timer.getGameTimeDeltaPartialTick(true)
            InstanceFactory.getBeeRenderer().render(
                InstanceFactory.getBee(),
                0f,
                if (partialTicks * 2 >= 1) 2 - partialTicks * 2 else partialTicks * 2,
                this,
                bufferSource(),
                0xF000F0
            )
        }
    }

    context(GuiGraphics, ItemStack)
    private fun renderHoneyLevelIndicator(x: Int, y: Int, level: Int) {
        if (level == 0) return

        val barXStart = x + 13
        val barYStart = y + 15
        val barXEnd = barXStart + 2
        val barYEnd = barYStart - 29
        pose().withPoseContext {
            scale(1f, 0.5f, 1f)
            translate(0f, y.toFloat() + 15f, 160f)

            fill(RenderType.guiOverlay(), barXStart, barYStart, barXEnd, barYEnd, -16777216)
            for (i in 0 until level) {
                fill(
                    RenderType.guiOverlay(),
                    barXStart,
                    barYStart - 6 * i,
                    barXEnd - 1,
                    barYStart - 6 * i - 5,
                    (0xFFFF941DL and 0xFFFFFFFFL).toInt()
                )
            }
        }
    }
}
