package de.maxhenkel.voicechat.voice.client;

import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.gui.group.JoinGroupList;
import de.maxhenkel.voicechat.intercompatibility.ClientCompatibilityManager;
import de.maxhenkel.voicechat.intercompatibility.CommonCompatibilityManager;
import de.maxhenkel.voicechat.net.ClientServerNetManager;
import de.maxhenkel.voicechat.voice.common.ClientGroup;
import net.minecraft.resources.Identifier;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientGroupManager {

    private final Map<UUID, ClientGroup> groups;
    private final Map<UUID, Identifier> groupIcons;

    public ClientGroupManager() {
        groups = new ConcurrentHashMap<>();
        groupIcons = new ConcurrentHashMap<>();
        ClientServerNetManager.setClientListener(CommonCompatibilityManager.INSTANCE.getNetManager().addGroupChannel, (player, packet) -> {
            ClientGroup group = packet.getGroup();
            groups.put(group.getId(), group);
            if (group.getIcon() != null) {
                Identifier identifier = IconUtil.registerImage("group_icon/" + group.getId().toString(), IconUtil.fromIntArray(group.getIcon()));
                groupIcons.put(group.getId(), identifier);
            }
            Voicechat.LOGGER.debug("Added group '{}' ({})", group.getName(), group.getId());
            JoinGroupList.update();
        });
        ClientServerNetManager.setClientListener(CommonCompatibilityManager.INSTANCE.getNetManager().removeGroupChannel, (player, packet) -> {
            groups.remove(packet.getGroupId());
            Identifier identifier = groupIcons.remove(packet.getGroupId());
            if (identifier != null) {
                IconUtil.unRegisterImage(identifier);
            }
            Voicechat.LOGGER.debug("Removed group {}", packet.getGroupId());
            JoinGroupList.update();
        });
        ClientCompatibilityManager.INSTANCE.onDisconnect(this::clear);
    }

    @Nullable
    public Identifier getGroupIconTexture(UUID groupId) {
        return groupIcons.get(groupId);
    }

    @Nullable
    public ClientGroup getGroup(UUID id) {
        return groups.get(id);
    }

    public Collection<ClientGroup> getGroups() {
        return groups.values();
    }

    public void clear() {
        groupIcons.values().forEach(IconUtil::unRegisterImage);
        groupIcons.clear();
        groups.clear();
    }

}
