/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bridgedb.metadata;

import org.bridgedb.metadata.constants.SchemaConstants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.bridgedb.metadata.type.*;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;
import org.w3c.dom.Element;

/**
 *
 * @author Christian
 */
public class PropertyMetaData extends MetaDataBase implements MetaData{

    private final String name;      
    private final URI predicate;
    private final MetaDataType metaDataType;
    private final RequirementLevel requirementLevel;
    private Set<Value> values;

    public PropertyMetaData(Element element) throws MetaDataException {
        name = element.getAttribute("name");
        String predicateSt = element.getAttribute(SchemaConstants.PREDICATE);
        predicate = new URIImpl(predicateSt);
        String objectClass = element.getAttribute(SchemaConstants.CLASS);
        metaDataType = getMetaDataType(objectClass);
        String requirementLevelSt = element.getAttribute(SchemaConstants.REQUIREMENT_LEVEL);
        requirementLevel = RequirementLevel.parse(requirementLevelSt);
        values = new HashSet<Value>();
    }
    
    private PropertyMetaData(PropertyMetaData other) {
        name = other.name;
        predicate = other.predicate;
        metaDataType = other.metaDataType;
        requirementLevel = other.requirementLevel;
        values = new HashSet<Value>();
    }
    
    private MetaDataType getMetaDataType(String objectClass) throws MetaDataException{
        if (SchemaConstants.CLASS_String.equalsIgnoreCase(objectClass)){
            return new StringType();
        }
        if (SchemaConstants.CLASS_URI.equalsIgnoreCase(objectClass)){
            return new UriType();
        }
        throw new MetaDataException ("Unexpected " + SchemaConstants.CLASS + " " + objectClass);
    }
    
    @Override
    public void appendToString(StringBuilder builder, int tabLevel) {
        tab(builder, tabLevel);
        builder.append("Property ");
        builder.append(name);
        if (values.isEmpty()){
            builder.append(" not Set ");
        } else if (values.size() == 1){
            builder.append(" == ");
            builder.append(values.iterator().next());
        } else {
            for (Value value: values){
                newLine(builder, tabLevel + 1);
                builder.append(value);
            }
        }
    }

    @Override
    public void appendSchema(StringBuilder builder, int tabLevel) {
        tab(builder, tabLevel);
        builder.append("Property ");
        builder.append(name);
        newLine(builder, tabLevel + 1);
        builder.append("predicate ");
        builder.append(predicate);        
        newLine(builder, tabLevel + 1);
        builder.append("class ");
        builder.append(metaDataType);        
        newLine(builder);
    }

    @Override
    public void loadValues(Resource id, Set<Statement> data, MetaData parent) {
        setupValues(id, parent);
        values = new HashSet<Value>();
        for (Iterator<Statement> iterator = data.iterator(); iterator.hasNext();) {
            Statement statement = iterator.next();
            if (statement.getPredicate().equals(predicate)){
                 iterator.remove();
                 rawRDF.add(statement);
                 values.add(statement.getObject());
            }
        }  
    }

    @Override
    public PropertyMetaData getSchemaClone() {
        return new PropertyMetaData(this);
    }

    @Override
    public boolean hasRequiredValues(RequirementLevel requirementLevel) {
        if (values.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasCorrectTypes() {
        for (Value value: values){
            if (!metaDataType.correctType(value)){
                return false;
            }
        }
        //If no incorrect values return true. Even if there are No values.
        return true;
    }



    @Override
    public void appendValidityReport(StringBuilder builder, RequirementLevel forceLevel, boolean includeWarnings, int tabLevel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
