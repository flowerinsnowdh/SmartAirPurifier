package cn.flowerinsnow.smartairpurifier.screen;

import cn.flowerinsnow.smartairpurifier.config.Config;
import cn.flowerinsnow.smartairpurifier.lang.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class ConfigScreen extends Screen {
    private final Screen parent;

    public ConfigScreen() {
        this(null);
    }

    public ConfigScreen(Screen parent) {
        super(Component.translatable(TranslationKeys.SCREEN_CONFIG_TITLE));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int textWidth = this.font.width(this.title);
        this.addRenderableWidget(
                new StringWidget(this.width / 2 - textWidth / 2, 10, textWidth, 9, this.title, this.font)
        );

        this.addRenderableWidget(
                Button.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_LAVA, Config.removeFogLava()),button -> {
                            boolean invert = Config.invertRemoveFogLava();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_LAVA, invert));
                        }
                )
                        .pos(this.width / 2 - 200, this.height / 2 - 25)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_WATER, Config.removeFogWater()), button -> {
                            boolean invert = Config.invertRemoveFogWater();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_WATER, invert));
                        }
                )
                        .pos(this.width / 2, this.height / 2 - 25)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_BLINDNESS, Config.removeFogBlindness()), button -> {
                            boolean invert = Config.invertRemoveFogBlindness();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_BLINDNESS, invert));
                        }
                )
                        .pos(this.width / 2 - 200, this.height / 2)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_DARKNESS, Config.removeFogDarkness()), button -> {
                            boolean invert = Config.invertRemoveFogDarkness();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_DARKNESS, invert));
                        }
                )
                        .pos(this.width / 2, this.height / 2)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_POWDER_SNOW, Config.removeFogPowderSnow()), button -> {
                            boolean invert = Config.invertRemoveFogPowderSnow();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_POWDER_SNOW, invert));
                        }
                )
                        .pos(this.width / 2 - 200, this.height / 2 + 25)
                        .size(200, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_DONE, Config.removeFogPowderSnow()), button ->
                            Minecraft.getInstance().setScreen(ConfigScreen.this.parent)
                )
                        .pos(this.width / 2, this.height / 2 + 25)
                        .size(200, 20)
                        .build()
        );
    }

    private static Component trueFalseConstantComponent(boolean value) {
        return value ? Component.translatable(TranslationKeys.CONSTANT_TRUE).withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)) : Component.translatable(TranslationKeys.CONSTANT_FALSE).withStyle(Style.EMPTY.withColor(ChatFormatting.RED));
    }

    private static Component trueFalseConstantComponent(String translationKey, boolean value) {
        return Component.translatable(translationKey, trueFalseConstantComponent(value));
    }
}
