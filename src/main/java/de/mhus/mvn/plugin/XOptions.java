package de.mhus.mvn.plugin;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import jdk.javadoc.doclet.Doclet.Option;

public class XOptions extends Properties {

    private static final long serialVersionUID = 1L;
    private Set<Option> options = new HashSet<>();

    public void add(XOption option) {
        option.setConsumer(this);
        options.add(option);
    }

    public Set<? extends Option> getOptions() {
        return options;
    }
  
    
}
