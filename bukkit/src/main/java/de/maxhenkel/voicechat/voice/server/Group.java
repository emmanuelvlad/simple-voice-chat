package de.maxhenkel.voicechat.voice.server;

import de.maxhenkel.voicechat.voice.common.ClientGroup;

import javax.annotation.Nullable;
import java.util.UUID;

public class Group {

    private UUID id;
    private String name;
    @Nullable
    private String password;
    private boolean persistent;
    private boolean hidden;
    private de.maxhenkel.voicechat.api.Group.Type type;
    private int priority;
    @Nullable
    private int[][] icon;
    private boolean translatable;

    public Group(UUID id, String name, @Nullable String password, boolean persistent, boolean hidden, de.maxhenkel.voicechat.api.Group.Type type, int priority, @Nullable int[][] icon, boolean translatable) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.persistent = persistent;
        this.hidden = hidden;
        this.type = type;
        this.priority = priority;
        this.icon = icon;
        this.translatable = translatable;
    }

    public Group(UUID id, String name, @Nullable String password, boolean persistent, boolean hidden, de.maxhenkel.voicechat.api.Group.Type type) {
        this(id, name, password, persistent, hidden, type, 0, null, false);
    }

    public Group(UUID id, String name, @Nullable String password, boolean persistent) {
        this(id, name, password, persistent, false, de.maxhenkel.voicechat.api.Group.Type.NORMAL);
    }

    public Group(UUID id, String name, @Nullable String password) {
        this(id, name, password, false);
    }

    public Group(UUID id, String name) {
        this(id, name, null);
    }

    public Group() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public boolean isHidden() {
        return hidden;
    }

    public de.maxhenkel.voicechat.api.Group.Type getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    @Nullable
    public int[][] getIcon() {
        return icon;
    }

    public boolean isTranslatable() {
        return translatable;
    }

    public boolean isOpen() {
        return type == de.maxhenkel.voicechat.api.Group.Type.OPEN;
    }

    public boolean isNormal() {
        return type == de.maxhenkel.voicechat.api.Group.Type.NORMAL;
    }

    public boolean isIsolated() {
        return type == de.maxhenkel.voicechat.api.Group.Type.ISOLATED;
    }

    public ClientGroup toClientGroup() {
        return new ClientGroup(id, name, password != null, persistent, hidden, type, priority, icon, translatable);
    }

}