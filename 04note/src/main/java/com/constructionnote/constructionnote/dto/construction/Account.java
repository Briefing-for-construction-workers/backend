package com.constructionnote.constructionnote.dto.construction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    private int cost;
    private int income;
    private boolean moneyGiven;
    private boolean moneyReceived;

    @Builder
    public Account(int cost, int income, boolean moneyGiven, boolean moneyReceived) {
        this.cost = cost;
        this.income = income;
        this.moneyGiven = moneyGiven;
        this.moneyReceived = moneyReceived;
    }

    public static Account createAccount(int cost, int income, boolean moneyGiven, boolean moneyReceived) {
        return Account.builder()
                .cost(cost)
                .income(income)
                .moneyGiven(moneyGiven)
                .moneyReceived(moneyReceived)
                .build();
    }
}
