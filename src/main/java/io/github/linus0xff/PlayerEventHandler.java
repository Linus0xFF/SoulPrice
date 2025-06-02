package io.github.linus0xff;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEventHandler {

    private static final String NBT_KEY = "soulprice_deathcount";

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag oldData = oldPlayer.getPersistentData();
        int deathCount = oldData.getInt(NBT_KEY);

        newPlayer.getPersistentData().putInt(NBT_KEY, deathCount);

        float newMaxHealth = Math.max(2.0f, 20.0f - deathCount * 2.0f);
        AttributeInstance attr = newPlayer.getAttribute(Attributes.MAX_HEALTH);
        if (attr != null) {
            attr.setBaseValue(newMaxHealth);
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide()) return;

        CompoundTag data = player.getPersistentData();
        int deathCount = data.getInt(NBT_KEY) + 1;
        data.putInt(NBT_KEY, deathCount);
    }
}