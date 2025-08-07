package com.spoonypanda.tconstructcraftingtweakscompat;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.InterModComms;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.ModList;

@Mod(TConstructCraftingTweaksCompat.MODID)
public class TConstructCraftingTweaksCompat {
    public static final String MODID = "tconstructcraftingtweakscompat";
    public static final int BUTTON_ADJUST_X = 25;

    public TConstructCraftingTweaksCompat() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (!ModList.get().isLoaded("craftingtweaks") || !ModList.get().isLoaded("tconstruct")) {
                System.out.println("[TConstructCraftingTweaksCompat] Required mods not present, skipping integration.");
                return;
            }

            try {
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

                InterModComms.sendTo("craftingtweaks", "RegisterProvider", () -> tag);
            } catch (Throwable t) {
                System.err.println("[TConstructCraftingTweaksCompat] Failed to register provider: " + t.getMessage());
            }
        });
    }
}