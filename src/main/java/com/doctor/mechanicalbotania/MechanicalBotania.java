package com.doctor.mechanicalbotania;

import com.doctor.mechanicalbotania.blocks.ManaMotorBlockEntity;
import com.doctor.mechanicalbotania.client.ClientRegistryHandler;
import com.doctor.mechanicalbotania.data.BlockTagProvider;
import com.doctor.mechanicalbotania.data.ItemTagProvider;
import com.doctor.mechanicalbotania.flowers.SpineretteBlockEntity;
import com.doctor.mechanicalbotania.init.BlockEntityRegistry;
import com.doctor.mechanicalbotania.init.BlockRegistry;
import com.doctor.mechanicalbotania.init.ItemRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


@Mod(MechanicalBotania.MODID)
public class MechanicalBotania
{
    public static final String MODID = "mechanicalbotania";

    public MechanicalBotania()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::gatherData);

        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITY_TYPE.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientRegistryHandler::init);
    }
    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        BlockTagsProvider blockProvider = new BlockTagProvider(generator, event.getExistingFileHelper());

        generator.addProvider(event.includeServer(), blockProvider);
        generator.addProvider(event.includeServer(), new ItemTagProvider(generator, blockProvider, MODID, event.getExistingFileHelper()));
    }
}
