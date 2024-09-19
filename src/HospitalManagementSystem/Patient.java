package HospitalManagementSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){

        /*use of parametrized constructor so that the value to the
        private members would be assign from the user or else
         from the location where class object is created */

        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatient(){
        System.out.println("-----Enter Patient Details-----");
        System.out.print("Patient Name:-");
        String name= scanner.next();
        System.out.print("patients Age:-");
        int age =scanner.nextInt();
        System.out.print("Patients Gender:-");
        String gender= scanner.next();

        try{
            String query ="INSERT INTO patients(name, age, gender) VALUES (?,?,?)";
            /*VALUES (?,?,?) this values will be directly inserted by prepared statement */
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            /* Below lines are used to set the values of VALUES(?,?,?) */
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient ADDED successfully");
            }
            else{
                System.out.println("Failed to ADD Patient");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatients(){
        String query="select * from patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients:-");
            System.out.println("+-----------+---------------+--------+-----------+");
            System.out.println("|Patients id| Name          | Age    | Gender    |");
            System.out.println("+-----------+---------------+--------+-----------+");
            while(resultSet.next()){
                int id= resultSet.getInt("id");
                String name= resultSet.getString("name");
                int age= resultSet.getInt("age");
                String gender= resultSet.getString("gender");
                System.out.printf("| %-10s| %-14s| %-7s| %-10s|\n", id, name, age, gender);
                System.out.println("+-----------+---------------+--------+-----------+");

            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Boolean getPatientById(int id){
        String query ="SELECT * FROM patients WHERE id = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            } else {
                return false;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }


}
