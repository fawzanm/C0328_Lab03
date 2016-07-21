/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
 
@Path("rest")
public class StudentService
{    
    StudentRegister register = new StudentRegister();
    private static final String SUCCESS_RESULT="<result>success</result>";
    private static final String FAILURE_RESULT="<result>failure</result>";
    
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces(MediaType.APPLICATION_XML)
    public Student viewStudent(@PathParam("id") int id) {
        return register.findStudent(id);
    }
    
    @POST
    @Path("student/{id}-{firstname}-{lastname}")
    @Produces(MediaType.APPLICATION_XML)
    public String modifyStudent(@PathParam("id") int id, @PathParam("firstname") String firstname, @PathParam("lastname") String lastname)
    {
        register.removeStudent(id);
        Student student = new Student(id, firstname, lastname);
        try {
            register.addStudent(student);
        } catch (Exception ex) {
            return FAILURE_RESULT;
        }
        return SUCCESS_RESULT;
    }
    
    @DELETE
    @Path("student/{id}")
    public String deleteStudent(@PathParam("id") int id)
    {
        register.removeStudent(id);
        return SUCCESS_RESULT;
    }
    
    @PUT
    @Path("student/{id}-{firstname}-{lastname}")
    @Produces(MediaType.APPLICATION_XML)
    public String addStudent(@PathParam("id") int id, @PathParam("firstname") String firstname, @PathParam("lastname") String lastname)
    {
        Student student = new Student(id, firstname, lastname);
        try {
            register.addStudent(student);
        } catch (Exception ex) {
            return FAILURE_RESULT;
        }
        return SUCCESS_RESULT;
    }
}


