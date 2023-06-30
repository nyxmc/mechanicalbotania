package com.doctor.mechanicalbotania.data;
import com.doctor.mechanicalbotania.MechanicalBotania;
import com.doctor.mechanicalbotania.init.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import vazkii.botania.common.block.BotaniaBlock;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.lib.BotaniaTags;

public class BlockTagProvider extends BlockTagsProvider {
    public BlockTagProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, MechanicalBotania.MODID, helper);
    }

    public static final TagKey<Block> PoorestSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "poorest_soil"));
    public static final TagKey<Block> PoorSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "poor_soil"));
    public static final TagKey<Block> MediumSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "medium_soil"));
    public static final TagKey<Block> GoodSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "good_soil"));
    public static final TagKey<Block> BestSoil = BlockTags.create(new ResourceLocation(MechanicalBotania.MODID, "best_soil"));


    @Override
    protected void addTags() {
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
