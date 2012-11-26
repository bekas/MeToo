/**
 * 
 */
package xmlparser;

import org.w3c.dom.Node;

/**
 * @author theurgist
 *
 */
public interface INodeSerializer {
	public boolean serialize(Node node, PageParser parser);
}
