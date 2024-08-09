package com.example.examplemod;

import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Fog {

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onFogDensityEvent(ViewportEvent.RenderFog event) {
//        event.setFarPlaneDistance(100);
//        System.out.println("1");
//        event.setCanceled(true);
    }

}
