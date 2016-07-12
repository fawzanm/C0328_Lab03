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
    StudentRegister reg =null;

    public StudentService()
    {
        reg = new StudentRegister();
    }

    @GET
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id)
    {
        Student st =  reg.findStudent(id);
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }

    @DELETE
    @Path("student/{id}")
    @Produces("text/html")
    public Response deleteStudent(@PathParam("id") int id)
    {
        reg.removeStudent(id);
        String message = "Deleted the student with the ID : " + id;
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @POST
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        reg.removeStudent(id);
        String message = null;
        try {
            String nid = "";
            String fn = "";
            String ln = "";
            input = input.substring(75);

            while(input.charAt(0) != '<')
            {
                fn = fn + Character.toString(input.charAt(0));
                input = input.substring(1);
            }

            input = input.substring(16);

            while(input.charAt(0) != '<')
            {
                nid = nid + Character.toString(input.charAt(0));
                input = input.substring(1);
            }

            input = input.substring(15);

            while(input.charAt(0) != '<')
            {
                ln = ln + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            Student st = new Student(Integer.parseInt(nid), fn, ln);
            reg.addStudent(st);
            message = "Edited the student with ID : " + id;
        }catch (Exception e) {
            message = "Failed to edit the student with ID : " + id;
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message = null;
        try {
            String nid = "";
            String fn = "";
            String ln = "";
            input = input.substring(75);

            while(input.charAt(0) != '<')
            {
                fn = fn + Character.toString(input.charAt(0));
                input = input.substring(1);
            }

            input = input.substring(16);

            while(input.charAt(0) != '<')
            {
                nid = nid + Character.toString(input.charAt(0));
                input = input.substring(1);
            }

            input = input.substring(15);

            while(input.charAt(0) != '<')
            {
                ln = ln + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            Student st = new Student(Integer.parseInt(nid), fn, ln);
            reg.addStudent(st);
            message = "Added a new student with ID : " + Integer.parseInt(nid);
        }catch (Exception e) {
            message = "Failed to add a new student with ID : " + id;
        }
       return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


