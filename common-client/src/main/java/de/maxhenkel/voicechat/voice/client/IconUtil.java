package de.maxhenkel.voicechat.voice.client;

import com.mojang.blaze3d.platform.NativeImage;
import de.maxhenkel.voicechat.Voicechat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

public class IconUtil {

    public static NativeImage fromIntArray(int[][] icon) {
        if (icon.length != 16) {
            throw new IllegalStateException("Icon is not 16x16");
        }
        NativeImage nativeImage = new NativeImage(16, 16, true);
        for (int x = 0; x < icon.length; x++) {
            if (icon[x].length != 16) {
                nativeImage.close();
                throw new IllegalStateException("Icon is not 16x16");
            }
            for (int y = 0; y < icon.length; y++) {
                nativeImage.setPixel(x, y, icon[x][y]);
            }
        }
        return nativeImage;
    }

    public static Identifier registerImage(String path, NativeImage image) {
        Identifier identifier = Identifier.fromNamespaceAndPath(Voicechat.MODID, path);
        Minecraft.getInstance().getEntityRenderDispatcher().textureManager.register(identifier, new DynamicTexture(identifier::toString, image));
        return identifier;
    }

    public static void unRegisterImage(Identifier identifier) {
        Minecraft.getInstance().getEntityRenderDispatcher().textureManager.release(identifier);
    }

}

