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
    public static StudentRegister register = new StudentRegister();
    @GET
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st;
        synchronized (register){
            st = register.findStudent(id);
        }
        if (st == null){
            String warning = "\"There is no such student\"";
            return Response.status(HttpResponseCodes.SC_FOUND).entity(warning).build();
        }

        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    @Consumes(MediaType.APPLICATION_XML)
    /*  input string should be application/xml type
     *  eg: "lakshitha madushan"
     */
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        Student st;
        String [] data = input.split(" ");
        if (data.length < 2)
            return Response.status(HttpResponseCodes.SC_OK).entity("\"wrong input\"").build();

        String message = "\"getting updated\"";

        synchronized (register){
            st = register.findStudent(id);

            try {
                if (st == null){
                    register.addStudent(new Student(id,data[0],data[1]));
                    message =  "\"New student is added\"";
                }
                else {
                    st.setFirstName(data[0]);
                    st.setLastName(data[1]);
                    message = "\"Student details updated\"";

                }
            }catch (Exception exception){
            }

        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        synchronized (register){
          register.removeStudent(id);
        }

        String message = "{message:'Successfully Deleted'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    /*  input string should be application/xml type
     *  eg: "lakshitha madushan"
     */
    public Response addStudent(@PathParam("id") int id, String input)
    {

        String [] data = input.split(" ") ;
        if (data.length < 2)
            return Response.status(HttpResponseCodes.SC_OK).entity("\"wrong input\"").build();

        Student st = new Student(id,data[0], data[1]);
        String message = "";

        synchronized (register){
            try {
                register.addStudent(st);
                message = "{message:'Successfully Added'}";
            } catch (Exception e) {
                message = "{message:'"+ e +"'}";
            }
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


