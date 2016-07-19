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


    StudentRegister ls = new  StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces(MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    public Response viewStudent(@PathParam("id") int id) {
        Student st = ls.findStudent(id);

            return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
        }


    
    @POST
    // Use {firstname lastname} in post body
    @Path("student/{id}")
    //@Produces( MediaType.APPLICATION_JSON )
    public Response modifyStudent(@PathParam("id") int id,String input)
    {
        Student st = ls.findStudent(id);// Ideally this should be machine readable format Json or XML
        String[] s=input.split(" ");

        if(st==null){
            return Response.status(HttpResponseCodes.SC_OK).entity("Update fail, No account found.").build();
        }
        else {
            st.UpdateStudent(s[0],s[1]);
            return Response.status(HttpResponseCodes.SC_OK).entity("Update success.").build();
        }
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        Student st = ls.findStudent(id);// Ideally this should be machine readable format Json or XML

        if(st==null){
            return Response.status(HttpResponseCodes.SC_OK).entity("Delete account fail, No account found.").build();
        }
        else {
            ls.removeStudent(id);
            return Response.status(HttpResponseCodes.SC_OK).entity("Delete success.").build();
        }
    }
    
    @PUT
    // Use {firstname lastname} in post body
    @Path("student/{id}")
   // @Produces( MediaType.APPLICATION_JSON )
    public Response addStudent(@PathParam("id") int id, String input)
    {
        Student fs = ls.findStudent(id);
        if (fs==null) {
            String[] s = input.split(" ");
            Student st = new Student(id, s[0], s[1]);

            try {
                ls.addStudent(st);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Response.status(HttpResponseCodes.SC_FOUND).entity("Account is added successfully.").build();
        }
            else
             return Response.status(HttpResponseCodes.SC_FOUND).entity("Account is already exists, Use POST method to modify.").build();
        }
    }



