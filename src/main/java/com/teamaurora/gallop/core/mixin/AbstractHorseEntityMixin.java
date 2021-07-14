package com.teamaurora.gallop.core.mixin;

import com.teamaurora.gallop.core.GallopConfig;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin extends AnimalEntity {
    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    private double getRandomBetweenValue(double f1, double f2) {
        return this.rand.nextDouble() * Math.abs(f2-f1) + Math.min(f1,f2);
    }

    /**
     * @author ex0planetary
     * @reason gonna be incompatible with other mods that change baby horse attributes anyway lol
     */
    @Overwrite
    protected void setOffspringAttributes(AgeableEntity otherParent, AbstractHorseEntity baby) {
        // Health
        double myHealth = this.getBaseAttributeValue(Attributes.MAX_HEALTH);
        double otherHealth = otherParent.getBaseAttributeValue(Attributes.MAX_HEALTH);
        double maxHealth = GallopConfig.COMMON.horseHealthMaxAmount.get();
        double avgHealthIncrease = GallopConfig.COMMON.horseHealthBetterAmount.get();
        if (this.rand.nextFloat() < GallopConfig.COMMON.horseHealthBetterPercent.get()) {
            // Better
            double d = Math.max(myHealth, otherHealth) + this.rand.nextDouble() * avgHealthIncrease * 2;
            if (d > maxHealth) d = maxHealth;
            baby.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(d));
        } else {
            // Worse
            baby.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(getRandomBetweenValue(myHealth, otherHealth)));
        }
        // Jump
        double myJump = this.getBaseAttributeValue(Attributes.HORSE_JUMP_STRENGTH);
        double otherJump = otherParent.getBaseAttributeValue(Attributes.HORSE_JUMP_STRENGTH);
        double maxJump = GallopConfig.COMMON.horseJumpMaxAmount.get();
        double avgJumpIncrease = GallopConfig.COMMON.horseJumpBetterAmount.get();
        if (this.rand.nextFloat() < GallopConfig.COMMON.horseJumpBetterPercent.get()) {
            // Better
            double d = Math.max(myJump, otherJump) + this.rand.nextDouble() * avgJumpIncrease * 2;
            if (d > maxJump) d = maxJump;
            baby.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(d);
        } else {
            // Worse
            baby.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(getRandomBetweenValue(myJump, otherJump));
        }
        // Speed
        double mySpeed = this.getBaseAttributeValue(Attributes.MOVEMENT_SPEED);
        double otherSpeed = otherParent.getBaseAttributeValue(Attributes.MOVEMENT_SPEED);
        double maxSpeed = GallopConfig.COMMON.horseSpeedMaxAmount.get();
        double avgSpeedIncrease = GallopConfig.COMMON.horseSpeedBetterAmount.get();
        if (this.rand.nextFloat() < GallopConfig.COMMON.horseSpeedBetterPercent.get()) {
            // Better
            double d = Math.max(mySpeed, otherSpeed) + this.rand.nextDouble() * avgSpeedIncrease * 2;
            if (d > maxSpeed) d = maxSpeed;
            baby.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(d);
        } else {
            // Worse
            baby.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getRandomBetweenValue(mySpeed, otherSpeed));
        }
    }

    // stfu minecraft
    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }
}
