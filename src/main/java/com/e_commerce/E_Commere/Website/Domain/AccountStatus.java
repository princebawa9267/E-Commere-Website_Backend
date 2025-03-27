package com.e_commerce.E_Commere.Website.Domain;

public enum AccountStatus {
    PENDING_VERIFICATON,  //Account is created but not yet verified
    ACTIVE,               //Account is active and is good standing
    SUSPENDED,            //Account is temporary suspended, possibly due to violations
    DEACTIVATED,          //Account is deactivated, user may have chosen to deactivated
    BANNED,               //Account is permanently banned due to serve violations
    CLOSED                //Account is permanently closed, possibly at user request
}
