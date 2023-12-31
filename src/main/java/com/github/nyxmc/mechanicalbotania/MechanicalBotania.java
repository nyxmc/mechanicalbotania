package com.github.nyxmc.mechanicalbotania;

import com.github.nyxmc.mechanicalbotania.client.ClientRegistryHandler;
import com.github.nyxmc.mechanicalbotania.data.BlockTagProvider;
import com.github.nyxmc.mechanicalbotania.data.ItemTagProvider;
import com.github.nyxmc.mechanicalbotania.init.BlockEntityRegistry;
import com.github.nyxmc.mechanicalbotania.init.BlockRegistry;
import com.github.nyxmc.mechanicalbotania.init.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(MechanicalBotania.MODID)
public class MechanicalBotania {
    public static final String MODID = "mechanicalbotania";

    public MechanicalBotania() {
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

        BlockTagsProvider blockProvider = new BlockTagProvider(
                generator,
                event.getLookupProvider(),
                event.getExistingFileHelper()
        );

        generator.addProvider(event.includeServer(), (DataProvider.Factory<BlockTagsProvider>) output -> blockProvider);
        generator.addProvider(event.includeServer(), (DataProvider.Factory<ItemTagProvider>) (PackOutput output) -> new ItemTagProvider(
                output,
                event.getLookupProvider(),
                blockProvider.contentsGetter(),
                event.getExistingFileHelper()
        ));
    }

    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Add to ingredients tab
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemRegistry.MANA_MOTOR_ITEM);
            event.accept(ItemRegistry.SPINERETTE_ITEM);
            event.accept(ItemRegistry.SPINERETTE_FLOATING_ITEM);
        }
    }
}
