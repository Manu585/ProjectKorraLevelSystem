package me.manu.projectkorralevelsystem.menu;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.*;
import me.flame.menus.builders.items.ItemBuilder;
import me.flame.menus.items.MenuItem;
import me.flame.menus.menu.Menu;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {
    public static Menu mainMenu;
    public static Menu fireMenu;
    public static Menu earthMenu;
    public static Menu waterMenu;
    public static Menu airMenu;

    public static MenuItem fireItem;
    public static MenuItem lightingItem;
    public static MenuItem combustionItem;
    public static MenuItem earthItem;
    public static MenuItem metalItem;
    public static MenuItem lavaItem;
    public static MenuItem sandItem;
    public static MenuItem waterItem;
    public static MenuItem iceItem;
    public static MenuItem plantItem;
    public static MenuItem bloodItem;
    public static MenuItem healItem;
    public static MenuItem airItem;
    public static MenuItem flightItem;
    public static MenuItem spiritualItem;
    private static List<MenuItem> fireAbilityItems;
    private static List<MenuItem> earthAbilityItems;
    private static List<MenuItem> waterAbilityItems;
    private static List<MenuItem> airAbilityItems;

    public static void initMenus() {
        createMainMenu();
        createFireMenu();
        createEarthMenu();
        createWaterMenu();
        createAirMenu();
    }

    public static List<MenuItem> createFireAbilityItems() {
        fireAbilityItems = new ArrayList<>();

        for (CoreAbility ability : FireAbility.getAbilities()) {
            if (ability.getElement().equals(Element.FIRE)) {
                MenuItem item = ItemBuilder.of(Material.RED_WOOL).buildItem();
                item.setCustomName(ability.getName());
                fireAbilityItems.add(item);
                System.out.println(ability.getName());
            }
        }
        return fireAbilityItems;
    }

    public static List<MenuItem> createEarthAbilityItems() {
        earthAbilityItems = new ArrayList<>();

        for (CoreAbility ability : EarthAbility.getAbilities()) {
            if (ability.getElement().equals(Element.EARTH)) {
                MenuItem item = ItemBuilder.of(Material.GREEN_WOOL).buildItem();
                item.setCustomName(ability.getName());
                earthAbilityItems.add(item);
                System.out.println(ability.getName());
            }
        }
        return earthAbilityItems;
    }

    public static List<MenuItem> createWaterAbilityItems() {
        waterAbilityItems = new ArrayList<>();

        for (CoreAbility ability : WaterAbility.getAbilities()) {
            if (ability.getElement().equals(Element.WATER)) {
                MenuItem item = ItemBuilder.of(Material.BLUE_WOOL).buildItem();
                item.setCustomName(ability.getName());
                waterAbilityItems.add(item);
                System.out.println(ability.getName());
            }
        }
        return waterAbilityItems;
    }

    public static List<MenuItem> createAirAbilityItems() {
        airAbilityItems = new ArrayList<>();

        for (CoreAbility ability : AirAbility.getAbilities()) {
            if (ability.getElement().equals(Element.AIR)) {
                MenuItem item = ItemBuilder.of(Material.WHITE_WOOL).buildItem();
                item.setCustomName(ability.getName());
                airAbilityItems.add(item);
                System.out.println(ability.getName());
            }
        }
        return airAbilityItems;
    }

    private static void createMainMenu() {
        mainMenu = Menu.builder()
                .title("§#b042f5Skilltree")
                .rows(3)
                .normal();

        fireItem = ItemBuilder.of(Material.RED_WOOL).buildItem();
        fireItem.setCustomName(Element.FIRE.getColor() + "Firebending");

        earthItem = ItemBuilder.of(Material.GREEN_WOOL).buildItem();
        earthItem.setCustomName(Element.EARTH.getColor() + "Earthbending");

        waterItem = ItemBuilder.of(Material.BLUE_WOOL).buildItem();
        waterItem.setCustomName(Element.WATER.getColor() + "Waterbending");

        airItem = ItemBuilder.of(Material.WHITE_WOOL).buildItem();
        airItem.setCustomName(Element.AIR.getColor() + "Airbending");

        mainMenu.setItem(10, fireItem);
        mainMenu.setItem(12, earthItem);
        mainMenu.setItem(14, waterItem);
        mainMenu.setItem(16, airItem);
    }

    private static void createFireMenu() {
        fireMenu = Menu.builder()
                .title(Element.FIRE.getColor() + "Firebending")
                .rows(3)
                .normal();

        fireMenu.addItem(createFireAbilityItems());
    }

    private static void createEarthMenu() {
        earthMenu = Menu.builder()
                .title(Element.EARTH.getColor() + "Earthbending")
                .rows(3)
                .normal();

        earthMenu.addItem(createEarthAbilityItems());
    }

    private static void createWaterMenu() {
        waterMenu = Menu.builder()
                .title(Element.WATER.getColor() + "Waterbending")
                .rows(3)
                .normal();

        waterMenu.addItem(createWaterAbilityItems());
    }

    private static void createAirMenu() {
        airMenu = Menu.builder()
                .title(Element.AIR.getColor() + "Airbending")
                .rows(3)
                .normal();

        airMenu.addItem(createAirAbilityItems());
    }
}
