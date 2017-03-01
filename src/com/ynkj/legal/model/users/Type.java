package com.ynkj.legal.model.users;

import java.util.List;



/**
 * @author fish
 *
 */
public class Type {
	String typeId;
	String typeName;
	String addTime;
	String typeDescripe;
	String typePic;
	String parentId;
	Integer isParent;
	Integer deleteFlag;
	String typeKey;
	Integer typeValue;
	
	private List<Type> listTypes;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getTypeDescripe() {
		return typeDescripe;
	}

	public void setTypeDescripe(String typeDescripe) {
		this.typeDescripe = typeDescripe;
	}

	public String getTypePic() {
		return typePic;
	}

	public void setTypePic(String typePic) {
		this.typePic = typePic;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public Integer getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(Integer typeValue) {
		this.typeValue = typeValue;
	}

	public List<Type> getListTypes() {
		return listTypes;
	}

	public void setListTypes(List<Type> listTypes) {
		this.listTypes = listTypes;
	}
	
	
	
	
	
	

}
