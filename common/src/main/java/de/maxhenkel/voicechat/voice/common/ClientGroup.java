package de.maxhenkel.voicechat.voice.common;

import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.plugins.impl.GroupImpl;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class ClientGroup {

    private final UUID id;
    private final String name;
    private final boolean hasPassword;
    private final boolean persistent;
    private final boolean hidden;
    private final de.maxhenkel.voicechat.api.Group.Type type;
    private final int priority;
    @Nullable
    private final int[][] icon;

    public ClientGroup(UUID id, String name, boolean hasPassword, boolean persistent, boolean hidden, de.maxhenkel.voicechat.api.Group.Type type, int priority, @Nullable int[][] icon) {
        this.id = id;
        this.name = name;
        this.hasPassword = hasPassword;
        this.persistent = persistent;
        this.hidden = hidden;
        this.type = type;
        this.priority = priority;
        this.icon = icon;
    }

    public ClientGroup(UUID id, String name, boolean hasPassword, boolean persistent, boolean hidden, de.maxhenkel.voicechat.api.Group.Type type) {
        this(id, name, hasPassword, persistent, hidden, type, 0, null);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasPassword() {
        return hasPassword;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Group.Type getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    @Nullable
    public int[][] getIcon() {
        return icon;
    }

    public static ClientGroup fromBytes(FriendlyByteBuf buf) {
        UUID id = buf.readUUID();
        String name = buf.readUtf(512);
        boolean hasPassword = buf.readBoolean();
        boolean persistent = buf.readBoolean();
        boolean hidden = buf.readBoolean();
        Group.Type type = GroupImpl.TypeImpl.fromInt(buf.readShort());
        int priority = buf.isReadable(4) ? buf.readInt() : 0;
        int[][] icon = null;
        if (buf.isReadable(1) && buf.readBoolean()) {
            icon = new int[16][16];
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    icon[x][y] = buf.readInt();
                }
            }
        }
        return new ClientGroup(id, name, hasPassword, persistent, hidden, type, priority, icon);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(id);
        buf.writeUtf(name, 512);
        buf.writeBoolean(hasPassword);
        buf.writeBoolean(persistent);
        buf.writeBoolean(hidden);
        buf.writeShort(GroupImpl.TypeImpl.toInt(type));
        buf.writeInt(priority);
        buf.writeBoolean(icon != null);
        if (icon != null) {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    buf.writeInt(icon[x][y]);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientGroup group = (ClientGroup) o;

        return Objects.equals(id, group.id);
    }
}
