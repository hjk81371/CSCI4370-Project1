package uga.cs4370.mydbimpl;

import java.util.List;

import uga.cs4370.mydb.*;

import uga.cs4370.mydb.Relation;
import uga.cs4370.mydb.RelationBuilder;
import uga.cs4370.mydb.Type;

public class Driver {
    
    public static void main(String[] args) {
        // Following is an example of how to use the relation class.
        // This creates a table with three columns with below mentioned
        // column names and data types.
        // After creating the table, data is loaded from a CSV file.
        // Path should be replaced with a correct file path for a compatible
        // CSV file.
        System.out.println("My Id: hjk81371");
        Relation rel1 = new RelationBuilder()
                .attributeNames(List.of("id", "name", "dept", "salary"))
                .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
                .build();
        rel1.loadData("/Users/harrisonkirstein/Desktop/mydb_project/activity_2/mysql-files/instructor_export.csv");
        //rel1.print();

        Relation rel2 = new RelationBuilder()
            .attributeNames(List.of("id", "name", "dept", "tot_cred"))
            .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
            .build();
        rel2.loadData("/Users/harrisonkirstein/Desktop/mydb_project/activity_2/mysql-files/student_export.csv");
        rel2.print();
 
        //rel instr10001 = ra.select(instructor,(row)-> row.get(instr.getAttrIndex("instr_id").equals(cell.val(10001))))
        //instr10000.print();
    }
}
