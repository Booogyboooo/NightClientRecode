package io.booogyboooo.nightclient.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.booogyboooo.nightclient.event.annotation.Event;

import java.util.AbstractMap;
import java.util.Comparator;

class Sort implements Comparator<Map.Entry<RegisteredMethod, Event>> {
	public int compare(Map.Entry<RegisteredMethod, Event> o1, Map.Entry<RegisteredMethod, Event> o2) {
		int p1 = o1.getValue().priority();
		int p2 = o2.getValue().priority();
		if (p1 > p2)
			return -1;
		if (p1 < p2)
			return 1;
		return 0;
	}
}

class RegisteredMethod {
	Object target;
	Method method;

	RegisteredMethod(Object target, Method method) {
		this.target = target;
		this.method = method;
	}

	static boolean matches(RegisteredMethod e, Object target, Method method) {
		if (e.target.equals(target) && e.method.equals(method)) {
			return true;
		}
		return false;
	}
}

public class EventManager {
	Map<EventType, List<RegisteredMethod>> registeredMethods = new HashMap<EventType, List<RegisteredMethod>>();

	public void init() {
		for (EventType type : EventType.values()) {
			registeredMethods.put(type, new ArrayList<RegisteredMethod>());
		}
	}

	public void registerEvent(Object target, String methodName) {
		try {
			Method method = target.getClass().getDeclaredMethod(methodName, EventData.class);
			if (!method.isAnnotationPresent(Event.class)) {
				System.out.println(methodName + " does not have @Event");
				return;
			}
			if (method.getReturnType() != void.class) {
				System.out.println(methodName + " has to return 'void'");
				return;
			}
			if (!Arrays.stream(method.getParameterTypes()).anyMatch(t -> t.equals(EventData.class))) {
				System.out.println(methodName + " has to have an 'EventData' parameter");
				return;
			}
			Event annotation = method.getAnnotation(Event.class);
			EventType type = annotation.eventType();
			List<RegisteredMethod> methods = registeredMethods.get(type);
			methods.add(new RegisteredMethod(target, method));
			registeredMethods.put(type, methods);
		} catch (NoSuchMethodException e) {
			System.out.println("Method not found: " + methodName + "(EventData data)");
		}
	}

	public void unregisterEvent(Object target, String methodName) {
		try {
			Method method = target.getClass().getDeclaredMethod(methodName, EventData.class);
			if (!method.isAnnotationPresent(Event.class)) {
				System.out.println(methodName + " is not an event");
			}
			Event annotation = method.getAnnotation(Event.class);
			EventType type = annotation.eventType();
			List<RegisteredMethod> methods = registeredMethods.get(type);
			RegisteredMethod temp = null;
			for (RegisteredMethod e : methods) {
				if (RegisteredMethod.matches(e, target, method)) {
					temp = e;
				}
			}
			if (temp != null) {
				methods.remove(temp);
			} else {
				System.out.println("Method not registered!");
			}
			registeredMethods.put(type, methods);
		} catch (NoSuchMethodException e) {
			System.out.println("Method not found: " + methodName + "(EventData data)");
		}
	}

	public void fireEvent(EventType event, EventData data) throws Exception {
		List<Map.Entry<RegisteredMethod, Event>> methods = new ArrayList<Map.Entry<RegisteredMethod, Event>>();
		for (RegisteredMethod e : registeredMethods.get(event)) {
			Event anno = e.method.getAnnotation(Event.class);
			methods.add(new AbstractMap.SimpleEntry<RegisteredMethod, Event>(e, anno));
		}
		methods.sort(new Sort());
		boolean first = true;
		for (Map.Entry<RegisteredMethod, Event> entry : methods) {
			RegisteredMethod e = entry.getKey();
			Event anno = entry.getValue();
			if (!first && anno.exclusive()) {
				continue;
			}
			e.method.invoke(e.target, data);
			if (first && anno.exclusive()) {
				break;
			}
			first = false;
		}
	}
}