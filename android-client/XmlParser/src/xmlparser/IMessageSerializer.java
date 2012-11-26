/**
 * 
 */
package xmlparser;

import org.w3c.dom.Node;

/**
 * Базовый интерфейс для сериализуемых сообщений
 * @author Theurgist
 */
public interface IMessageSerializer {
	public boolean serialize(Node node);
}
