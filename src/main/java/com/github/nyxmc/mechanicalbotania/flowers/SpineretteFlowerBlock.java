package com.github.nyxmc.mechanicalbotania.flowers;

import com.github.nyxmc.mechanicalbotania.blocks.ManaMotorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.BotaniaBlock;
import vazkii.botania.forge.block.ForgeSpecialFlowerBlock;

import java.util.function.Supplier;

public class SpineretteFlowerBlock extends ForgeSpecialFlowerBlock {
    public SpineretteFlowerBlock(MobEffect stewEffect, int stewDuration, Properties props, Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> blockEntityType) {
        super(stewEffect, stewDuration, props, blockEntityType);
    }



    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState statenew, boolean something) {
        if (level.getBlockEntity(pos) != null && !level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SpineretteBlockEntity spinerette) {
                BlockPos bindPos = spinerette.getBindPos();
                if (level.getBlockEntity(bindPos) != null && level.getBlockEntity(bindPos) instanceof ManaMotorBlockEntity manaMotorBlockEntity) {
                    manaMotorBlockEntity.removeBoundFlower(spinerette.getBlockPos());
                }
            }
        }
        super.onRemove(state, level, pos, statenew, something);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        if(below.isAir() && level.getBlockEntity(pos) != null && !level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SpineretteBlockEntity spinerette) {
                BlockPos bindPos = spinerette.getBindPos();
                if (level.getBlockEntity(bindPos) != null && level.getBlockEntity(bindPos) instanceof ManaMotorBlockEntity manaMotorBlockEntity) {
                    manaMotorBlockEntity.removeBoundFlower(spinerette.getBlockPos());
                }
            }
        }
        return super.canSurvive(state, level, pos);
    }


}
