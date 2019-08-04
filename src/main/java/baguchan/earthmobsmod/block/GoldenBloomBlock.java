package baguchan.earthmobsmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GoldenBloomBlock extends FlowerBlock {
	public GoldenBloomBlock(Effect effectIn, int duration, Block.Properties propertiesIn) {
		super(effectIn, duration, propertiesIn);
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isRemote) {
			if (entityIn instanceof PlayerEntity) {
				PlayerEntity playerEntity = (PlayerEntity) entityIn;
				if (!((PlayerEntity) entityIn).isCreative()) {
					playerEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 40));
				}
			} else if (entityIn instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entityIn;
				livingentity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 40));
			}

		}
	}
}