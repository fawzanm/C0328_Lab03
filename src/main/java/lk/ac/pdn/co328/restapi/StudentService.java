/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("rest")
public class StudentService
{
    private static StudentRegister register = new StudentRegister();

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st;
        st = register.findStudent(id);

        if(st == null){
            System.out.print("fwvefuwbeub\n");
            String message = id + " is not valid Reg No. Please enter valid number !!!";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }

        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces( MediaType.APPLICATION_XML )
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        String message;

        synchronized (register) {
            try {
                /**post update the state of the student
                 * so,first remove the student and add the updated student
                 * **/
                register.removeStudent(id);
                register.addStudent(input);
                message = "Updated details of student successfully !!!";
            } catch (Exception e) {
               message = e.getMessage();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
         return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message;

        synchronized (register){
            try{
                register.removeStudent(id);
                message = new String("Student has removed !! ");
            }catch (Exception e){
                message = e.getMessage();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Produces(  MediaType.APPLICATION_XML )
    @Consumes( MediaType.APPLICATION_XML)
    public Response addStudent(@PathParam("id") int id, Student input) throws Exception
    {
        String message;

        synchronized (register){
            try{
                register.addStudent(input);
                message = "Add student successfully !!!";
            }catch (Exception e){
                message = e.getMessage();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
         return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


