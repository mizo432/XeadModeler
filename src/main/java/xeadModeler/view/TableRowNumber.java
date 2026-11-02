package xeadModeler.view;

import xeadModeler.service.JsonService;
import xeadModeler.service.JsonServiceImpl;

/**
 * Class of Table Row Number
 */
public class TableRowNumber extends Object {

    private Object pojo_;
    private Object pojo_ext1;
    private Object pojo_ext2;
    private JsonService jsonService;

    public TableRowNumber(int num, Object elm) {
      number_ = num;
      pojo_ = elm;
      pojo_ext1 = null;
      pojo_ext2 = null;
      this.jsonService = new JsonServiceImpl();
    }

    public TableRowNumber(int num, Object elm, Object elmExt1,
        Object elmExt2) {
      number_ = num;
      pojo_ = elm;
      pojo_ext1 = elmExt1;
      pojo_ext2 = elmExt2;
      this.jsonService = new JsonServiceImpl();
    }

    private int number_;

    public String toString() {
      return Integer.toString(number_);
    }

    public Object getPojo() {
      return pojo_;
    }

    public Object getPojoExt1() {
      return pojo_ext1;
    }

    public Object getPojoExt2() {
      return pojo_ext2;
    }

    /**
     * Get attribute value from JSON POJO.
     * Maps attribute names to getter method names.
     */
    public String getAttribute(String attributeName) {
      if (pojo_ == null) {
        return null;
      }
      return getAttributeFromJsonPojo(pojo_, attributeName);
    }

    /**
     * Get attribute value from pojo_ext1.
     */
    public String getAttributeExt1(String attributeName) {
      if (pojo_ext1 == null) {
        return null;
      }
      return getAttributeFromJsonPojo(pojo_ext1, attributeName);
    }

    /**
     * Get attribute value from pojo_ext2.
     */
    public String getAttributeExt2(String attributeName) {
      if (pojo_ext2 == null) {
        return null;
      }
      return getAttributeFromJsonPojo(pojo_ext2, attributeName);
    }

    /**
     * Get attribute value from JSON POJO by mapping attribute names to getter methods.
     */
    private String getAttributeFromJsonPojo(Object obj, String attributeName) {
      if (obj == null) {
        return null;
      }
      // Map common attribute names to getter method names
      String getterName;
      switch (attributeName) {
        case "ID":
          getterName = "getId";
          break;
        case "Name":
          getterName = "getName";
          break;
        case "Descriptions":
          getterName = "getDescriptions";
          break;
        case "SortKey":
          getterName = "getSortKey";
          break;
        case "BasicType":
          getterName = "getBasicType";
          break;
        case "Length":
          getterName = "getLength";
          break;
        case "Decimal":
          getterName = "getDecimal";
          break;
        case "SQLExpression":
          getterName = "getSqlExpression";
          break;
        case "Header":
        case "Headder":
          getterName = "getHeader";
          break;
        case "Type":
          getterName = "getType";
          break;
        case "Table1ID":
          getterName = "getTable1ID";
          break;
        case "TableKey1ID":
          getterName = "getTableKey1ID";
          break;
        case "Table2ID":
          getterName = "getTable2ID";
          break;
        case "TableKey2ID":
          getterName = "getTableKey2ID";
          break;
        case "Version":
          getterName = "getVersion";
          break;
        default:
          // Try to construct getter name from attribute name
          getterName = "get" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
          break;
      }
      try {
        java.lang.reflect.Method method = obj.getClass().getMethod(getterName);
        Object result = method.invoke(obj);
        return result != null ? result.toString() : null;
      } catch (Exception e) {
        // If getter doesn't exist or fails, return null
        return null;
      }
    }

    /**
     * Set attribute value on JSON POJO.
     */
    public void setAttribute(String attributeName, String value) {
      if (pojo_ == null) {
        return;
      }
      jsonService.setAttributeOnJsonPojo(pojo_, attributeName, value);
    }

    /**
     * Set attribute value on JSON POJO by mapping attribute names to setter methods.
     */
    private void setAttributeOnJsonPojo(Object obj, String attributeName, String value) {
      if (obj == null) {
        return;
      }
      String setterName;
      switch (attributeName) {
        case "ID":
          setterName = "setId";
          break;
        case "Name":
          setterName = "setName";
          break;
        case "Descriptions":
          setterName = "setDescriptions";
          break;
        case "SortKey":
          setterName = "setSortKey";
          break;
        case "BasicType":
          setterName = "setBasicType";
          break;
        case "Length":
          setterName = "setLength";
          break;
        case "Decimal":
          setterName = "setDecimal";
          break;
        case "SQLExpression":
          setterName = "setSqlExpression";
          break;
        case "Header":
        case "Headder":
          setterName = "setHeader";
          break;
        case "Type":
          setterName = "setType";
          break;
        case "Table1ID":
          setterName = "setTable1ID";
          break;
        case "TableKey1ID":
          setterName = "setTableKey1ID";
          break;
        case "Table2ID":
          setterName = "setTable2ID";
          break;
        case "TableKey2ID":
          setterName = "setTableKey2ID";
          break;
        case "Version":
          setterName = "setVersion";
          break;
        case "AscDesc":
          setterName = "setAscDesc";
          break;
        default:
          setterName = "set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
          break;
      }
      try {
        java.lang.reflect.Method method = obj.getClass().getMethod(setterName, String.class);
        method.invoke(obj, value);
      } catch (Exception e) {
        // If setter doesn't exist or fails, ignore
      }
    }
  }
