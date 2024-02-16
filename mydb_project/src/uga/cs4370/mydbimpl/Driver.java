package uga.cs4370.mydbimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uga.cs4370.mydb.*;
import uga.cs4370.mydbimpl.*;

public class Driver {
    
    public static void main(String[] args) {

        Relation instructors = new RelationBuilder()
                .attributeNames(List.of("id", "name", "dept", "salary"))
                .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
                .build();
        instructors.loadData("./instructor_export.csv");

        Relation students = new RelationBuilder()
        .attributeNames(List.of("id", "name", "dept", "tot_cred"))
        .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        students.loadData("./student_export.csv");

        Relation courses = new RelationBuilder()
        .attributeNames(List.of("id", "title", "dept", "credits"))
        .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.INTEGER))
        .build();
        courses.loadData("./course_export.csv");

        Relation advisors = new RelationBuilder()
        .attributeNames(List.of("stu_id", "instr_id"))
        .attributeTypes(List.of(Type.INTEGER, Type.INTEGER))
        .build();
        advisors.loadData("./advisor_export.csv");

        Relation prereqs = new RelationBuilder()
        .attributeNames(List.of("course_id", "prereq_id"))
        .attributeTypes(List.of(Type.INTEGER, Type.INTEGER))
        .build();
        prereqs.loadData("./prereqs_export.csv");

        Relation departments = new RelationBuilder()
        .attributeNames(List.of("name", "building", "budget"))
        .attributeTypes(List.of(Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        departments.loadData("./department_export.csv");

        RA ra = new Implementation();

        // Predicate templatePred = new Predicate(){
        //     @Override
        //     public boolean check(List<Cell> row) {
        //         int attrIndex = instructors.getAttrIndex("id");
        //         Cell cell = row.get(attrIndex);
        //         int value = (Integer) cell.getAsInt();
        //         return value > 90000;
        //     }
        // };


        // Queries go here, must have more than 1 table used also between 1 and 50 rows

        // QUERY 1 -- Names shared by students & professors
        // PROJECT[student.name](SELECT[student.name == instructor.name](students X instructors))
        System.out.println("\n1. Names shared by both students and professors:");

        Relation instrNames = ra.project(instructors, List.of("name"));
        Relation studentNames = ra.project(students, List.of("name"));

        // Natural join on name
        Relation sameNames = ra.join(instrNames, studentNames);
        sameNames.print();
        System.out.println();


        // QUERY 2 -- Students advised by an instructor in the Finance department
        // SELECT[instructor.dept == "Finance"](students JOIN[stu_id] advisors JOIN[instr_id] instructors)
        System.out.println("2. Students advised by an instructor in the Finance department:");

        // Join students, instructors, & advisors
        Relation renamedStudents = ra.rename(students, students.getAttrs(), List.of("stu_id", "stu_name", "stu_dept", "tot_cred"));
        Relation renamedInstrs = ra.rename(instructors, instructors.getAttrs(), List.of("instr_id", "instr_name", "instr_dept", "salary"));
        Relation studentXInstrXAdvisor = ra.join(ra.join(renamedStudents, advisors), renamedInstrs);

        Predicate deptIsFinance = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = studentXInstrXAdvisor.getAttrIndex("instr_dept");
                Cell cell = row.get(attrIndex);
                return cell.getAsString().equals("Finance");
            }
        };

        Relation advisedByFinance = ra.select(studentXInstrXAdvisor, deptIsFinance);
        Relation advisedByFinanceFinal = ra.project(advisedByFinance, List.of("stu_id", "stu_name", "stu_dept", "tot_cred"));
        advisedByFinanceFinal.print();
        System.out.println();

        // QUERY 3 -- Courses which have "Ponzi Schemes" as a prereq
        // SELECT[title == "Ponzi Schemes"](courses JOIN prereqs)

        System.out.println("3. Courses which require 'Ponzi Schemes' as a prereq:");

        Relation renamedCourses = ra.rename(courses, courses.getAttrs(), List.of("course_id", "course_title", "course_dept", "course_credits"));
        Relation renamedPrereqs = ra.rename(courses, courses.getAttrs(), List.of("prereq_id", "prereq_title", "prereq_dept", "prereq_credits"));
        Relation coursesXPrereqIDs = ra.join(renamedCourses, prereqs);
        Relation coursesXPrereqs= ra.join(coursesXPrereqIDs, renamedPrereqs);

        Predicate prereqIsPonziScheme = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = coursesXPrereqs.getAttrIndex("prereq_title");
                Cell cell = row.get(attrIndex);
                return cell.getAsString().equals("Ponzi Schemes");
            }
        };

        Relation prereqIsPonziSchemes = ra.select(coursesXPrereqs, prereqIsPonziScheme);
        Relation prereqIsPonziSchemesFinal = ra.project(prereqIsPonziSchemes, List.of("course_id", "course_title", "course_dept", "course_credits"));
        prereqIsPonziSchemesFinal.print();
        System.out.println();

        // QUERY 4 -- Instructors whose department has over $600,000 in funding
        System.out.println("4. Instructors whose departments have over $600,000 in funding:");
        
        Relation renamedDepts = ra.rename(departments, departments.getAttrs(), List.of("dept", "building", "budget"));
        Relation deptsXInstructors = ra.join(instructors, renamedDepts);

        Predicate budgetOverThreshold = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = deptsXInstructors.getAttrIndex("budget");
                Cell cell = row.get(attrIndex);
                return cell.getAsDouble() > 600000;
            }
        };

        Relation instructorsWithBudgetOverThreshold = ra.select(deptsXInstructors, budgetOverThreshold);
        Relation instructorsWithBudgetOverThresholdFinal = ra.project(instructorsWithBudgetOverThreshold, List.of("id", "name", "dept", "budget"));
        instructorsWithBudgetOverThresholdFinal.print();
        System.out.println();

        // QUERY 5 -- Instructors who do not work in Candlestick hall
        System.out.println("5. Instructors who don't work in Candlestick Hall:");

        Predicate worksInCandlestick = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = deptsXInstructors.getAttrIndex("building");
                Cell cell = row.get(attrIndex);
                return cell.getAsString().equals("Candlestick");
            }
        };

        Relation instructorsWhoWorkInCandlestick = ra.select(deptsXInstructors, worksInCandlestick);
        Relation noCandlestick = ra.diff(deptsXInstructors, instructorsWhoWorkInCandlestick);
        Relation noCandlestickFinal = ra.project(noCandlestick, List.of("id", "name", "building"));
        noCandlestickFinal.print();
        System.out.println();

        // TODO: queries 5

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
