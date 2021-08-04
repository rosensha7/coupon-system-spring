package com.jb.coupons.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDetails {
    public String id;
    public String userEmail;
    public String password;
    public ClientType clientType;

}
