package com.auction.flab.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStatus {

    PROPOSAL("P"),
    CONFIRMATION("C");

    private String status;

}
