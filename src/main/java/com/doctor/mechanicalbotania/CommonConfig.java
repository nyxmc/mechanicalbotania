package com.doctor.mechanicalbotania;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class CommonConfig {
    public static ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RPMValues;
    public static final ForgeConfigSpec.ConfigValue<Integer> baseStress;
    public static final ForgeConfigSpec.ConfigValue<Integer> manaCost;


    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        manaCost = COMMON_BUILDER.comment("Amount of mana used by the spinerette every 20 ticks (1 second)").define("manaCost", 5);
        baseStress = COMMON_BUILDER.comment("Base Stress value that is divided by number of bound flowers to produce total stress created by mana motor ").define("baseStressValue", 2048);
        RPMValues = COMMON_BUILDER.comment("Values of how much RPM each tier of soil generates, in ascending order").define("rpmvalues", List.of(8, 16, 32, 64, 128));
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
