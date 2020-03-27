package com.da.learn.learnboot.circledepedency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonB {

    @Autowired
    PersonA personA;
}
