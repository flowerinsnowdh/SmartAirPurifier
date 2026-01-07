package cn.flowerinsnow.smartairpurifier.config;

import cn.flowerinsnow.smartairpurifier.SmartAirPurifier;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import org.jetbrains.annotations.Contract;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.core.json.JsonWriteFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static final int VERSION = 1;
    private static final String FIELD_VERSION = "version";
    private static final String FIELD_REMOVE_FOG = "remove_fog";
    private static final String FIELD_REMOVE_FOG_LAVA = "lava";
    private static final String FIELD_REMOVE_FOG_POWDER_SNOW = "powder_snow";
    private static final String FIELD_REMOVE_FOG_BLINDNESS = "blindness";
    private static final String FIELD_REMOVE_FOG_DARKNESS = "darkness";
    private static final String FIELD_REMOVE_FOG_WATER = "water";
    private static final String FIELD_REMOVE_FOG_ATMOSPHERIC = "atmospheric";

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder()
            .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS, JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, JsonReadFeature.ALLOW_SINGLE_QUOTES, JsonReadFeature.ALLOW_UNQUOTED_PROPERTY_NAMES)
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(JsonWriteFeature.QUOTE_PROPERTY_NAMES)
            .build();

    private Config() {
    }

    private static ObjectNode root;

    public static void init() {
        if (!Files.exists(getConfigPath())) {
            root = JSON_MAPPER.createObjectNode();
            root.put(FIELD_VERSION, VERSION);
            ObjectNode removeFogNode = JSON_MAPPER.createObjectNode();
            removeFogNode.put(FIELD_REMOVE_FOG_LAVA, true);
            removeFogNode.put(FIELD_REMOVE_FOG_POWDER_SNOW, true);
            removeFogNode.put(FIELD_REMOVE_FOG_BLINDNESS, true);
            removeFogNode.put(FIELD_REMOVE_FOG_DARKNESS, true);
            removeFogNode.put(FIELD_REMOVE_FOG_WATER, true);
            removeFogNode.put(FIELD_REMOVE_FOG_ATMOSPHERIC, true);
            root.set(FIELD_REMOVE_FOG, removeFogNode);

            save();
        } else if (!Files.isRegularFile(getConfigPath())) {
            IOException ex = new IOException("config file " + getConfigPath() + " is not a regular file.");
            MinecraftClient.getInstance().printCrashReport(CrashReport.create(ex, ex.getMessage()));
        } else {
            root = ((ObjectNode) JSON_MAPPER.readTree(getConfigPath()));
            if (!root.has(FIELD_VERSION) || root.get(FIELD_VERSION).asInt() != VERSION) {
                try {
                    Files.delete(getConfigPath());
                } catch (IOException e) {
                    MinecraftClient.getInstance().printCrashReport(CrashReport.create(e, "unable to delete old config file."));
                    return;
                }
                init();
            }
        }
    }

    @Contract(mutates = "io")
    public static void save() {
        JSON_MAPPER.writeValue(getConfigPath(), root);
    }

    @Contract(pure = true)
    public static boolean removeFogLava() {
        return root.get(FIELD_REMOVE_FOG).get(FIELD_REMOVE_FOG_LAVA).asBoolean();
    }

    public static void removeFogLava(boolean removeFogLava) {
        ((ObjectNode) root.get(FIELD_REMOVE_FOG)).put(FIELD_REMOVE_FOG_LAVA, removeFogLava);
    }

    public static boolean invertRemoveFogLava() {
        boolean invert = !removeFogLava();
        removeFogLava(invert);
        return invert;
    }

    @Contract(pure = true)
    public static boolean removeFogPowderSnow() {
        return root.get(FIELD_REMOVE_FOG).get(FIELD_REMOVE_FOG_POWDER_SNOW).asBoolean();
    }

    public static void removeFogPowderSnow(boolean removeFogPowderSnow) {
        ((ObjectNode) root.get(FIELD_REMOVE_FOG)).put(FIELD_REMOVE_FOG_POWDER_SNOW, removeFogPowderSnow);
    }

    public static boolean invertRemoveFogPowderSnow() {
        boolean invert = !removeFogPowderSnow();
        removeFogPowderSnow(invert);
        return invert;
    }

    @Contract(pure = true)
    public static boolean removeFogBlindness() {
        return root.get(FIELD_REMOVE_FOG).get(FIELD_REMOVE_FOG_BLINDNESS).asBoolean();
    }

    public static void removeFogBlindness(boolean removeFogBlindness) {
        ((ObjectNode) root.get(FIELD_REMOVE_FOG)).put(FIELD_REMOVE_FOG_BLINDNESS, removeFogBlindness);
    }

    public static boolean invertRemoveFogBlindness() {
        boolean invert = !removeFogBlindness();
        removeFogBlindness(invert);
        return invert;
    }

    @Contract(pure = true)
    public static boolean removeFogDarkness() {
        return root.get(FIELD_REMOVE_FOG).get(FIELD_REMOVE_FOG_DARKNESS).asBoolean();
    }

    public static void removeFogDarkness(boolean removeFogDarkness) {
        ((ObjectNode) root.get(FIELD_REMOVE_FOG)).put(FIELD_REMOVE_FOG_DARKNESS, removeFogDarkness);
    }

    public static boolean invertRemoveFogDarkness() {
        boolean invert = !removeFogDarkness();
        removeFogDarkness(invert);
        return invert;
    }

    @Contract(pure = true)
    public static boolean removeFogWater() {
        return root.get(FIELD_REMOVE_FOG).get(FIELD_REMOVE_FOG_WATER).asBoolean();
    }

    public static void removeFogWater(boolean removeFogWater) {
        ((ObjectNode) root.get(FIELD_REMOVE_FOG)).put(FIELD_REMOVE_FOG_WATER, removeFogWater);
    }

    public static boolean invertRemoveFogWater() {
        boolean invert = !removeFogWater();
        removeFogWater(invert);
        return invert;
    }

    @Contract("-> new")
    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(SmartAirPurifier.MOD_ID + ".json5");
    }
}
