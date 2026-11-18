package com.sparkvisuals.core;

/** A capability is advertised only when a real, tested provider exists. */
public enum Capability {
    IN_GAME_RENDER,
    FRAME_TIMING,
    INPUT_EVENTS,
    LOCAL_PLAYER_POSITION,
    CAMERA_CONTROL,
    ENTITY_TARGETING,
    PROJECTILE_STATE
}
