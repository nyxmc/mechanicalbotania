package com.github.nyxmc.mechanicalbotania.blocks;

import com.github.nyxmc.mechanicalbotania.init.BlockEntityRegistry;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
    public class ManaMotor extends DirectionalKineticBlock implements IBE<ManaMotorBlockEntity> {
        public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

        public ManaMotor(Properties properties) {
            super(properties);
            registerDefaultState(defaultBlockState().setValue(POWERED, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(POWERED);
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            Direction preferred = getPreferredFacing(context);
            if ((context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) || preferred == null)
                return super.getStateForPlacement(context);
            return defaultBlockState().setValue(FACING, preferred);
        }

        public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
            return AllShapes.MOTOR_BLOCK.get(state.getValue(FACING));
        }

        @Override
        public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
            return face == state.getValue(FACING);
        }

        @Override
        public Direction.Axis getRotationAxis(BlockState state) {
            return state.getValue(FACING).getAxis();
        }

        @Override
        public boolean hideStressImpact() {
            return true;
        }

        public void setPowered(Level world, BlockPos pos, boolean powered) {
            world.setBlock(pos, world.getBlockState(pos).setValue(POWERED, powered), 3);
        }

        @Override
        public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
            return true;
        }


        public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos from, boolean b) {
            if (!world.isClientSide) {
                boolean flag = state.getValue(POWERED);
                if (flag != world.hasNeighborSignal(pos)) {
                    if (flag) {
                        setPowered(world, pos, false);
                        world.scheduleTick(pos, this, 4);
                    } else {
                        setPowered(world, pos, true);
                        world.setBlock(pos, state.cycle(POWERED), 2);
                    }
                }
            }
        }

        public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
            if (state.getValue(POWERED) && !world.hasNeighborSignal(pos))
                world.setBlock(pos, state.cycle(POWERED), 2);
        }

        public Class<ManaMotorBlockEntity> getBlockEntityClass() {
            return ManaMotorBlockEntity.class;
        }

        public BlockEntityType<? extends ManaMotorBlockEntity> getBlockEntityType() {
            return BlockEntityRegistry.MANAMOTOR.get();
        }
    }

