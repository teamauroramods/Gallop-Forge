package com.teamaurora.gallop.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class GallopConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Double> horseHealthBetterPercent;
        public final ForgeConfigSpec.ConfigValue<Double> horseHealthBetterAmount;
        public final ForgeConfigSpec.ConfigValue<Double> horseHealthMaxAmount;
        public final ForgeConfigSpec.ConfigValue<Double> horseJumpBetterPercent;
        public final ForgeConfigSpec.ConfigValue<Double> horseJumpBetterAmount;
        public final ForgeConfigSpec.ConfigValue<Double> horseJumpMaxAmount;
        public final ForgeConfigSpec.ConfigValue<Double> horseSpeedBetterPercent;
        public final ForgeConfigSpec.ConfigValue<Double> horseSpeedBetterAmount;
        public final ForgeConfigSpec.ConfigValue<Double> horseSpeedMaxAmount;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configurations for Gallop").push("common");

            builder.comment("Horse breeding statistics").push("breeding_stats");

            builder.comment("Health").push("health");
            horseHealthBetterPercent = builder.define("Chance health will be better than parents", 0.66);
            horseHealthBetterAmount = builder.define("Average amount health will be better by", 2.33);
            horseHealthMaxAmount = builder.define("Max health stat a bred horse can have", 30.0);
            builder.pop();

            builder.comment("Jump").push("jump");
            horseJumpBetterPercent = builder.define("Chance jump will be better than parents", 0.66);
            horseJumpBetterAmount = builder.define("Average amount jump will be better by", 0.1);
            horseJumpMaxAmount = builder.define("Max jump stat a bred horse can have", 1.0);
            builder.pop();

            builder.comment("Speed").push("Speed");
            horseSpeedBetterPercent = builder.define("Chance speed will be better than parents", 0.66);
            horseSpeedBetterAmount = builder.define("Average amount speed will be better by", 0.0375);
            horseSpeedMaxAmount = builder.define("Max speed stat a bred horse can have", 0.3375);
            builder.pop();

            builder.pop();

            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
