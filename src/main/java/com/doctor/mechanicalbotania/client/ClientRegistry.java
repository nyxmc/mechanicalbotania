package com.doctor.mechanicalbotania.client;

import com.doctor.mechanicalbotania.blocks.ManaMotorBlockEntity;
import com.doctor.mechanicalbotania.blocks.ManaMotorRenderer;
import com.doctor.mechanicalbotania.init.BlockEntityRegistry;
import com.doctor.mechanicalbotania.init.BlockRegistry;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.botania.client.render.block_entity.SpecialFlowerBlockEntityRenderer;

import java.util.function.BiConsumer;

public class ClientRegistry {
    static void registerLayers(FMLClientSetupEvent event) {
        registerRenderTypes(ItemBlockRenderTypes::setRenderLayer);
    }
    public static void registerRenderTypes(BiConsumer<Block, RenderType> r) {
        RenderType cutout = RenderType.cutout();
        r.accept(BlockRegistry.spinerette.get(), cutout);
        r.accept(BlockRegistry.spineretteFloating.get(), cutout);
    }
    static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.SPINERETTE.get(), SpecialFlowerBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.MANAMOTOR.get(), ManaMotorRenderer::new);
    }

    static void registerInstance(FMLClientSetupEvent event) {
        InstancedRenderRegistry.BlockEntityConfig<ManaMotorBlockEntity> registry = InstancedRenderRegistry.configure(BlockEntityRegistry.MANAMOTOR.get());
        registry.factory(HalfShaftInstance::new);
        registry.apply();

    }
}
