package com.teamaurora.gallop.common.item;

import com.teamaurora.gallop.core.GallopConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class WhistleItem extends Item {
    public WhistleItem(Properties properties) {
        super(properties);
    }

    private static ItemStack addMountToItemStack(ItemStack itemIn, @Nullable Entity entityIn) {
        if (entityIn == null) {
            itemIn.removeChildTag("Mount");
            itemIn.removeChildTag("MountName");
        } else {
            itemIn.getOrCreateTag().putString("Mount", entityIn.getUniqueID().toString());
            itemIn.getOrCreateTag().putString("MountName", entityIn.hasCustomName() ? ITextComponent.Serializer.toJson(entityIn.getCustomName()) : ITextComponent.Serializer.toJson(entityIn.getType().getName()));
        }
        return itemIn;
    }

    @Nullable
    private static Entity getEntityFromItemStack(ServerWorld world, ItemStack itemIn) {
        if (!itemIn.hasTag()) return null;
        return getEntityFromTag(world, itemIn.getTag());
    }

    @Nullable
    private static Entity getEntityFromTag(ServerWorld world, @Nullable CompoundNBT tag) {
        if (tag == null) return null;
        String uuid = tag.getString("Mount");
        Entity entity = world.getEntityByUuid(UUID.fromString(uuid));
        if (entity != null) return entity;
        return null;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return addMountToItemStack(super.getDefaultInstance(), null);
    }

    private static boolean isMountable(String entityResourceLocation) {
        return GallopConfig.COMMON.whistleables.get().contains(entityResourceLocation);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (playerIn.getEntityWorld() instanceof ServerWorld) {
            Entity entity = getEntityFromItemStack((ServerWorld) playerIn.getEntityWorld(), stack);
            if (entity == null && isMountable(EntityType.getKey(target.getType()).toString())) {
                addMountToItemStack(stack, target);
                return ActionResultType.SUCCESS;
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (worldIn instanceof ServerWorld) {
            Entity entity = getEntityFromItemStack((ServerWorld) worldIn, stack);
            if (entity != null) {
                entity.setPosition(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
                return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
            } else {
                for (ServerWorld sw : worldIn.getServer().getWorlds()) {
                    Entity entity2 = getEntityFromItemStack(sw, stack);
                    if (entity2 != null) {
                        entity2.setPosition(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
                        entity2.setWorld(worldIn);
                        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
                    }
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private ITextComponent getNameFromStack(ItemStack itemIn) {
        if (!itemIn.hasTag()) return null;
        return getNameFromTag(itemIn.getTag());
    }

    private ITextComponent getNameFromTag(CompoundNBT tag) {
        if (tag == null) return null;
        String rawName = tag.getString("MountName");
        ITextComponent iTextComponent = ITextComponent.Serializer.getComponentFromJson(rawName);
        return iTextComponent;
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        ITextComponent mountName = getNameFromStack(stack);
        if (mountName != null) {
            tooltip.add(mountName);
        }
    }
}
