package application.rest.v1;

import java.io.InputStream;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
 
import javax.ws.rs.core.Context; 
import javax.ws.rs.core.UriInfo;


@Path("/mpistio")
public class MPIstio {

    static String kv(String k, String v) { 
       return "\""
        + k
        + "\":\"" 
        + v 
        + "\"";
    }

    // default path will be / under mpistio
    @GET 
    @Produces({ MediaType.TEXT_HTML })
    public InputStream getmpistio() {
        try {
            return this.getClass().getResourceAsStream("/root.html");
        } catch (Exception e) {
            throw new RuntimeException("Exception returning index.html", e);
        }
    }

    // mpistio/example 
    @GET
    @Path("example")
    @Produces(MediaType.TEXT_PLAIN)
    public Response example() {
        List<String> list = new ArrayList<>();
        //return a simple list of strings
        list.add("Sample Microservice");
        return Response.ok(list.toString()).build();
    }

    // mpistio/get 
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiGet() {
        long t = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        sb.append ("{");
        sb.append (kv("time", Long.toString(t)));
        sb.append (",");
        sb.append (kv("path", "/mpistio/get"));
        sb.append (",");
        sb.append (kv("version", "1.0"));
        sb.append ("}");
        return Response.ok(sb.toString()).build();
    }

    // mpistio/all 
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiAll(@Context HttpHeaders headers) { 
        String response = "{\n";
        String comma = "";
        for (String header : headers.getRequestHeaders().keySet()) {
            response += comma + header + " : " + headers.getRequestHeaders().getFirst(header); 
            comma = ",\n";
        }
        response += "\n}"; 
        return Response.ok(response).build();
    }
 


}
