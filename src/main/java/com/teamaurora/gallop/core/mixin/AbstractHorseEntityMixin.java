package com.teamaurora.gallop.core.mixin;

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
        double maxHealth = 30.0;
        double avgHealthIncrease = 2.33;
        if (this.rand.nextFloat() < 0.66) {
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
        double maxJump = 1.0;
        double avgJumpIncrease = 0.1;
        if (this.rand.nextFloat() < 0.66) {
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
        double maxSpeed = 0.3375;
        double avgSpeedIncrease = 0.0375;
        if (this.rand.nextFloat() < 0.66) {
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
