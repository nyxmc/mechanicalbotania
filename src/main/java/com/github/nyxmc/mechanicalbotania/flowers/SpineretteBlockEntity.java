package com.github.nyxmc.mechanicalbotania.flowers;
import com.github.nyxmc.mechanicalbotania.CommonConfig;
import com.github.nyxmc.mechanicalbotania.blocks.ManaMotorBlockEntity;
import com.github.nyxmc.mechanicalbotania.data.BlockTagProvider;
import com.github.nyxmc.mechanicalbotania.init.BlockEntityRegistry;
import com.github.nyxmc.mechanicalbotania.init.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block.Bound;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.common.proxy.Proxy;

import java.util.List;

public class SpineretteBlockEntity extends FunctionalFlowerBlockEntity {

    public final List<? extends Integer> RPMValues = CommonConfig.RPMValues.get();
    private final int manaCost = CommonConfig.manaCost.get() / 5;
    private static final String TAG_BIND_X = "bindX";
    private static final String TAG_BIND_Y = "bindY";
    private static final String TAG_BIND_Z = "bindZ";
    private static final int BIND_RANGE = 6;
    private BlockPos bindPos = Bound.UNBOUND_POS;
    private int rpm;
    public SpineretteBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPINERETTE.get(), pos, state);
    }
    @Override
    public int getMaxMana() {
        return 2000;
    }
    @Override
    public int getColor() {
        return 14203392;
    }
    @Override
    public RadiusDescriptor getRadius() {
        return null;
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        if(this.ticksExisted % 4 == 0 && !this.level.isClientSide && this.getMana() > 0 ) {
            BlockState soil = level.getBlockState(this.getBlockPos().below());
            if(this.bindPos.getY() != Integer.MIN_VALUE) {
                this.addMana(-manaCost);
            }
            if(this.ticksExisted % 20 == 0 && this.bindPos != Bound.UNBOUND_POS) {
                ManaMotorBlockEntity mm = (ManaMotorBlockEntity) level.getBlockEntity(bindPos);
                if(mm != null) {
                    mm.updateGeneratedRotation();
                    mm.updateSpeed = true;
                    mm.calculateAddedStressCapacity();
                    mm.setChanged();

                }

            }
            if(soil.is(BlockTagProvider.PoorestSoil)) {
                this.rpm = RPMValues.get(0);
            } else if(soil.is(BlockTagProvider.PoorSoil)) {
                this.rpm = RPMValues.get(1);
            } else if(soil.is(BlockTagProvider.MediumSoil)) {
                this.rpm = RPMValues.get(2);
            } else if(soil.is(BlockTagProvider.GoodSoil)) {
                this.rpm = RPMValues.get(3);
            } else if (soil.is(BlockTagProvider.BestSoil)) {
                this.rpm = RPMValues.get(4);
            } else {
                this.rpm = 0;
            }
            if(this.getMana() == 0) {
                this.rpm = 0;
                if(this.bindPos.getY() != Integer.MIN_VALUE && level.getBlockEntity(this.bindPos) instanceof ManaMotorBlockEntity be) {
                    be.updateGeneratedRotation();
                    be.updateSpeed = true;
                    be.calculateAddedStressCapacity();
                    be.setChanged();
                }
            }

        }
    }
    public void setBindPos(BlockPos bindPos) {
        this.bindPos = bindPos;
    }

    public BlockPos getBindPos() {
        return bindPos;
    }

    @Override
    public RadiusDescriptor getSecondaryRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), BIND_RANGE);
    }
    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt("RPM", this.rpm);
        cmp.putInt(TAG_BIND_X, bindPos.getX());
        cmp.putInt(TAG_BIND_Y, bindPos.getY());
        cmp.putInt(TAG_BIND_Z, bindPos.getZ());
    }
    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        this.rpm = cmp.getInt("RPM");
        bindPos = new BlockPos(
                cmp.getInt(TAG_BIND_X),
                cmp.getInt(TAG_BIND_Y),
                cmp.getInt(TAG_BIND_Z)
        );
    }


    @Override
    public boolean bindTo(Player player, ItemStack wand, BlockPos pos, Direction side) {
        boolean bound = super.bindTo(player, wand, pos, side);

        if (!bound && !pos.equals(bindPos) && pos.distSqr(getEffectivePos()) <= BIND_RANGE && !pos.equals(getEffectivePos()) && level.getBlockState(pos).getBlock() == BlockRegistry.MANAMOTOR.get() && level.getBlockEntity(pos) instanceof ManaMotorBlockEntity manaMotor) {
            if(manaMotor.canAcceptFlower()) {
                if(level.getBlockEntity(bindPos) instanceof ManaMotorBlockEntity mm && bindPos != pos) {
                    mm.removeBoundFlower(this.worldPosition);
                }
                bindPos = pos;
                sync();
                manaMotor.addBoundFlower(this.worldPosition);
                return true;
            } else {
                return false;
            }
        }
        return bound;
    }


    @Override
    public BlockPos getBinding() {
        return Proxy.INSTANCE.getClientPlayer().isShiftKeyDown() && bindPos.getY() != Integer.MIN_VALUE ? bindPos : super.getBinding();
    }

    public int getRpm() {
        return this.rpm;
    }
}
