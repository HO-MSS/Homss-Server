package com.homss.server.common.modle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeModel {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
