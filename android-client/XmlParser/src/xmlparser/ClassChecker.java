package xmlparser;

import java.lang.reflect.Type;

public class ClassChecker {
	
	/**
	 * Проверка на то, что <b>checkedType</b> реализует <b>interfaceType</b>
	 * */
	static public boolean CheckInterface(Class<?> checkedType, Class<?> interfaceType) {
		Type[] interfaces = checkedType.getGenericInterfaces();
		String interfName = interfaceType.getName();
		
		boolean gotit = false;
		for (Type i:interfaces) {
			if (i.toString().equals("interface "+interfName)) {
				gotit = true;
				break;
			}
		}
		return gotit;
	}
	/**
	 * Проверка на то, что <b>checkedType</b> наследован от <b>baseType</b>
	 * */
	static public boolean CheckInheritance(Class<?> checkedType, Class<?> baseType) {
		Type base = checkedType;
		String baseName = baseType.getName();
		String checkedName;
		
		boolean gotit = false;
		do {
			checkedName = base.toString();
			if (checkedName.equals("class "+baseName)){
				gotit = true;
				break;
			}
			else {
				base = ((Class<?>) base).getGenericSuperclass();
				if (base == null)
					break;
			}
		} while (!gotit);
	
		return gotit;
	}

}
