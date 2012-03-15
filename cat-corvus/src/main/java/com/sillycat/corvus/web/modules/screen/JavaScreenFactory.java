package com.sillycat.corvus.web.modules.screen;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.corvus.web.modules.Assembler;
import com.sillycat.corvus.web.modules.JavaBaseFactory;

public class JavaScreenFactory extends JavaBaseFactory{

    public Assembler getAssembler(String name) {
        return getAssembler(ControllerConstant.SCREEN, name);
    }
}
