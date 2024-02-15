package uga.cs4370.mydbimpl;
import uga.cs4370.mydb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Implementation implements RA {
    
    /**
     * Performs the select operation on the relation rel
     * by applying the predicate p.
     * 
     * @return The resulting relation after applying the select operation.
     * Is giving a weird array out of bounds index atm
     */
    @Override
    public Relation select(Relation rel, Predicate p) {

        Relation newrel = new RelationBuilder().attributeNames(rel.getAttrs()).attributeTypes(rel.getTypes()).build();
        for(int i = 0; i<(rel.getSize()); i++) {
            List<Cell> findrows = rel.getRow(i);
            if(p.check(findrows)){
                newrel.insert(findrows);
            }
            
        } // for
            
        /*
         * PRETTY MUCH THE WHERE CLAUSE IN SQL.
         */
        return newrel;
    }

    /**
     * Performs the project operation on the relation rel
     * given the attributes list attrs.
     * 
     * @return The resulting relation after applying the project operation.
     * 
     * @throws IllegalArgumentException If attributes in attrs are not 
     * present in rel.
     */
    @Override
    public Relation project(Relation rel, List<String> attrs) {
        List<Type> currTypes = rel.getTypes();
        List<String> currAttributes = rel.getAttrs();
        List<Integer> attrsToKeep = new ArrayList<>();
        List<String> attrsNameInCorrectOrder = new ArrayList<>();

        // find columns to keep
        for (int i = 0; i < currAttributes.size(); i++) {
            if (attrs.contains(currAttributes.get(i))) {
                attrsToKeep.add(i);
                attrsNameInCorrectOrder.add(currAttributes.get(i));
            }
        }

        // find types of columns to keep
        List<Type> newTypes = new ArrayList<>();
        for (int index : attrsToKeep) {
            newTypes.add(currTypes.get(index));
        }

        // create projected relation
        Relation newRel = new RelationBuilder().attributeNames(attrsNameInCorrectOrder).attributeTypes(newTypes).build();

        // project selected rows onto new relation
        int numOfRows = rel.getSize();
        for (int i = 0; i < numOfRows; i++) {
            List<Cell> currRow = rel.getRow(i);
            List<Cell> newRow = new ArrayList<>();
            for (int j = 0; j < attrsToKeep.size(); j++) {
                newRow.add(currRow.get(attrsToKeep.get(j)));
            } // for

            newRel.insert(newRow);
        }
        /*
         * PRETTY MUCH THE SELECT CLAUSE IN SQL
         * Gets columns
         * https://www.makeuseof.com/learn-how-to-use-the-project-and-selection-operations-in-sql/
         */
        return newRel;
    }

    /**
     * Performs the union operation on the relations rel1 and rel2.
     * 
     * @return The resulting relation after applying the union operation.
     * 
     * @throws IllegalArgumentException If rel1 and rel2 are not compatible.
     */
    @Override
    public Relation union(Relation rel1, Relation rel2) {
        
        if(rel1.getAttrs().size() == (rel2.getAttrs().size()) && (rel1.getTypes().equals(rel2.getTypes()))){
            Relation newrel = new RelationBuilder().attributeNames(rel1.getAttrs()).attributeTypes(rel1.getTypes()).build();
            for(int i = 0; i<(rel1.getSize()); i++) {
                newrel.insert(rel1.getRow(i));
            }
            for(int i=0; i<(rel2.getSize());i++){
                newrel.insert(rel2.getRow(i));
            }
            return newrel;
            }
        else {
            throw new IllegalArgumentException ("rel1 and rel2 are not compatible.");
        }
        /*
         * NOTE:
         * RETURNING TABLE SHOULD ONLY HAVE ATRRIBUTE NAMES FROM rel1
         * Ensure to check that number of attributes in rel1 and rel2 are equal.
         * Ensure to check that types of attributes in rel1 and rel2 are the same (attribute name does not matter).
         * Technically supposed to remove duplicates, but Menik specified it does not matter for the project.
         */
    }

    /**
     * Performs the set difference operaion on the relations rel1 and rel2.
     * 
     * @return The resulting relation after applying the set difference operation.
     * 
     * @throws IllegalArgumentException If rel1 and rel2 are not compatible.
     */
    @Override
    public Relation diff(Relation rel1, Relation rel2) {
        // NEEDS IMPLEMENTATION
        if(rel1.getAttrs().size() == (rel2.getAttrs().size()) && (rel1.getTypes().equals(rel2.getTypes()))){
            Relation newrel = new RelationBuilder().attributeNames(rel1.getAttrs()).attributeTypes(rel1.getTypes()).build();



        /*
         * NOTE:
         * RETURNING TABLE SHOULD ONLY HAVE ATRRIBUTE NAMES FROM rel1
         * Check if a row in rel1 appears in rel2, if so remove it from returned relation.
         * basically XOR, only return rows not present in both rel1 and rel2.
         */
            return newrel;
        } else {
            throw new IllegalArgumentException("rel1 and rel2 are not compatible.");
        }
    }

    /**
     * Renames the attributes in origAttr of relation rel to corresponding 
     * names in renamedAttr.
     * 
     * @return The resulting relation after renaming the attributes.
     * 
     * @throws IllegalArgumentException If attributes in origAttr are not present in 
     * rel or origAttr and renamedAttr do not have matching argument counts.
     */
    @Override
    public Relation rename(Relation rel, List<String> origAttr, List<String> renamedAttr) {
        if(origAttr.getAttrs() != renamedArrt.getAttrs() || origAttr.getAttrs() != rel.getAttrs()) {
            List<String> attr = rel.getAttrs();
            List<String> newAttr = new ArrayList<String>();
            HashMap<String, String> sim = new HashMap<String, String>();
            
            for(int i=0; i<attr.size(); i++){
                sim.put(origAttr.get(i), renamedAttr.get(i));
            }
            for(int i=0; i<attr.size(); i++){
                if(sim.containsKey(attr.get(i))){
                    newAttr.add(sim.get(attr.get(i)));
                }
                else{
                    newAttr.add(attr.get(i));
                }
            }
            Relation newName = new RelationBuilder().attributeNames(rel.getAttrs()).attributeTypes(rel.getTypes()).build();;
            return newName;
        } else {
            throw new IllegalArgumentException("attributes in origAttr are not present in rel or origAttr and renamedAttr do not have matching argument counts.");
        }
    }
        
    }

    /**
     * Performs cartisian product on relations rel1 and rel2.
     * 
     * @return The resulting relation after applying cartisian product.
     * 
     * @throws IllegalArgumentException if rel1 and rel2 have common attibutes.
     */
    @Override
    public Relation cartesianProduct(Relation rel1, Relation rel2) {
        List<String> attrs = new ArrayList<>();
        attrs.addAll(rel1.getAttrs());
        List<String> rel2Attributes = rel2.getAttrs();
        
        // ensure no duplicate attribute names
        for (int i = 0; i < rel2Attributes.size(); i++) {
            if (attrs.contains(rel2Attributes.get(i))) {
                String currName = rel2Attributes.get(i);
                String newName = currName + "_rel_2";
                rel2Attributes.remove(i);
                rel2Attributes.add(i, newName);
            }
        } // for

        attrs.addAll(rel2Attributes);
        List<Type> types = new ArrayList<>();
        types.addAll(rel1.getTypes());
        types.addAll(rel2.getTypes());

        Relation newRel = new RelationBuilder().attributeNames(attrs).attributeTypes(types).build();
        for (int i = 0; i < rel1.getSize(); i++) {
            for (int j = 0; j < rel2.getSize(); j++) {
                List<Cell> rel1Row = rel1.getRow(i);
                List<Cell> rel2Row = rel2.getRow(j);
                List<Cell> combinedRows = new ArrayList<>();
                combinedRows.addAll(rel1Row);
                combinedRows.addAll(rel2Row);
                newRel.insert(combinedRows);
            }
        }

        /*
         * Every possible combination of joining rel1 and rel2.
         */
        return newRel;
    }

    /**
     * Peforms natural join on relations rel1 and rel2.
     * 
     * @return The resulting relation after applying natural join.
     */
    @Override
    public Relation join(Relation rel1, Relation rel2) {
        // NEEDS IMPLEMENTATION
        /*
         * Join columns with cartesian product together, but decide
         * the column to join by automatically based on common attribute name.
         * RETURN RELATION SHOULD ONLY CONTAIN ONE OF THE COLUMNS THAT SHARE NAME.
         */
        return null;
    }

    /**
     * Performs theta join on relations rel1 and rel2 with predicate p.
     * 
     * @return The resulting relation after applying theta join.
     * 
     * @throws IllegalArgumentException if rel1 and rel2 have common attibutes.
     */
    @Override
    public Relation join(Relation rel1, Relation rel2, Predicate p) {
        return select(cartesianProduct(rel1, rel2), p);
        
        /*
         * NOTE:
         * Joins rel1 and rel2 using cartesianProduct based on Predicate.
         */
    }
}
