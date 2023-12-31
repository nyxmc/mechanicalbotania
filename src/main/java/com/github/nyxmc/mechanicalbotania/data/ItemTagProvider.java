package com.github.nyxmc.mechanicalbotania.data;

import com.github.nyxmc.mechanicalbotania.MechanicalBotania;
import com.github.nyxmc.mechanicalbotania.init.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider {
    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockLookupProvider, ExistingFileHelper helper) {
        super(output, lookupProvider, blockLookupProvider, MechanicalBotania.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BotaniaTags.Items.CONTRIBUTOR_HEADFLOWERS).add(ItemRegistry.SPINERETTE_ITEM.get());
        tag(BotaniaTags.Items.SPECIAL_FLOWERS).add(ItemRegistry.SPINERETTE_ITEM.get());
        tag(BotaniaTags.Items.SPECIAL_FLOATING_FLOWERS).add(ItemRegistry.SPINERETTE_FLOATING_ITEM.get());
    }
}
