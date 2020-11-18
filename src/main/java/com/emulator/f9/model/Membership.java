package com.emulator.f9.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Membership {
    @Getter
    @Setter
    String usrId;

    @Getter
    @Setter
    String password;

    @Getter
    String credential = "T1BVUzM2NS1jbGllbnQ6T1BVUzM2NS1zZWNyZXQ=";

    @Getter
    @Setter
    String access_token;
}
