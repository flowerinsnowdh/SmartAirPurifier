package cn.flowerinsnow.smartairpurifier.screen;

import cn.flowerinsnow.smartairpurifier.config.Config;
import cn.flowerinsnow.smartairpurifier.lang.TranslationKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ConfigScreen extends Screen {
    private final Screen parent;

    public ConfigScreen() {
        this(null);
    }

    public ConfigScreen(Screen parent) {
        super(Text.translatable("smart-air-purifier.screen.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int textWidth = this.textRenderer.getWidth(this.title);
        this.addDrawableChild(
                new TextWidget(this.width / 2 - textWidth / 2, 10, textWidth, 9, this.title, this.textRenderer)
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_LAVA, Config.removeFogLava()),button -> {
                            boolean invert = Config.invertRemoveFogLava();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_LAVA, invert));
                        }
                )
                        .position(this.width / 2 - 200, this.height / 2 - 25)
                        .size(200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_WATER, Config.removeFogWater()), button -> {
                            boolean invert = Config.invertRemoveFogWater();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_WATER, invert));
                        }
                )
                        .position(this.width / 2, this.height / 2 - 25)
                        .size(200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_BLINDNESS, Config.removeFogBlindness()), button -> {
                            boolean invert = Config.invertRemoveFogBlindness();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_BLINDNESS, invert));
                        }
                )
                        .position(this.width / 2 - 200, this.height / 2)
                        .size(200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_DARKNESS, Config.removeFogDarkness()), button -> {
                            boolean invert = Config.invertRemoveFogDarkness();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_DARKNESS, invert));
                        }
                )
                        .position(this.width / 2, this.height / 2)
                        .size(200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_POWDER_SNOW, Config.removeFogPowderSnow()), button -> {
                            boolean invert = Config.invertRemoveFogPowderSnow();
                            Config.save();
                            button.setMessage(trueFalseConstantComponent(TranslationKeys.SCREEN_CONFIG_REMOVE_FOG_POWDER_SNOW, invert));
                        }
                )
                        .position(this.width / 2 - 200, this.height / 2 + 25)
                        .size(200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        trueFalseConstantComponent(TranslationKeys.SCREEN_DONE, Config.removeFogPowderSnow()), button ->
                            MinecraftClient.getInstance().setScreen(ConfigScreen.this.parent)
                )
                        .position(this.width / 2, this.height / 2 + 25)
                        .size(200, 20)
                        .build()
        );
    }

    private static Text trueFalseConstantComponent(boolean value) {
        return value ? Text.translatable(TranslationKeys.CONSTANT_TRUE).setStyle(Style.EMPTY.withColor(Formatting.GREEN)) : Text.translatable(TranslationKeys.CONSTANT_FALSE).setStyle(Style.EMPTY.withColor(Formatting.RED));
    }

    private static Text trueFalseConstantComponent(String translationKey, boolean value) {
        return Text.translatable(translationKey, trueFalseConstantComponent(value));
    }
}
