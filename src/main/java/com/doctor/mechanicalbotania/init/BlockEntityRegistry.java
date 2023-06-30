package com.doctor.mechanicalbotania.init;

import com.doctor.mechanicalbotania.MechanicalBotania;
import com.doctor.mechanicalbotania.blocks.ManaMotorBlockEntity;
import com.doctor.mechanicalbotania.flowers.SpineretteBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MechanicalBotania.MODID);

    public static final RegistryObject<BlockEntityType<ManaMotorBlockEntity>> MANAMOTOR = BLOCK_ENTITY_TYPE.register("mana_motor", () -> create(ManaMotorBlockEntity::new, BlockRegistry.MANAMOTOR.get()));

    public static final RegistryObject<BlockEntityType<SpineretteBlockEntity>> SPINERETTE = BLOCK_ENTITY_TYPE.register("spinerette", () -> create(SpineretteBlockEntity::new, BlockRegistry.spineretteFloating.get(), BlockRegistry.spinerette.get()));


    private static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory, blocks).build(null);
    }
}
