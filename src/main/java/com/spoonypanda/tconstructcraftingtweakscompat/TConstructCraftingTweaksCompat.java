package com.spoonypanda.tconstructcraftingtweakscompat;

import com.mojang.logging.LogUtils;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("tconstructcraftingtweakscompat")
public class TConstructCraftingTweaksCompat
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int BUTTON_OFFSET_X = 25;
    private static final int BUTTON_OFFSET_Y = 18;

    public TConstructCraftingTweaksCompat()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        if (!ModList.get().isLoaded("craftingtweaks") || !ModList.get().isLoaded("tconstruct")) {
            LOGGER.warn("[TConstructCraftingTweaksCompat] Required mods not present, skipping integration.");
            return;
        }

        try {
            CompoundTag tag = new CompoundTag();
            tag.putString("ContainerClass", "slimeknights.tconstruct.tables.menu.CraftingStationContainerMenu");
            tag.putInt("GridSlotNumber", 1);
            tag.putInt("GridSize", 9);

            CompoundTag tweakRotate = new CompoundTag();
            tweakRotate.putInt("ButtonX",BUTTON_OFFSET_X);
            tag.put("TweakRotate", tweakRotate);
            CompoundTag tweakBalance = new CompoundTag();
            tweakBalance.putInt("ButtonX",BUTTON_OFFSET_X);
            tweakBalance.putInt("ButtonY",BUTTON_OFFSET_Y);
            tag.put("TweakBalance", tweakBalance);
            CompoundTag tweakClear = new CompoundTag();
            tweakClear.putInt("ButtonX",BUTTON_OFFSET_X);
            tweakClear.putInt("ButtonY",BUTTON_OFFSET_Y*2);
            tag.put("TweakClear", tweakClear);

            InterModComms.sendTo("craftingtweaks", "RegisterProvider", () -> tag);
        } catch (Throwable t) {
            LOGGER.error("Failed to register provider: " + t.getMessage());
        }
    }

}
