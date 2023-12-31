package com.github.nyxmc.mechanicalbotania.blocks;

import com.github.nyxmc.mechanicalbotania.CommonConfig;
import com.github.nyxmc.mechanicalbotania.flowers.SpineretteBlockEntity;
import com.github.nyxmc.mechanicalbotania.init.BlockEntityRegistry;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block.Bound;

public class ManaMotorBlockEntity extends GeneratingKineticBlockEntity {
    public ManaMotorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MANAMOTOR.get(), pos, state);
        setLazyTickRate(20);
    }
    //im certain there's a better way to do this but i don't know what it is
    private final BlockPos[] boundFlowers = {Bound.UNBOUND_POS, Bound.UNBOUND_POS, Bound.UNBOUND_POS, Bound.UNBOUND_POS, Bound.UNBOUND_POS};
    private int numberBoundFlowers = 0;
    private final int baseStress = CommonConfig.baseStress.get();
    private static final int maxBound = 5;

    public boolean canAcceptFlower() {
        return numberBoundFlowers < maxBound;
    }
    public void initialize() {
        super.initialize();
        if (!this.hasSource() || this.getGeneratedSpeed() > this.getTheoreticalSpeed()) {
            this.updateGeneratedRotation();
        }

    }
    public void removeBoundFlower(BlockPos pos) {
        for(int i = 0; i < maxBound; i++) {
            if(boundFlowers[i].getX() == pos.getX() && boundFlowers[i].getY() == pos.getY() && boundFlowers[i].getZ() == pos.getZ()) {
                boundFlowers[i] = Bound.UNBOUND_POS;
                numberBoundFlowers = Math.max(0, this.numberBoundFlowers - 1);
                this.updateGeneratedRotation();
                this.updateSpeed = true;
                this.calculateAddedStressCapacity();
            }
        }
    }

    public void addBoundFlower(BlockPos pos) {
        boolean flowerAdded = false;
        for(int i = 0; i < maxBound; i++) {
            if(boundFlowers[i].getY() == Integer.MIN_VALUE && !flowerAdded) {
                boundFlowers[i] = pos;
                flowerAdded = true;
                this.numberBoundFlowers += 1;
                this.updateGeneratedRotation();
                this.updateSpeed = true;
                this.calculateAddedStressCapacity();
            }
        }
    }


    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if(boundFlowers != null) {
            ListTag listTag = compound.getList("BOUND_FLOWERS", Tag.TAG_COMPOUND);
            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag subTag = listTag.getCompound(i);
                boundFlowers[i] = new BlockPos(
                        subTag.getInt("TAG_BIND_X"),
                        subTag.getInt("TAG_BIND_Y"),
                        subTag.getInt("TAG_BIND_Z")
                );
            }
            this.numberBoundFlowers = compound.getInt("NUMBER_BOUND_FLOWERS");
        }
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if(boundFlowers != null) {

            ListTag list = new ListTag();
            for (int i = 0; i < boundFlowers.length; i++) {
                BlockPos pos = boundFlowers[i];
                CompoundTag sub = new CompoundTag();
                sub.putInt("TAG_BIND_X", pos.getX());
                sub.putInt("TAG_BIND_Y", pos.getY());
                sub.putInt("TAG_BIND_Z", pos.getZ());
                list.add(i, sub);
            }
            compound.put("BOUND_FLOWERS", list);
            compound.putInt("NUMBER_BOUND_FLOWERS", numberBoundFlowers);
        }
    }
    public float getRPM() {
        float rpm = 0.0f;
        for (BlockPos boundFlower : boundFlowers) {
            if (boundFlower != Bound.UNBOUND_POS && this.level.getBlockEntity(boundFlower) instanceof SpineretteBlockEntity spinerette) {
                if (spinerette != null) {
                    rpm = Math.min(256, rpm + spinerette.getRpm());
                } else {
                    rpm = 0.0f;
                }
            }
        }
        return rpm;
    }

    @Override
    public float getGeneratedSpeed() {
        return convertToDirection(this.getRPM(), this.getBlockState().getValue(ManaMotor.FACING));
    }
    @Override
    public float calculateAddedStressCapacity() {
        float capacity = Math.round((float) baseStress / (numberBoundFlowers * (getRPM() / 8) * 4));
        this.lastCapacityProvided = capacity;
        return capacity;
    }

    @Override
    public void destroy() {
        for (BlockPos boundFlower : boundFlowers) {
            if(boundFlower != Bound.UNBOUND_POS && this.level.getBlockEntity(boundFlower) instanceof SpineretteBlockEntity) {
                SpineretteBlockEntity blockEntity = (SpineretteBlockEntity) level.getBlockEntity(boundFlower);
                if (blockEntity != null) {
                    blockEntity.setBindPos(Bound.UNBOUND_POS);
                    blockEntity.sync();
                }
            }
        }
        super.destroy();
    }
}
