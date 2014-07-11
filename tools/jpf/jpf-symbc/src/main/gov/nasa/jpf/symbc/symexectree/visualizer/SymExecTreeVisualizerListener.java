/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import java.util.LinkedList;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.symbc.symexectree.ASymbolicExecutionTreeListener;
import gov.nasa.jpf.symbc.symexectree.DefaultNodeFactory;
import gov.nasa.jpf.symbc.symexectree.NodeFactory;
import gov.nasa.jpf.symbc.symexectree.structure.SymbolicExecutionTree;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class SymExecTreeVisualizerListener extends ASymbolicExecutionTreeListener {

	private String dotFileOutputPath;
	private AVisualizer dotPrinter;
	/**
	 * symbolic.visualiser.outputformat = [png|eps|dot|pdf|ps|svg]		(default: dot)
	 * symbolic.visualizer.basepath = <output_path>
	 */
	public SymExecTreeVisualizerListener(Config conf, JPF jpf	) {
		super(conf, jpf);
		this.dotFileOutputPath = conf.getString("symbolic.visualizer.basepath");
		if(this.dotFileOutputPath == null)
			throw new SymExecTreeVisualizerException("symbolic.visualizer.basepath has not been set");
		String outputFormat = conf.getString("symbolic.visualizer.outputformat", "dot");
		OUTPUT_FORMAT format = OUTPUT_FORMAT.valueOf(outputFormat.toUpperCase());
		this.dotPrinter = new CompleteTreeVisualizer(format);
	}

	@Override
	protected NodeFactory getNodeFactory() {
		return new DefaultNodeFactory();
	}

	@Override
	protected void processSymbExecTree(
			LinkedList<SymbolicExecutionTree> trees) {
		this.dotPrinter.prettyPrintSymTrees(trees, this.dotFileOutputPath);
	}
}
