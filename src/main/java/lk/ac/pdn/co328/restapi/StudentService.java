/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

@Path("rest")
public class StudentService
{
    private StudentRegister stReg;

    public StudentService() {
        stReg = new StudentRegister();
    }

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st;
        if (stReg.findStudent(id) != null) {
            st = stReg.findStudent(id);

            return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
        } else {
            String message = "{message:'Student is not in the database'}";

            return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        }


    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input) throws Exception {
        String message;

        if (stReg.findStudent(id) != null && stReg.findStudent(id).getId() > 0) {
            stReg.removeStudent(id);

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(input));

            Document doc = builder.parse(src);
            String fNAme = doc.getElementsByTagName("firstName").item(0).getTextContent();
            String lName = doc.getElementsByTagName("lastName").item(0).getTextContent();

            Student st = new Student(id, fNAme, lName);
            stReg.addStudent(st);

            message = "{message:'Student updated'}";  // Ideally this should be machine readable format Json or XML
        } else {
            message = "{message:'Student is not in the database'}";
        }

        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        stReg.removeStudent(id);

        String message = "{message:'Student deleted'}";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input) throws Exception {
        String message;

        if (stReg.findStudent(id) != null && stReg.findStudent(id).getId() > 0) {
            message = "{message:'Student is already in the system'}";

        } else {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(input));

            Document doc = builder.parse(src);
            String fNAme = doc.getElementsByTagName("firstName").item(0).getTextContent();
            String lName = doc.getElementsByTagName("lastName").item(0).getTextContent();

            Student st = new Student(id, fNAme, lName);
            stReg.addStudent(st);

            message = "{message:'Student added'}";  // Ideally this should be machine readable format Json or XML
        }

        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


