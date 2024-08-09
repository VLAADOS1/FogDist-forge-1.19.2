package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChatEvent {

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event) {
        String message = event.getMessage().trim();
        String[] parts = message.split(" ");

        if (parts[0].equalsIgnoreCase(".fog")) {
            event.setCanceled(true);

            if (parts.length == 2) {
                try {
                    int intensity = Integer.parseInt(parts[1]);

                    if (intensity < 1 || intensity > 150) {
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Введите число от 1 до 150."));
                        return;
                    }

                    float fogStart = 300.0f / (intensity * 0.2f);
                    float fogEnd = 600.0f / (intensity * 0.2f);

                    if (intensity > 100) {
                        fogStart *= (1.5f - (intensity - 50) * 0.01f);
                        fogEnd *= (1.5f - (intensity - 50) * 0.01f);
                    }

                    FogConfig config = FogConfig.loadConfig();
                    config.setFogStart(fogStart);
                    config.setFogEnd(fogEnd);
                    config.saveConfig();

                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Интенсивность тумана установлена на " + intensity + ". Начало = " + fogStart + ", Конец = " + fogEnd));

                } catch (NumberFormatException e) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Неверный формат числа. Введите корректное целое число."));
                }
            } else {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Использование: .fog <интенсивность> (1-150)"));
            }
        } else if (parts[0].equalsIgnoreCase(".fogtype")) {
            event.setCanceled(true);

            if (parts.length == 2) {
                try {
                    int type = Integer.parseInt(parts[1]);

                    FogConfig.FogShapeType shapeType;
                    if (type == 1) {
                        shapeType = FogConfig.FogShapeType.CYLINDER;
                    } else if (type == 2) {
                        shapeType = FogConfig.FogShapeType.SPHERE;
                    } else {
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Введите 1 для CYLINDER или 2 для SPHERE."));
                        return;
                    }

                    FogConfig config = FogConfig.loadConfig();
                    config.setFogShape(shapeType);
                    config.saveConfig();

                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Тип тумана установлен на " + (shapeType == FogConfig.FogShapeType.CYLINDER ? "CYLINDER" : "SPHERE")));

                } catch (NumberFormatException e) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Неверный формат числа. Введите корректное значение."));
                }
            } else {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Использование: .fogtype <тип> (1 - CYLINDER, 2 - SPHERE)"));
            }
        } else if (parts[0].equalsIgnoreCase(".foghelp")) {
            event.setCanceled(true);

            String helpMessage = "Команды тумана:\n"
                    + ".fog <интенсивность> (1-150) - Устанавливает интенсивность тумана. Чем больше число, тем ближе туман.\n"
                    + ".fogtype <тип> (1 - CYLINDER, 2 - SPHERE) - Устанавливает тип тумана. 1 для CYLINDER, 2 для SPHERE.\n"
                    + ".foghelp - Показывает информацию о командах.";

            Minecraft.getInstance().player.sendSystemMessage(Component.literal(helpMessage));
        }
    }
}
