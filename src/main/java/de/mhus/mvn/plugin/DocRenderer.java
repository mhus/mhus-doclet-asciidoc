package de.mhus.mvn.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DocRenderer {

    private String outputDir;
    private XOptions options;

    public DocRenderer(XOptions options) {
        this.options = options;
    }

    /**
     * Exports a javadoc comment using a given link:PrintWriter[], surrounding
     * it by a AsciiDoc tag with a specific name.
     *
     * @param tag the name of the tag to surround the javadoc comment into the AsciiDoc file
     * @param comment the javadoc comment to export
     * @param writer the link:PrintWriter[] to be used to export the javadoc comment to an AsciiDoc file
     */
    private void outputText(String tag, String comment, PrintWriter writer) {
        writer.println("// tag::" + tag + "[]");
        writer.println(cleanJavadocInput(comment));
        writer.println("// end::" + tag + "[]");
    }

    private String cleanJavadocInput(String input) {
        return input.trim()
                .replaceAll("\n ", "\n") // Newline space to accommodate javadoc newlines.
                .replaceAll("(?m)^( *)\\*\\\\/$", "$1*/"); // Multi-line comment end tag is translated into */.
    }
    
    /**
     * Gets a link:PrintWriter[] to export the documentation of a class or package
     * to an AsciiDoc file.
     *
     * @param packageDoc the package documentation object that will be the package that the documentation
     *                   is being exported or the package of the class that its documentation
     *                   is being exported
     * @param name the name of the AsciiDoc file to export the documentation to
     */
    private PrintWriter getWriter(String packageDoc, String name) throws FileNotFoundException {
        File packageDirectory = new File(getOutputDir() + packageDoc.replace('.', File.separatorChar));
        if(!packageDirectory.exists() && !packageDirectory.mkdirs()){
            throw new RuntimeException("The directory was not created due to unknown reason.");
        }

        File file = new File(packageDirectory, name + ".adoc");
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }

    /**
     * Gets the output directory passed as a command line argument to javadoc tool.
     * @return the output directory to export the javadocs
     */
    private String getOutputDir() {
            return includeTrailingDirSeparator(outputDir);
    }

    /**
     * Adds a trailing slash at the end of a path if it doesn't have one yet.
     * The trailing slash type is system-dependent and will be accordingly selected.
     *
     * @param path the path to include a trailing slash
     * @return the path with a trailing slash if there wasn't one and the path is not empty,
     * the original path otherwise
     */
    private String includeTrailingDirSeparator(String path){
        if(path.trim().isEmpty()) {
            return path;
        }

        if(path.charAt(path.length()-1) != File.separatorChar){
            return path + File.separator;
        }

        return path;
    }
    
}
