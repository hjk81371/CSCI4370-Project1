package uga.cs4370.mydbimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uga.cs4370.mydb.*;
import uga.cs4370.mydbimpl.*;

public class Driver {
    
    public static void main(String[] args) {
        // Following is an example of how to use the relation class.
        // This creates a table with three columns with below mentioned
        // column names and data types.
        // After creating the table, data is loaded from a CSV file.
        // Path should be replaced with a correct file path for a compatible
        // CSV file.
        Relation rel1 = new RelationBuilder()
                .attributeNames(List.of("id", "name", "dept", "salary"))
                .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
                .build();
        //rel1.loadData("/Users/harrisonkirstein/Desktop/CSCI4370-Project1/instructor_export.csv");
        rel1.loadData("C:\\Users\\brand\\OneDrive\\Documents\\GitHub\\CSCI4370-Project1\\instructor_export.csv");

        Relation rel2 = new RelationBuilder()
        .attributeNames(List.of("id", "name", "dept", "tot_cred"))
        .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        //rel2.loadData("/Users/harrisonkirstein/Desktop/CSCI4370-Project1/student_export.csv");
        rel2.loadData("C:\\Users\\brand\\OneDrive\\Documents\\GitHub\\CSCI4370-Project1\\student_export.csv");

        List<String> testList = new ArrayList<>();
        testList.add("id");
        testList.add("name");
        testList.add("dept");
        testList.add("salary");
        
        RA ra = new Implementation();

        Predicate pred = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = rel1.getAttrIndex("id");
                Cell cell = row.get(attrIndex);
                int value = (Integer) cell.getAsInt();
                return value > 90000;
            }
        };

        Predicate strPred = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = rel1.getAttrIndex("dept");
                Cell cell = row.get(attrIndex);
                String value = (String) cell.getAsString();
                return value == "English";
            }
        };


        
        //Queries go here, must have more than 1 table used also between 1 and 50 rows
        
        
        List<String> test2 = new ArrayList<>();
        test2.add("ids");
        test2.add("names");
        test2.add("building");
        test2.add("build_name");

        //ra.rename(rel2, testList, test2);
        

        //ra.join(rel1, rel2).print();
        //ra.diff(rel1, rel2).print(); //query 1
        //Relation id9000 =ra.select(rel1, pred);
        //id9000.print();
        //List<String> deptattr = Arrays.asList("dept");
        //ra.project(rel1,deptattr).print();

        //ra.project(rel1, testList).print();
        //ra.cartesianProduct(rel2, ra.project(rel1, testList)).print();
        //ra.cartesianProduct(rel1, rel2).print();
        //ra.join(rel1,rel2,pred).print();

        // Relation rel2 = new RelationBuilder()
        //     .attributeNames(List.of("id", "name", "dept", "tot_cred"))
        //     .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
        //     .build();
        // rel2.loadData("/Users/harrisonkirstein/Desktop/mydb_project/activity_2/mysql-files/student_export.csv");
        // rel2.print();
 
        // rel instr10001 = ra.select(instructor,(row)-> row.get(instr.getAttrIndex("instr_id").equals(cell.val(34175))))
        // instr10000.print();
        //ra.select(rel1,strPred).print();;
    }
}
