package com.da.learn.learnboot.circledepedency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonA {

    @Autowired
    PersonB personB;

    public PersonB getPersonB() {
        return personB;
    }
}
