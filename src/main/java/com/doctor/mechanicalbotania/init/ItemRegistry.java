package com.doctor.mechanicalbotania.init;

import com.doctor.mechanicalbotania.MechanicalBotania;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MechanicalBotania.MODID);

    public static final RegistryObject<BlockItem> SPINERETTE_FLOATING_ITEM = ITEMS.register("spinerette_floating", () -> new BlockItem(BlockRegistry.spineretteFloating.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<BlockItem> SPINERETTE_ITEM = ITEMS.register("spinerette", () -> new BlockItem(BlockRegistry.spinerette.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<BlockItem> MANA_MOTOR_ITEM = ITEMS.register("mana_motor", () -> new BlockItem(BlockRegistry.MANAMOTOR.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
