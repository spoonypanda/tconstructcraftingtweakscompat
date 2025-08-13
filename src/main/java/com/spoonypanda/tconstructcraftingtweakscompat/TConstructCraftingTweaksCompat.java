package com.spoonypanda.tconstructcraftingtweakscompat;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TConstructCraftingTweaksCompat.MODID)
public class TConstructCraftingTweaksCompat {
    public static final String MODID = "tconstructcraftingtweakscompat";
    public static final int BUTTON_ADJUST_X = 25;

    public TConstructCraftingTweaksCompat() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::enqueueIMC);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        if (!ModList.get().isLoaded("craftingtweaks") || !ModList.get().isLoaded("tconstruct")) {
            return;
        }

        InterModComms.sendTo("craftingtweaks", "RegisterProvider", () -> {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("ContainerClass", "slimeknights.tconstruct.tables.inventory.table.CraftingStationContainer");
            tag.putInt("GridSlotNumber", 1);
            tag.putInt("GridSize", 9);

            CompoundNBT clear = new CompoundNBT();
            clear.putInt("ButtonX", BUTTON_ADJUST_X);
            tag.put("TweakClear", clear);

            CompoundNBT rotate = new CompoundNBT();
            rotate.putInt("ButtonX", BUTTON_ADJUST_X);
            tag.put("TweakRotate", rotate);

            CompoundNBT balance = new CompoundNBT();
            balance.putInt("ButtonX", BUTTON_ADJUST_X);
            tag.put("TweakBalance", balance);

            return tag;
        });
    }
}