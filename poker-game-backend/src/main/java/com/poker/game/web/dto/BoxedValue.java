package com.poker.game.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoxedValue<T> {
    private final T value;
}
