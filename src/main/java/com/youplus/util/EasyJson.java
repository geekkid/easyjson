package com.youplus.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class EasyJson {

	private Map<String, Object>	jsonData;
	private Map<String, Object>	flatJsonMap;

	public EasyJson(String jsonStr, String rootElement) {
		try {
			this.jsonData = EasyJson.getMapFromJSON(jsonStr);
			flatJsonMap = new HashMap<String, Object>();
			this.createFlatMap(rootElement, this.jsonData);
			System.out.println(flatJsonMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public EasyJson(String jsonStr) {
		this(jsonStr, "root");
	}
		
	@SuppressWarnings("unchecked")
	private void createFlatMap(String nodeDepth, Object element) {
		if (element instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) element;
			flatJsonMap.put(nodeDepth, map);
			for (Entry<String, Object> entry : map.entrySet()) {
				createFlatMap(nodeDepth + "." + entry.getKey(), entry.getValue());
			}
		} else if (element instanceof List) {
			List<Object> list = (List<Object>) element;
			int i=0;
			flatJsonMap.put(nodeDepth, list);
			flatJsonMap.put(nodeDepth + "[count]", list.size());
			for (Object listItem : list) {
				createFlatMap(nodeDepth + "[" + i + "]", listItem);
				i++;
			}
		} else {
			System.out.println(nodeDepth);
			flatJsonMap.put(nodeDepth, element);
		}
	}

	public Object getValue(String jsonPath) {
		return flatJsonMap.get(jsonPath);
	}

	public static List<Map<String, Object>> getObjectFromJSON(String jsonData)
			throws IOException {
		return new ObjectMapper().readValue(jsonData,
				new TypeReference<ArrayList<Map<String, Object>>>() {
				});
	}

	public static Map<String, Object> getMapFromJSON(String jsonData)
			throws IOException {
		return new ObjectMapper().readValue(jsonData,
				new TypeReference<HashMap<String, Object>>() {
				});
	}

}
