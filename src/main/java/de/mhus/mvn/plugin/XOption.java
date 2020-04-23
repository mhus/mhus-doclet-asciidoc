package de.mhus.mvn.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jdk.javadoc.doclet.Doclet.Option;

public class XOption implements Option {

	private int paramCnt;
	private String desc;
	private Kind kind;
	private List<String> names;
    private XOptions consumer;

	public XOption(String name, int paramCnt, String desc) {
		names = Arrays.asList(name.split(","));
		names.replaceAll(e -> "-" + e );
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

    protected void setConsumer(XOptions consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean process(String option, List<String> arguments) {
        if (consumer == null) return false;
        if (paramCnt <= 0) {
            consumer.put(option.substring(1), true);
        } else
        if (paramCnt == 1) {
            consumer.put(option.substring(1), arguments.get(0));
        } else {
            ArrayList<String> list = new ArrayList<String>(paramCnt);
            for (int i = 0; i < paramCnt; i++)
                list.add(arguments.get(i));
            consumer.put(option.substring(1), list);
        }
        return true;
    }

}
