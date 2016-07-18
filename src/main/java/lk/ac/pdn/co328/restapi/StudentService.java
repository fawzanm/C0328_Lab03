package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService {
    StudentRegister register_ = new StudentRegister();

    //localhost:8080/rest/student/1
    @GET
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st;
        try {
            st = register_.findStudent(id);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(HttpResponseCodes.SC_FOUND).entity(e.getMessage()).build();
        }

        if(st == null){
            return Response.status(HttpResponseCodes.SC_FOUND).entity("ID "+id+"  is not valid.").build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    //@Consumes("application/xml")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    //@Produces({ MediaType.APPLICATION_FORM_URLENCODED })
    @Produces({MediaType.APPLICATION_JSON})
    //public Response modifyStudent(@PathParam("id") int id,@MatrixParam("first") String firstname, @MatrixParam("last") String lastname){
    public Response modifyStudent(@PathParam("id") int id,String input){
        Student st = null;
        try {
            //st = new Student(id,firstname, lastname) ;
            register_.addStudent(st);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String message = "{message:'FIXME : Updated'}";  // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(st).build();
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    //localhost:8080/rest/student/1
    @DELETE
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") int id) {

        Student st;
        st = register_.findStudent(id);

        if(st == null){
            return Response.status(HttpResponseCodes.SC_FOUND).entity("ID "+id+" is not valid.").build();
        }else{
            try {
                register_.removeStudent(id);
                return Response.status(HttpResponseCodes.SC_OK).entity("ID  "+id+" is Deleted.").build();
            }catch (Exception e){
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_OK).entity(e.getMessage()).build();
            }
        }

    }


    //localhost:8080/rest/student/1;first=Amila;last=Indrajith
    @PUT
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
     public Response addStudent(@PathParam("id") int id,@MatrixParam("first") String firstname, @MatrixParam("last") String lastname){
        Student st = null;
        try {
            st = new Student(id,firstname,lastname) ;
            register_.addStudent(st);
            return Response.status(HttpResponseCodes.SC_OK).entity("Created Student,ID : " + id + ", First Name : " + firstname + ", Last Name : " + lastname).build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(HttpResponseCodes.SC_OK).entity(e.getMessage()).build();
        }
    }
}


