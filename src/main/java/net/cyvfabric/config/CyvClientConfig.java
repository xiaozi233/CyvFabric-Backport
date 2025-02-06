package net.cyvfabric.config;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.hud.HUDManager;
import net.cyvfabric.hud.structure.DraggableHUDElement;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CyvClientConfig {
    public HashMap<String,ConfigValue<?>> configFields = new LinkedHashMap<>();

    public void init() {
        //special globals
        configFields.put("color1", new ConfigValue<>("aqua"));
        configFields.put("color2", new ConfigValue<>("white"));
        configFields.put("theme", new ConfigValue<>("CYVISPIRIA"));
        configFields.put("whiteChat", new ConfigValue<>(false));

        configFields.put("df", new ConfigValue<>(5));
        configFields.put("trimZeroes", new ConfigValue<>(true));

        //parkour
        configFields.put("showMilliseconds", new ConfigValue<>(true));

        configFields.put("sendLbChatOffset", new ConfigValue<>(false));
        configFields.put("sendMmChatOffset", new ConfigValue<>(false));

        configFields.put("highlightLanding", new ConfigValue<>(false));
        configFields.put("highlightLandingCond", new ConfigValue<>(false));
        configFields.put("momentumPbCancelling", new ConfigValue<>(false));

        //inertia listener
        configFields.put("inertiaEnabled", new ConfigValue<>(false));
        configFields.put("inertiaTick", new ConfigValue<>(4));
        configFields.put("inertiaMin", new ConfigValue<>(-0.02));
        configFields.put("inertiaMax", new ConfigValue<>(0.02));
        configFields.put("inertiaAxis", new ConfigValue<>('x'));
        configFields.put("inertiaGroundType", new ConfigValue<>("normal"));

        //macros
        configFields.put("currentMacro", new ConfigValue<>("macro"));
        configFields.put("smoothMacro", new ConfigValue<>(false));

        for (DraggableHUDElement mod : HUDManager.registeredRenderers) {
            String name = mod.getName();
            mod.getConfigFields().forEach(
                    (property, configField) -> configFields.put(name + "_" + property, configField)
            );
        }
    }

    public static class ConfigValue<T> {
        public T value;
        public T defaultValue;
        public Type type;
        public ConfigValue(T value) {
            this.value = value;
            this.defaultValue = value;

            if (value instanceof Integer) {
                type = Type.INTEGER;
            } else if (value instanceof Long) {
                type = Type.LONG;
            } else if (value instanceof Double) {
                type = Type.DOUBLE;
            } else if (value instanceof Boolean) {
                type = Type.BOOLEAN;
            } else if (value instanceof Character) {
                type = Type.CHARACTER;
            } else if (value instanceof String) {
                type = Type.STRING;
            } else {
                type = Type.UNKNOWN;
            }
        }

        public void set(Object value) {
            try {
                this.value = (T) (value);
            } catch (Exception e) {
                CyvFabric.LOGGER.error(String.valueOf(e));
            }
        }
    }

    public enum Type {
        UNKNOWN, INTEGER, LONG, DOUBLE, BOOLEAN, CHARACTER, STRING
    }

    //A bunch of methods here
    public static void set(String k, Object value) {
        try {CyvFabric.config.configFields.get(k).set(value);
        } catch (Exception e) {CyvFabric.LOGGER.error(String.valueOf(e));}
    }

    public static int getInt(String k, int defaultValue) {
        try {return Integer.parseInt(CyvFabric.config.configFields.get(k).value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;}
    }

    public static long getLong(String k, long defaultValue) {
        try {return Long.parseLong(CyvFabric.config.configFields.get(k).value.toString());
        } catch (Exception e) {return defaultValue;}
    }

    public static double getDouble(String k, double defaultValue) {
        try {return Double.parseDouble(CyvFabric.config.configFields.get(k).value.toString());
        } catch (Exception e) {return defaultValue;}
    }

    public static boolean getBoolean(String k, boolean defaultValue) {
        try {return Boolean.parseBoolean(CyvFabric.config.configFields.get(k).value.toString());
        } catch (Exception e) {return defaultValue;}
    }

    public static char getChar(String k, char defaultValue) {
        try {return CyvFabric.config.configFields.get(k).value.toString().toCharArray()[0];
        } catch (Exception e) {return defaultValue;}
    }

    public static String getString(String k, String defaultValue) {
        try {return CyvFabric.config.configFields.get(k).value.toString();
        } catch (Exception e) {return defaultValue;}
    }
}
