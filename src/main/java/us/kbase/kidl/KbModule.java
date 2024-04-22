package us.kbase.kidl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class represents module in spec file. It consists of 3 blocks in parsing structure:
 * properties, type info, name to type map.
 */
public class KbModule {
	private String moduleName;
	private String serviceName;
	private String comment;
	private List<String> options;
	private List<KbModuleComp> moduleComponents;
	private List<KbTypeInfo> typeInfoList;
	private Map<String, KbType> nameToType;
	private KbAnnotations annotations;
	private String lastAuthTempMode = "none";
	private List<?> data = null;
	
	public KbModule() {
	}
	
	public KbModule(String serviceName, String moduleName, String comment) {
		this.serviceName = serviceName == null ? moduleName : serviceName;
		this.moduleName = moduleName;
		this.comment = comment == null ? "" : comment;
		this.options = new ArrayList<String>();
		this.moduleComponents = new ArrayList<KbModuleComp>();
		this.typeInfoList = new ArrayList<KbTypeInfo>();
		this.nameToType = new LinkedHashMap<String, KbType>();
		nameToType.put("int", new KbScalar("int"));
		nameToType.put("float", new KbScalar("float"));
		nameToType.put("string", new KbScalar("string"));
		nameToType.put("UnspecifiedObject", new KbUnspecifiedObject());
		annotations = new KbAnnotations();
	}
	
	public void addModuleComponent(KbModuleComp comp) {
		moduleComponents.add(comp);
		if (comp instanceof KbAuthdef) {
			lastAuthTempMode = ((KbAuthdef)comp).getType();
		} else if (comp instanceof KbTypedef) {
			KbTypedef typeDef = (KbTypedef)comp;
			nameToType.put(typeDef.getName(), typeDef);
		} else if (lastAuthTempMode != null) {
			KbFuncdef func = (KbFuncdef)comp;
			if (func.getAuthentication() == null)
				func.setAuthentication(lastAuthTempMode);
		}
	}
	
	public Object toJson() {
		List<Object> ret = new ArrayList<Object>();
		Map<String, Object> main = new TreeMap<String, Object>();
		main.put("!", "Bio::KBase::KIDL::KBT::DefineModule");
		main.put("annotations", annotations.toJson(false));
		if (comment != null)
			main.put("comment", comment);
		List<Object> comps = new ArrayList<Object>();
		for (KbModuleComp comp : moduleComponents)
			comps.add(comp.toJson());
		main.put("module_components", comps);
		main.put("module_name", moduleName);
		main.put("options", options);
		main.put("service_name", serviceName);
		ret.add(main);
		ret.add(new ArrayList<Object>());
		Map<String, Object> thirdPart = new TreeMap<String, Object>();
		for (Map.Entry<String, KbType> entry : nameToType.entrySet())
			thirdPart.put(entry.getKey(), entry.getValue().toJson());
		ret.add(thirdPart);
		return ret;
	}
	
	public void loadFromList(List<?> data) throws KidlParseException {
		this.data = data;
		if (data.size() != 3)
			throw new KidlParseException("List has wrong number of elements: " + data.size());
		Map<?,?> props = (Map<?,?>)data.get(0);
		moduleName = Utils.prop(props, "module_name");
		serviceName = Utils.prop(props, "service_name");
		comment = Utils.propOrNull(props, "comment");
		List<?> optionList = Utils.propList(props, "options");
		options = Collections.unmodifiableList(Utils.repareTypingString(optionList));
		moduleComponents = new ArrayList<KbModuleComp>();
		List<?> compList = Utils.propList(props, "module_components");
		String defaultAuth = null;
		for (Object item : compList) {
			if (!Map.class.isInstance(item)) {
				if (item instanceof String) {
					defaultAuth = (String)item;
				} else {
					throw new KidlParseException("List item is not compatible with type " +
							"[" + Map.class.getName() + "], it has type: " + item.getClass().getName());
				}
			} else {
				Map<?,?> compProps = (Map)item;
				String compType = Utils.getPerlSimpleType(compProps);
				if (compType.equals("Typedef")) {
					moduleComponents.add(new KbTypedef().loadFromMap(compProps));
				} else if (compType.equals("Funcdef")) {
					moduleComponents.add(new KbFuncdef().loadFromMap(compProps, defaultAuth));
				} else {
					throw new KidlParseException("Unknown module component type: " + compType);
				}
			}
		}
		moduleComponents = Collections.unmodifiableList(moduleComponents);
		typeInfoList = new ArrayList<KbTypeInfo>();
		for (Map<?,?> infoProps : Utils.repareTypingMap((List<?>)data.get(1))) {
			typeInfoList.add(new KbTypeInfo().loadFromMap(infoProps));
		}
		typeInfoList = Collections.unmodifiableList(typeInfoList);
		Map<?,?> typeMap = (Map<?,?>)data.get(2);
		nameToType = new LinkedHashMap<String, KbType>();
		for (Object key : typeMap.keySet()) {
			String typeName = key.toString();
			Map<?,?> typeProps = Utils.propMap(typeMap, typeName);
			nameToType.put(typeName, Utils.createTypeFromMap(typeProps));
		}
		nameToType = Collections.unmodifiableMap(nameToType);
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public List<String> getOptions() {
		return options;
	}
	
	public String getComment() {
		return comment;
	}
	
	public List<KbModuleComp> getModuleComponents() {
		return moduleComponents;
	}
	
	public List<KbTypeInfo> getTypeInfoList() {
		return typeInfoList;
	}
	
	public Map<String, KbType> getNameToType() {
		return nameToType;
	}
	
	public List<?> getData() {
		return data;
	}
}
