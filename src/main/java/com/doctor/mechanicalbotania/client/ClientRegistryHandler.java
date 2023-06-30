package com.doctor.mechanicalbotania.client;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientRegistryHandler {
    public static void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientRegistry::registerBlockEntityRenderers);
        bus.addListener(ClientRegistry::registerLayers);
        bus.addListener(ClientRegistry::registerInstance);
    }
}
