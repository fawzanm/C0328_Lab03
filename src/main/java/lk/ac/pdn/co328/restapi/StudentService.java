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

    Student st_ = new Student(1,"thushani","nipunika");

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
       try{
            register.addStudent(st_);
        }catch (Exception e){

        }

        Student st = register.findStudent(id);
        if(st == null){
            return Response.status(HttpResponseCodes.SC_FOUND).entity("Error."+id+ "id is not valid.").build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }

    @POST
    @Path("student/{id}")
    @Consumes("application/xml")

    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        if(input == null) {
            try {
                register.addStudent(input);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Cant added the student.").build();
            }
        }
        else{
            register.removeStudent(id);
            try {
                register.addStudent(input);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Student is modified.").build();
            }
        }
        String message = "{message:'FIXME : Updated'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @DELETE
    @Path("student/{id}")

    public Response deleteStudent(@PathParam("id") int id) {
        if ((register.findStudent(id) != (null))) {
            try {
                register.removeStudent(id);
                String message = "{message:'FIXME : Deleted'}";  // Ideally this should be machine readable format Json or XML
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
            } catch (Exception e) {
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Student cant remove.").build();
            }
        }else {
            return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Invalid ID.").build();
        }
    }


    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, Student input) {
        if (input != (null)) {
            try {
                register.addStudent(input);
                String message = "{message:'FIXME : Added'}";  // Ideally this should be machine readable format Json or XML
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();

            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Cant added the student.").build();
            }
        }else{
            return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Student is not valid").build();
        }
    }
}


