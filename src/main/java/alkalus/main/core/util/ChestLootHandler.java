package alkalus.main.core.util;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.util.Config;
import com.emoniph.witchery.worldgen.ComponentShack;
import com.emoniph.witchery.worldgen.ComponentVillageApothecary;
import com.emoniph.witchery.worldgen.ComponentVillageBookShop;
import com.emoniph.witchery.worldgen.ComponentVillageKeep;
import com.emoniph.witchery.worldgen.ComponentVillageWatchTower;

public class ChestLootHandler {

    public static final String SHACK = "witchery:shack";
    public static final String APOTHECARY = "witchery:apothecary";
    public static final String BOOKSHOP = "witchery:bookshop";
    public static final String KEEP = "witchery:keep";
    public static final String WATCHTOWER = "witchery:watchtower";

    private static final Map<WeightedRandomChestContent[], String> CATEGORIES = new IdentityHashMap<>();

    public static void register() {
        addTable(SHACK, ComponentShack.shackChestContents);
        addTable(APOTHECARY, ComponentVillageApothecary.villageApothecaryChestContents);
        addTable(BOOKSHOP, getBookshopContents());
        addTable(KEEP, ComponentVillageKeep.villageTowerChestContents);
        addTable(WATCHTOWER, ComponentVillageWatchTower.villageTowerChestContents);
    }

    public static WeightedRandomChestContent[] getContents(WeightedRandomChestContent[] original, Random random) {
        final String category = CATEGORIES.get(original);
        return category == null ? original : ChestGenHooks.getItems(category, random);
    }

    // Witchery builds this table lazily on the first bookshop generation, so build it the same way up front
    private static WeightedRandomChestContent[] getBookshopContents() {
        if (ComponentVillageBookShop.bookshopChestContents == null) {
            final int pageMeta = Witchery.Items.GENERIC.itemVampireBookPage.damageValue;
            final List<WeightedRandomChestContent> list = new ArrayList<>();
            list.add(new WeightedRandomChestContent(Items.book, 0, 1, 1, 1));
            list.add(new WeightedRandomChestContent(Witchery.Items.GENERIC, pageMeta, 1, 1, 3));
            list.add(new WeightedRandomChestContent(Witchery.Items.GENERIC, pageMeta, 1, 1, 2));
            list.add(new WeightedRandomChestContent(Witchery.Items.GENERIC, pageMeta, 1, 1, 1));
            for (String entry : Config.instance().townBooks) {
                try {
                    String name = entry;
                    int meta = 0;
                    final int comma = entry.lastIndexOf(',');
                    if (comma >= 0) {
                        name = entry.substring(0, comma);
                        meta = Integer.parseInt(entry.substring(comma + 1));
                    }
                    final Item item = (Item) Item.itemRegistry.getObject(name);
                    if (item != null) {
                        list.add(new WeightedRandomChestContent(item, meta, 1, 1, 1));
                    }
                } catch (Throwable ignored) {}
            }
            ComponentVillageBookShop.bookshopChestContents = list.toArray(new WeightedRandomChestContent[0]);
        }
        return ComponentVillageBookShop.bookshopChestContents;
    }

    private static void addTable(String category, WeightedRandomChestContent[] contents) {
        CATEGORIES.putIfAbsent(contents, category);
        for (WeightedRandomChestContent content : contents) {
            ChestGenHooks.addItem(category, content);
        }
    }
}
