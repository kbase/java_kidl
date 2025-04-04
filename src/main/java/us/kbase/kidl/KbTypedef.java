package us.kbase.kidl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class represents type definition (or named type) in spec-file.
 * @author rsutormin
 */
public class KbTypedef implements KbType, KbModuleDef {
	private String name;
	private String module;
	private KbType aliasType;
	private String comment;
	private Map<?,?> data = null;
	private KbAnnotations annotations;
	
	public KbTypedef() {}
	
	public KbTypedef(String module, String name, KbType aliasType, String comment) throws KidlParseException {
		this.module = module;
		this.name = name;
		this.aliasType = aliasType;
		this.comment = comment == null ? "" : comment;
		this.annotations = new KbAnnotations().loadFromComment(this.comment, this);
		
		// TODO Check if we are overriding an annotation that is defined lower - if so, then we should
		// either override or throw an error.  Right now we silently ignore.
		if (aliasType instanceof KbScalar) {
			((KbScalar)aliasType).utilizeAnnotations(annotations);
		} else if (aliasType instanceof KbTuple) {
			((KbTuple)aliasType).setComment(this.comment);
			((KbTuple)aliasType).setName(this.name);
		} else if (aliasType instanceof KbStruct) {
			((KbStruct)aliasType).setComment(this.comment);
			((KbStruct)aliasType).setModule(this.module);
			((KbStruct)aliasType).setName(this.name);
			((KbStruct)aliasType).utilizeAnnotations(annotations);
		}
	}
	
	public KbTypedef loadFromMap(Map<?,?> data) throws KidlParseException {
		name = Utils.prop(data, "name");
		module = Utils.prop(data, "module");
		comment = Utils.prop(data, "comment");
		annotations = new KbAnnotations();
		if (data.containsKey("annotations")) {
			annotations.loadFromMap(Utils.propMap(data, "annotations"));
		}
		aliasType = Utils.createTypeFromMap(Utils.propMap(data, "alias_type"), annotations);
		this.data = data;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see us.kbase.kidl.KbModuleDef#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	public String getModule() {
		return module;
	}
	
	/* (non-Javadoc)
	 * @see us.kbase.kidl.KbModuleDef#getComment()
	 */
	@Override
	public String getComment() {
		return comment;
	}
	
	public KbType getAliasType() {
		return aliasType;
	}
	
	/* (non-Javadoc)
	 * @see us.kbase.kidl.KbModuleDef#getData()
	 */
	@Override
	public Map<?, ?> getData() {
		return data;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KbTypedef [name=");
		builder.append(name);
		builder.append(", module=");
		builder.append(module);
		builder.append(", aliasType=");
		builder.append(aliasType);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", data=");
		builder.append(data);
		builder.append(", annotations=");
		builder.append(annotations);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		return getSpecName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null &&
				(obj instanceof KbTypedef) &&
				getSpecName().equals(((KbTypedef)obj).getSpecName());
	}
	
	/* (non-Javadoc)
	 * @see us.kbase.kidl.KbModuleDef#getAnnotations()
	 */
	@Override
	public KbAnnotations getAnnotations() {
		return annotations;
	}
	
	@Override
	public <T> T accept(final KidlVisitor<T> visitor, final KidlNode parent) {
		return visitor.visit(this, parent, aliasType.accept(visitor, this));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object toJsonSchema(boolean inner) {
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
		if (!inner) {
			ret.put("id", getName());
			ret.put("description", getComment());
		}
		if (!(getAliasType() instanceof KbStruct))
			inner = true;
		ret.putAll((Map<String, Object>)getAliasType().toJsonSchema(inner));
		return ret;
	}

	@Override
	public void afterCreation() {
		// Default implementation - just do nothing
	}

	@Override
	public String getSpecName() {
		return module + "." + name;
	}
}
