package me.kingtux.resourceworlds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.io.File;
import java.lang.reflect.Field;

public class RWUtils {
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    /**
     * Stolen from NitroCommand
     *
     * @return returns the CommandMap
     */
    public static CommandMap getCommandMap() {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            return (CommandMap) commandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            ResourceWorlds.getInstance().getLogger().severe("Failure to get CommandMap");
            e.printStackTrace();
            return null;
        }
    }

}
