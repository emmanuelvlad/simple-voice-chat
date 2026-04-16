package de.maxhenkel.voicechat.voice.client;

import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.gui.volume.AdjustVolumeList;
import de.maxhenkel.voicechat.intercompatibility.ClientCompatibilityManager;
import de.maxhenkel.voicechat.intercompatibility.CommonCompatibilityManager;
import de.maxhenkel.voicechat.net.ClientServerNetManager;
import de.maxhenkel.voicechat.plugins.CategoryManager;
import de.maxhenkel.voicechat.plugins.impl.VolumeCategoryImpl;
import net.minecraft.resources.Identifier;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCategoryManager extends CategoryManager {

    protected final Map<String, Identifier> images;

    public ClientCategoryManager() {
        images = new ConcurrentHashMap<>();
        ClientServerNetManager.setClientListener(CommonCompatibilityManager.INSTANCE.getNetManager().addCategoryChannel, (player, packet) -> {
            addCategory(packet.getCategory());
            Voicechat.LOGGER.debug("Added category {}", packet.getCategory().getId());
        });
        ClientServerNetManager.setClientListener(CommonCompatibilityManager.INSTANCE.getNetManager().removeCategoryChannel, (player, packet) -> {
            removeCategory(packet.getCategoryId());
            Voicechat.LOGGER.debug("Removed category {}", packet.getCategoryId());
        });
        ClientCompatibilityManager.INSTANCE.onDisconnect(this::clear);
    }

    @Override
    public void addCategory(VolumeCategoryImpl category) {
        super.addCategory(category);

        if (category.getIcon() != null) {
            Identifier identifier = IconUtil.registerImage(category.getId(), IconUtil.fromIntArray(category.getIcon()));
            images.put(category.getId(), identifier);
        }
        AdjustVolumeList.update();
    }

    @Override
    @Nullable
    public VolumeCategoryImpl removeCategory(String categoryId) {
        VolumeCategoryImpl volumeCategory = super.removeCategory(categoryId);
        Identifier identifier = images.remove(categoryId);
        if (identifier != null) {
            IconUtil.unRegisterImage(identifier);
        }
        AdjustVolumeList.update();
        return volumeCategory;
    }

    public void clear() {
        images.values().forEach(IconUtil::unRegisterImage);
        images.clear();
        categories.clear();
    }

    public Identifier getTexture(String id, Identifier defaultImage) {
        return images.getOrDefault(id, defaultImage);
    }

}
