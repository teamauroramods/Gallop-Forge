package com.teamaurora.gallop.core.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract Entity getRidingEntity();

    @Inject(method = "isEntityInsideOpaqueBlock", at = @At("HEAD"), cancellable = true)
    public void isEntityInsideOpaqueBlock(CallbackInfoReturnable<Boolean> cir) {
        if (getRidingEntity() != null) {
            cir.setReturnValue(false);
        }
    }
}
