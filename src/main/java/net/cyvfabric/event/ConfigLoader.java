package net.cyvfabric.event;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.ColorTheme;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.HUDManager;
import net.cyvfabric.hud.structure.DraggableTextLabel;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

import java.io.*;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

public class ConfigLoader {
    public static final String NAME = "config.txt";
    public static final String PATH = "config/cyvfabric/";
    public static final String FILEPATH = PATH + NAME;

    public static File configFile;

    public static void init(CyvClientConfig cfg) {
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> { //shutdown hook
            save(CyvFabric.config, true);
        });

        File dir = new File(PATH);
        if (!dir.exists()) dir.mkdirs();
        dir = new File(PATH);
        configFile = new File(FILEPATH);

        try {
            configFile.createNewFile();
        } catch (IOException e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
            return;
        }

        CyvFabric.LOGGER.info("Config file loaded!");

        read(cfg);

    }

    public static void read(CyvClientConfig cfg) {
        try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            String s = "";
            while ((s = bufferedreader.readLine()) != null) {
                String[] parts = s.split("=");
                try {
                    if (cfg.configFields.containsKey(parts[0])) {
                        cfg.configFields.get(parts[0]).set(parts[1]);
                    } else {
                        //key doesn't exist
                    }
                } catch (Exception e) {
                    CyvFabric.LOGGER.info("Config option \"" + Arrays.toString(parts) + "\" failed to load.");
                }

            }

            bufferedreader.close();

        } catch (Exception e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
        }

        //set colors
        CyvClientColorHelper.checkColor(CyvClientConfig.getString("color1", "aqua"), CyvClientConfig.getString("color2", "white"));

        //set theme
        try {
            CyvFabric.theme = ColorTheme.valueOf(CyvClientConfig.getString("theme", "CYVISPIRIA"));
        } catch (Exception e) {
            CyvFabric.theme = ColorTheme.CYVISPIRIA;
        }

        for (DraggableTextLabel mod : HUDManager.registeredRenderers) {
            mod.readConfigFields();
        }

        //decimal precision
        CyvFabric.df.setMinimumIntegerDigits(1);
        if (CyvClientConfig.getBoolean("trimZeroes", true)) CyvFabric.df.setMinimumFractionDigits(0);
        else CyvFabric.df.setMinimumFractionDigits(CyvClientConfig.getInt("df",5));
        CyvFabric.df.setMaximumFractionDigits(CyvClientConfig.getInt("df",5));
        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        CyvFabric.df.setDecimalFormatSymbols(s);

    }

    public static void save(CyvClientConfig cfg, boolean isFinal) {
        for (DraggableTextLabel mod : HUDManager.registeredRenderers) {
            mod.saveConfigFields();
        }

        if (!isFinal) {
            return;
        }

        try {
            FileWriter writer = new FileWriter(configFile, false);

            cfg.configFields.forEach((name, data) -> {
                try {
                    writer.write(name + "=" + data.value.toString() + "\n");
                } catch (IOException e) {CyvFabric.LOGGER.error(String.valueOf(e));}
            });

            CyvFabric.LOGGER.info("CyvClient config saved!");
            writer.close();

        } catch (IOException e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
        }
    }
}
