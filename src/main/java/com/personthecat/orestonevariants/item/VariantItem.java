package com.personthecat.orestonevariants.item;

import com.personthecat.orestonevariants.blocks.BaseOreVariant;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class VariantItem extends BlockItem {
    private final String bgKey = getBackgroundKey();

    public VariantItem(Block block) {
        this(block, new Item.Properties().group(VariantGroup.GROUP), block.getRegistryName());
    }

    protected VariantItem(Block block, Item.Properties properties, ResourceLocation name) {
        super(block, properties);
        setRegistryName(name);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        ITextComponent name = super.getDisplayName(stack);
        return name.appendText(" (")
            .appendSibling(new TranslationTextComponent(bgKey))
            .appendText(")");
    }

    private String getBackgroundKey() {
        return ((BaseOreVariant) getBlock()).bgBlock.getBlock().getTranslationKey();
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        // Manually doing this so that items get filled with the correct alternatives.
        if (isInGroup(group)) {
            items.add(new ItemStack(this));
        }
    }
}