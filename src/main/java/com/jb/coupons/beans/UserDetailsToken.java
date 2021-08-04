package com.jb.coupons.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsToken {
    public String email;
    public String token;
    public ClientType clientType;
}
