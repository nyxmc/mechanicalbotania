package com.github.nyxmc.mechanicalbotania.data;

import com.github.nyxmc.mechanicalbotania.MechanicalBotania;
import com.github.nyxmc.mechanicalbotania.init.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends BlockTagsProvider {
    public BlockTagProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(generator.getPackOutput(), lookupProvider, MechanicalBotania.MODID, helper);
    }

    public static final TagKey<Block> PoorestSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "poorest_soil"));
    public static final TagKey<Block> PoorSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "poor_soil"));
    public static final TagKey<Block> MediumSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "medium_soil"));
    public static final TagKey<Block> GoodSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "good_soil"));
    public static final TagKey<Block> BestSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "best_soil"));


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BotaniaTags.Blocks.GENERATING_SPECIAL_FLOWERS).add(BlockRegistry.spinerette.get());
        tag(BotaniaTags.Blocks.SPECIAL_FLOATING_FLOWERS).add(BlockRegistry.spineretteFloating.get());
        tag(BotaniaTags.Blocks.FLOATING_FLOWERS).add(BlockRegistry.spineretteFloating.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegistry.MANAMOTOR.get());

        tag(PoorestSoil).add(Blocks.DIRT);
        tag(PoorSoil).add(Blocks.ROOTED_DIRT);
        tag(MediumSoil).add(Blocks.GRASS_BLOCK);
        tag(GoodSoil).add(Blocks.PODZOL);
        tag(BestSoil).add(BotaniaBlocks.enchantedSoil);
    }
}
