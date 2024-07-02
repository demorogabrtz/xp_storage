package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class Xpstorage implements ModInitializer {
    public static final String MOD_ID = "xp_storage";

    public static final Item CRYSTALLIZED_LAPIS = new Item(new Item.Settings());
    public static XpBook xp_book1;
    public static XpBook xp_book2;
    public static XpBook xp_book3;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        ModConfig config = ModConfig.get();

        xp_book1 = new XpBook(config.books.book1.capacity, config.books.book1.xpFromUsing, config.cosmetic.colorBar1,
                false, Rarity.COMMON);
        xp_book2 = new XpBook(config.books.book2.capacity, config.books.book2.xpFromUsing, config.cosmetic.colorBar2,
                true, Rarity.UNCOMMON);
        xp_book3 = new XpBook(config.books.book3.capacity, config.books.book3.xpFromUsing, config.cosmetic.colorBar3,
                true, Rarity.RARE);

        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "crystallized_lapis"), CRYSTALLIZED_LAPIS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "xp_book"), xp_book1);
        if (ModConfig.get().books.nbBooks > 1)
            Registry.register(Registries.ITEM, new Identifier(MOD_ID, "xp_book2"), xp_book2);
        if (ModConfig.get().books.nbBooks > 2)
            Registry.register(Registries.ITEM, new Identifier(MOD_ID, "xp_book3"), xp_book3);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.LAPIS_LAZULI, CRYSTALLIZED_LAPIS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(xp_book1);
            if (ModConfig.get().books.nbBooks > 1)
                entries.add(xp_book2);
            if (ModConfig.get().books.nbBooks > 2)
                entries.add(xp_book3);
        });
    }
}
