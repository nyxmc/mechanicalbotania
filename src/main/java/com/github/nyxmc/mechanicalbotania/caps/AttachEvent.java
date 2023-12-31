package com.github.nyxmc.mechanicalbotania.caps;

import com.github.nyxmc.mechanicalbotania.MechanicalBotania;
import com.github.nyxmc.mechanicalbotania.flowers.SpineretteBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.api.block_entity.BindableSpecialFlowerBlockEntity;
import vazkii.botania.forge.CapabilityUtil;

@Mod.EventBusSubscriber
public class AttachEvent {
    @SubscribeEvent
    public static void attachBlockEntityCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof SpineretteBlockEntity spineretteBlockEntity) {
            event.addCapability(
                    new ResourceLocation(MechanicalBotania.MODID, "wand_hud"),
                    CapabilityUtil.makeProvider(
                            BotaniaForgeClientCapabilities.WAND_HUD,
                            new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>(spineretteBlockEntity)
                    )
            );
        }
    }
}
