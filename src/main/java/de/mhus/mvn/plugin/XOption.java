package de.mhus.mvn.plugin;

import java.util.Arrays;
import java.util.List;

import jdk.javadoc.doclet.Doclet.Option;

public abstract class XOption implements Option {

	private int paramCnt;
	private String desc;
	private Kind kind;
	private List<String> names;

	public XOption(String name, int paramCnt, String desc) {
		names = Arrays.asList(name.split(","));
		this.paramCnt= paramCnt;
		this.desc = desc;
		this.kind = Kind.STANDARD;
	}
	
	@Override
	public int getArgumentCount() {
		return paramCnt;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public List<String> getNames() {
		return names;
	}

	@Override
	public String getParameters() {
		return "";
	}

}
