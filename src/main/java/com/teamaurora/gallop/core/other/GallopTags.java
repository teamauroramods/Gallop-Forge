package com.teamaurora.gallop.core.other;

import com.teamaurora.gallop.core.Gallop;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class GallopTags {
    public static class Entities {
        public static final ITag.INamedTag<EntityType<?>> WHISTLEABLES = EntityTypeTags.createOptional(new ResourceLocation(Gallop.MODID, "whistleables"));
    }
}
