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

    StudentRegister registry=null;

    public StudentService(){


        registry=new StudentRegister();
    }


    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = registry  .findStudent(id);
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        String msg=null;
        registry.removeStudent(id);

        String new_id="";
        String First_name="";
        String Last_name="";
        input=input.substring(75);

        try{

            while(input.charAt(0) != '<'){

                First_name=First_name + Character.toString(input.charAt(0));
                input=input.substring(1);
            }
            input=input.substring(16);
            while(input.charAt(0) != '<'){

                new_id=new_id + Character.toString(input.charAt(0));
                input=input.substring(1);
            }
            input=input.substring(15);
            while(input.charAt(0) != '<'){

                Last_name=Last_name+Character.toString(input.charAt(0));
                input=input.substring(1);
            }

            Student studnt=new Student(Integer.parseInt(new_id),First_name,Last_name);
            registry.addStudent(studnt);
            msg="The student ID : "+ id + "Edited";




        } catch (Exception e) {
            e.printStackTrace();
            msg="The student ID : "+ id + "Failed to Edit";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(msg).build();
    }
    
    @DELETE
    @Path("student/{id}")
    @Produces("text/html")
    public Response deleteStudent(@PathParam("id") int id)
    {

        registry.removeStudent(id);
        String message ="The student ID : "+ id + "deleted";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {

        String msg=null;
        String new_id="";
        String First_name="";
        String Last_name="";
        input=input.substring(75);

        try{

            while(input.charAt(0) != '<'){

                First_name=First_name + Character.toString(input.charAt(0));
                input=input.substring(1);
            }
            input=input.substring(16);
            while(input.charAt(0) != '<'){

                new_id=new_id + Character.toString(input.charAt(0));
                input=input.substring(1);
            }
            input=input.substring(15);
            while(input.charAt(0) != '<'){

                Last_name=Last_name+Character.toString(input.charAt(0));
                input=input.substring(1);
            }

            Student studnt=new Student(Integer.parseInt(new_id),First_name,Last_name);
            registry.addStudent(studnt);
            msg="The student ID : "+ id + "Added";




        } catch (Exception e) {
            e.printStackTrace();
            msg="The student ID : "+ id + "Failed to Add";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(msg).build();
    }
}


