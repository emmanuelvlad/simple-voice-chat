package de.maxhenkel.voicechat.api;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Groups can be created using {@link VoicechatServerApi#groupBuilder()}.
 */
public interface Group {

    /**
     * @return the visual name of the group
     */
    String getName();

    /**
     * @return if the group has a password
     */
    boolean hasPassword();

    /**
     * @return the ID of the group
     */
    UUID getId();

    /**
     * @return if the group is persistent
     */
    boolean isPersistent();

    /**
     * @return if the group is hidden in the group list UI
     */
    boolean isHidden();

    /**
     * @return the group type
     */
    Type getType();

    /**
     * Groups with higher priority are displayed first in the group list.
     * Default priority is <code>0</code>.
     *
     * @return the display priority of the group
     */
    int getPriority();

    /**
     * A 16x16 icon to display next to the group name in the group list.
     * The array is indexed as [x][y] where each value is an ABGR color int.
     *
     * @return the 16x16 icon pixel array, or <code>null</code> if no custom icon
     */
    @Nullable
    int[][] getIcon();

    public interface Type {

        /**
         * Players in a group can hear nearby players that are not in a group
         */
        public static final Type NORMAL = new Type() {
        };

        /**
         * Players in a group can hear nearby players and nearby players can hear players in the group
         */
        public static final Type OPEN = new Type() {
        };

        /**
         * Players in a group can only hear other players in the group
         */
        public static final Type ISOLATED = new Type() {
        };
    }

    public interface Builder {

        /**
         * Sets the ID of the group.
         * If this is not set, the ID will be randomly generated.
         * <br/>
         * <b>NOTE</b>: If there is already a group with the same ID, the group will be overwritten.
         *
         * @param id the group ID or <code>null</code> if the ID should be randomly generated
         * @return the builder
         */
        Builder setId(@Nullable UUID id);

        /**
         * <b>NOTE</b>: The name might be stripped of special characters and whitespace.
         *
         * @param name the name of the group
         * @return the builder
         */
        Builder setName(String name);

        /**
         * @param password the group password
         * @return the builder
         */
        Builder setPassword(@Nullable String password);

        /**
         * @param persistent if the group should be persistent
         * @return the builder
         */
        Builder setPersistent(boolean persistent);

        /**
         * @param hidden if the group should be hidden in the group list UI
         * @return the builder
         */
        Builder setHidden(boolean hidden);

        /**
         * @param type the group type
         * @return the builder
         */
        Builder setType(Type type);

        /**
         * Sets the display priority of the group.
         * Groups with higher priority are displayed first in the group list.
         * Default is <code>0</code>.
         *
         * @param priority the display priority
         * @return the builder
         */
        Builder setPriority(int priority);

        /**
         * Sets a custom 16x16 icon to display next to the group name.
         * The array should be indexed as [x][y] where each value is an ABGR color int.
         *
         * @param icon the 16x16 icon pixel array, or <code>null</code> for no custom icon
         * @return the builder
         */
        Builder setIcon(@Nullable int[][] icon);

        /**
         * @return the built group
         * @throws IllegalStateException if the name is not set or invalid
         */
        Group build();

    }

}
