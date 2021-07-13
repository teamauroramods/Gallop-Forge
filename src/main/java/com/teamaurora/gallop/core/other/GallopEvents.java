package com.teamaurora.gallop.core.other;

import com.teamaurora.gallop.core.Gallop;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gallop.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GallopEvents {
    @SubscribeEvent
    public void onHorseFuck(BabyEntitySpawnEvent event) {
        int fuck = 0;
        if (event.getParentA() instanceof HorseEntity && event.getParentB() instanceof HorseEntity) {
            // two trucks having sex
            event.setCanceled(true);
        }
    }
}
