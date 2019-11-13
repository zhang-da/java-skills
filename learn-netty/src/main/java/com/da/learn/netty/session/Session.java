package com.da.learn.netty.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private String userId;
    private String userName;


    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}
