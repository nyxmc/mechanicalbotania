package com.github.nyxmc.mechanicalbotania.flowers;

import com.github.nyxmc.mechanicalbotania.blocks.ManaMotorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;

import java.util.function.Supplier;

public class SpineretteFloatingFlowerBlock extends FloatingSpecialFlowerBlock {
    public SpineretteFloatingFlowerBlock(Properties props, Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> blockEntityType) {
        super(props, blockEntityType);
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
}
