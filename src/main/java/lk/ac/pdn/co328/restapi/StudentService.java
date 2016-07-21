/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService
{
    StudentRegister studentReg = new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Student viewStudent(@PathParam("id") int id) {
        Student st = new Student();
        st = studentReg.findStudent(id);
        if (st != null)  return st;
        else return new Student();
    }
    
    @POST
    @Path("student/{id}-{firstname}-{lastname}")
    @Produces(MediaType.APPLICATION_XML)
    public String modifyStudent(@PathParam("id") int id, @PathParam("firstname") String firstname, @PathParam("lastname") String lastname)
    {
        studentReg.removeStudent(id);
        Student student = new Student(id, firstname, lastname);
        try {
            studentReg.addStudent(student);
        } catch (Exception ex) {
            return "<html><h1>failure</h1></html>";
        }
        return "<html><h1>success</h1></html>";
        //String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public String deleteStudent(@PathParam("id") int id)
    {
        studentReg.removeStudent(id);
        return "<html><h1>success</h1></html>";
        //String message = "{message:'FIXME : Delete service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @PUT
    @Path("student/{id}-{firstname}-{lastname}")
    @Produces(MediaType.APPLICATION_XML)
    public String addStudent(@PathParam("id") int id, @PathParam("firstname") String firstname, @PathParam("lastname") String lastname)
    {
        Student student = new Student(id, firstname, lastname);
        try {
            studentReg.addStudent(student);
        } catch (Exception ex) {
            return "<html><h1>failture</h1></html>";
        }

        //String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();

        return "<html><h1>success</h1></html>";
    }
}


