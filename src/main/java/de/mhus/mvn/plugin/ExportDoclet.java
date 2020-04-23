package de.mhus.mvn.plugin;

import java.io.PrintStream;
import java.util.Collection;
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

public class ExportDoclet implements Doclet {

	private DocTrees treeUtils;
    private XOptions options;
    private DocRenderer renderer;

	@Override
	public void init(Locale locale, Reporter reporter) {
        options = new XOptions();
        options.add(new XOption("bottom", 1, "Specifies the text to be placed at the bottom of each output file."));
        options.add(new XOption("charset", 1, "Specifies the HTML character set for this document."));
        options.add(new XOption("d", 1, "Specifies the destination directory where javadoc saves the generated HTML files."));
        options.add(new XOption("docencoding", 1, "Specifies the encoding of the generated HTML files."));
        options.add(new XOption("doctitle", 1, "Specifies the title to be placed near the top of the overview summary file."));
        options.add(new XOption("use", 1, "Includes one \"Use\" page for each documented class and package."));
        options.add(new XOption("windowtitle", 1, "Specifies the title to be placed in the HTML <title> tag."));
        options.add(new XOption("version", 0, "Adds a \"Version\" subheading with the specified version-text to the generated docs when the -version option is used."));
        options.add(new XOption("author", 0, "Adds an \"Author\" entry with the specified name-text to the generated docs when the -author option is used."));
		
	}

	@Override
	public String getName() {
		return "Export AsciiDoc Doclet";
	}

	@Override
	public Set<? extends Option> getSupportedOptions() {
        return options.getOptions();
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_11;
	}

	@Override
	public boolean run(DocletEnvironment environment) {
		System.out.println("RUN");
		treeUtils = environment.getDocTrees();
		
		renderer = new DocRenderer(options);
		
		dump("",environment.getSpecifiedElements());
		renderer = null;
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
