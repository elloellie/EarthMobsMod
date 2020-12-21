package baguchan.earthmobsmod.event;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.entity.BoulderingZombieEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EarthMobsMod.MODID)
public class CommonEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPostRenderLiving(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof BoulderingZombieEntity) {
            BoulderingZombieEntity bouldering = (BoulderingZombieEntity) entity;

            float partialTicks = event.getPartialRenderTick();
            MatrixStack matrixStack = event.getMatrixStack();
            IRenderTypeBuffer bufferIn = event.getBuffers();

            if (bouldering.getClimingBoundingBox() != null) {
                if (Minecraft.getInstance().getRenderManager().isDebugBoundingBox()) {
                    matrixStack.push();

                    AxisAlignedBB axisalignedbb = bouldering.getClimingBoundingBox().offset(-bouldering.getPosX(), -bouldering.getPosY(), -bouldering.getPosZ());
                    WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), axisalignedbb, 1.0f, 1.0f, 0.3f, 1.0f);
                    matrixStack.pop();
                }
            }
        }
    }
}
