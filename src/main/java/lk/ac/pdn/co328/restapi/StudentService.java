/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService{ 
 private StudentRegister register = null;
  public StudentService(){
   register = new StudentRegister() ;
  }
    @GET
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = register.findStudent(id);
        if (st==null) return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(st).build();
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        /*
        Body of expected POST request should be of following format.
        <?xml version="1.0" encoding="UTF-8"?>
        <student><firstName>miley</firstName>
        <id>11</id>
        <lastName>cyrus</lastName>
        </student>
        */
        String message = null;
        String [] data;
        String [] data2;
        try {
            String sid = "";
            String fname = "";
            String lname = "";

            data = input.split("<");

            data2 = data[3].split(">");
            fname = data2[1];

            data2 = data[5].split(">");
            sid = data2[1];

            data2 = data[7].split(">");
            lname = data2[1];

            register.removeStudent(Integer.parseInt(sid));
            Student news = new Student(Integer.parseInt(sid), fname, lname);
            register.addStudent(news);
            message = "Details od student with id " + sid + " was successfully modified. ";
        }catch(Exception e){
            message = "Modification failed.";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = null;
        try{
            register.removeStudent(id);
            message = "Student with id "+id+" was successfully deleted.";
            return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        } catch (Exception ex){
            message = "Failed to delete student with id "+id+".";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }
    }
    
    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        /*
        Body of expected PUT request should be of following format.
        <?xml version="1.0" encoding="UTF-8"?>
        <student><firstName>miley</firstName>
        <id>11</id>
        <lastName>cyrus</lastName>
        </student>
        */
        String message = null;
        String [] data;
        String [] data2;
        try {
            String sid = "";
            String fname = "";
            String lname = "";

            data = input.split("<");

            data2 = data[3].split(">");
            fname = data2[1];

            data2 = data[5].split(">");
            sid = data2[1];

            data2 = data[7].split(">");
            lname = data2[1];

            Student news = new Student(Integer.parseInt(sid),fname,lname);
            register.addStudent(news);
            message = "Student with id "+sid+" was successfully added. ";
        }catch(Exception e){
            message = "Failed to add the student.";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


