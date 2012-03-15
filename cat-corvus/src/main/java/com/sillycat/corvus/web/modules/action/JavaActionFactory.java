package com.sillycat.corvus.web.modules.action;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.corvus.web.modules.Assembler;
import com.sillycat.corvus.web.modules.JavaBaseFactory;

public class JavaActionFactory extends JavaBaseFactory{

    public Assembler getAssembler(String name) {
        return getAssembler(ControllerConstant.ACTION, name);
    }
}
