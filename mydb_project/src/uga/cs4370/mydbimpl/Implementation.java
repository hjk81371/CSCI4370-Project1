package uga.cs4370.mydbimpl;
import uga.cs4370.mydb.*;

import java.util.List;

public class Implementation implements RA {
    
    /**
     * Performs the select operation on the relation rel
     * by applying the predicate p.
     * 
     * @return The resulting relation after applying the select operation.
     */
    @Override
    public Relation select(Relation rel, Predicate p) {
        // NEEDS IMPLEMENTATION
        Relation newrel = new RelationImpl(rel.getTypes(),rel.getAttrs());
        for(int i = 0; i<rel.getSize(); i++){
            List<Cell> rows = newrel.getRow(i);
            if(p.check(rows)){
                newrel.insert(rows.subList(0,rows.size()));
            }
        }
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
        // NEEDS IMPLEMENTATION

        /*
         * PRETTY MUCH THE SELECT CLAUSE IN SQL
         * Gets columns
         * https://www.makeuseof.com/learn-how-to-use-the-project-and-selection-operations-in-sql/
         */
        return null;
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
        // NEEDS IMPLEMENTATION
        if(rel1.getAttrs().equals(rel2.getAttrs()) && (rel1.getTypes().equals(rel2.getTypes()))){
            Relation newrel = new RelationImpl(rel1.getAttrs(),rel1.getTypes());
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
        if(rel1.getAttrs().equals(rel2.getAttrs()) && (rel1.getTypes().equals(rel2.getTypes()))){
            Relation newrel = new RelationImpl(rel1.getAttrs(),rel1.getTypes());



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
        // NEEDS IMPLEMENTATION
        return null;
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
        // NEEDS IMPLEMENTATION

        /*
         * Every possible combination of joining rel1 and rel2.
         */
        return null;
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
        // NEEDS IMPLEMENTATION

        /*
         * NOTE:
         * Joins rel1 and rel2 using cartesianProduct based on Predicate.
         */
        return null;
    }
}
