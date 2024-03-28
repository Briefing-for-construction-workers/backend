package com.constructionnote.constructionnote.dto.construction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {
    private String clientName;
    private String phoneNumber;

    @Builder
    public Client(String clientName, String phoneNumber) {
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
    }

    public static Client createClient(String clientName, String phoneNumber) {
        return Client.builder()
                .clientName(clientName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
