package de.mhus.mvn.plugin;

import javax.annotation.processing.SupportedOptions;

/**
 * Before Class.
 * 
 * @category category
 * @see TestDocklet
 * @author mikehummel
 *
 */
@SupportedOptions("options")
public class Example {

	/**
	 * This is a Parameter {@link java.lang.String}.
	 */
	public String parameter;
	
	/**
	 * Constructor
	 * 
	 * New Line
	 * 
	 * {@code 
	 * Code
	 * } 
	 * {@docRoot docRoot} 
	 * {@inheritDoc inheritDoc} 
	 * {@linkplain link plain} 
	 * {@link link} 
	 * {@literal literal} 
	 * {@value value}
	 * 
	 * @param in Constructor Parameter
	 * @exception Exception
	 */
	public Example(String in) throws Exception {
		parameter = in;
	}

	/**
	 * Parameter Getter
	 * @return Current Parameter Value
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Parameter Setter
	 * @param parameter New Parameter Value
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	/**
	 * Inner Class
	 * 
	 * @author mikehummel
	 *
	 */
	public static class InnerClass {
		
		/**
		 * Inner Parameter
		 */
		private String inner;

		/**
		 * Inner Getter
		 * @return Current Inner Value
		 */
		public String getInner() {
			return inner;
		}

		/**
		 * Inner Setter
		 * 
		 * Second Line
		 * 
		 * @param inner New Inner value
		 */
		public void setInner(String inner) {
			this.inner = inner;
		}
	}
}
