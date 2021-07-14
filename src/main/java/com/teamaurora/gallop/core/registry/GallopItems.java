package com.teamaurora.gallop.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import com.teamaurora.gallop.common.item.WhistleItem;
import com.teamaurora.gallop.core.Gallop;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gallop.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GallopItems {
    public static final ItemSubRegistryHelper HELPER = Gallop.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> WHISTLE = HELPER.createItem("whistle", ()->new WhistleItem(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)));
}
