package com.example.examplemod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FogConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(Minecraft.getInstance().gameDirectory, "config/fog_config.json");

    private float fogStart = 100.0f;
    private float fogEnd = 150.0f;

    @SerializedName("fog_shape")
    private FogShapeType fogShape = FogShapeType.CYLINDER;

    public enum FogShapeType {
        CYLINDER,
        SPHERE
    }

    public static FogConfig loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, FogConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FogConfig config = new FogConfig();
            config.saveConfig();
            return config;
        }
        return new FogConfig();
    }

    public void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getFogStart() {
        return fogStart;
    }

    public void setFogStart(float fogStart) {
        this.fogStart = fogStart;
    }

    public float getFogEnd() {
        return fogEnd;
    }

    public void setFogEnd(float fogEnd) {
        this.fogEnd = fogEnd;
    }

    public FogShape getFogShape() {
        return fogShape == FogShapeType.CYLINDER ? FogShape.CYLINDER : FogShape.SPHERE;
    }

    public void setFogShape(FogShapeType fogShape) {
        this.fogShape = fogShape;
    }
}