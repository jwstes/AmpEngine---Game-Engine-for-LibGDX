package com.mygdx.game;

@FunctionalInterface
public interface AnimatedEntityAction {
    long execute(AnimatedEntity entity, long animationTime, long time);
}