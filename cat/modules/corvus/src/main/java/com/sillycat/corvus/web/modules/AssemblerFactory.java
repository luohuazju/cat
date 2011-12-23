package com.sillycat.corvus.web.modules;


public interface AssemblerFactory {
	
	 Assembler getAssembler(String name) throws Exception;

}
