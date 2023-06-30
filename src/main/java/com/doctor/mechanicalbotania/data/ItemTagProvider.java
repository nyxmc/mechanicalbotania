package com.doctor.mechanicalbotania.data;

import com.doctor.mechanicalbotania.init.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.lib.BotaniaTags;

public class ItemTagProvider extends ItemTagsProvider{

    public ItemTagProvider(DataGenerator gen, BlockTagsProvider provider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, provider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BotaniaTags.Items.CONTRIBUTOR_HEADFLOWERS).add(ItemRegistry.SPINERETTE_ITEM.get());
        tag(BotaniaTags.Items.SPECIAL_FLOWERS).add(ItemRegistry.SPINERETTE_ITEM.get());;
        tag(BotaniaTags.Items.SPECIAL_FLOATING_FLOWERS).add(ItemRegistry.SPINERETTE_FLOATING_ITEM.get());
    }

}
