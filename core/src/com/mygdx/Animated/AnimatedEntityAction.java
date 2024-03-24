package com.mygdx.Animated;

@FunctionalInterface
public interface AnimatedEntityAction {
    long execute(AnimatedEntity entity, long animationTime, long time);
}