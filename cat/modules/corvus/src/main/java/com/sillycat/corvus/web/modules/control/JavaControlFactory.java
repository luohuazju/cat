package com.sillycat.corvus.web.modules.control;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.corvus.web.modules.Assembler;
import com.sillycat.corvus.web.modules.JavaBaseFactory;

public class JavaControlFactory extends JavaBaseFactory{

    public Assembler getAssembler(String name) {
        return getAssembler(ControllerConstant.CONTORL, name);
    }
}

