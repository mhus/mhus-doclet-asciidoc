package de.mhus.mvn.plugin;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTreeScanner;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class TestDoclet implements Doclet {

	private DocletEnvironment env;
	private DocTrees treeUtils;

	@Override
	public void init(Locale locale, Reporter reporter) {
		System.out.println("INIT");
	}

	@Override
	public String getName() {
		return "TestDoclet";
	}

	@Override
	public Set<? extends Option> getSupportedOptions() {
        Set<Option> options = new HashSet<>();
        options.add(new XOption("-bottom,-charset,-d,-docencoding,-doctitle,-use,-windowtitle",1,"") {
			@Override
			public boolean process(String option, List<String> arguments) {
				System.out.println("Option: " + option + "=" + arguments);
				return true;
			}
        });
        options.add(new XOption("-version,-author",0,"") {
			@Override
			public boolean process(String option, List<String> arguments) {
				System.out.println("Option: " + option + "=" + arguments);
				return true;
			}
        });
        return options;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_11;
	}

	@Override
	public boolean run(DocletEnvironment environment) {
		System.out.println("RUN");
		env = environment;
		treeUtils = environment.getDocTrees();
		dump("",environment.getSpecifiedElements());
		env = null;
		return true;
	}

	private void dump(String path, Collection<? extends Element> elements) {
		for (Element sub : elements) {
			String p = path + "/" + sub;
			System.out.println(p);
			System.out.println(p + " Modifiers: " + sub.getModifiers());
			System.out.println(p + " Annotation: " + sub.getAnnotationMirrors());
			DocCommentTree dcTree = treeUtils.getDocCommentTree(sub);
			if (dcTree != null) {
				new ShowDocTrees(System.out).scan(dcTree, 1);
			}
			
			dump(p, sub.getEnclosedElements());
		}
	}

	/**
     * A scanner to display the structure of a documentation comment.
     */
    class ShowDocTrees extends DocTreeScanner<Void, Integer> {
        final PrintStream out;
 
        ShowDocTrees(PrintStream out) {
            this.out = out;
        }
 
        @Override
        public Void scan(DocTree t, Integer depth) {
            String indent = "  ".repeat(depth);
            out.println(indent + "# "
                    + t.getKind() + " "
                    + t.toString().replace("\n", "\n" + indent + "##   "));
            return super.scan(t, depth + 1);
        }
    }
    
}
