package io.github.linus0xff;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(SoulPrice.MODID)
public class SoulPrice {
    public static final String MODID = "soulprice";

    public SoulPrice() {
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());

    }
}