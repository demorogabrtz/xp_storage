package com.github.charlyb01.xpstorage.config;

import com.github.charlyb01.xpstorage.Xpstorage;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "xp_storage")
public class ModConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("books")
    @ConfigEntry.Gui.TransitiveObject
    public BookConfig books = new BookConfig();

    @ConfigEntry.Category("bottles")
    @ConfigEntry.Gui.TransitiveObject
    public BottleConfig bottles = new BottleConfig();

    @ConfigEntry.Category("cosmetic")
    @ConfigEntry.Gui.TransitiveObject
    public CosmeticConfig cosmetic = new CosmeticConfig();

    public static ModConfig get() {
        if (!Xpstorage.areConfigsInit) {
            AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
            Xpstorage.areConfigsInit = true;
        }

        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
