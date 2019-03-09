package application.rest;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response; 

import java.util.List; 
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
  
import javax.ws.rs.core.UriInfo;

@Path("/")
public class RootEndpoint {
    static String kv(String k, String v) { 
       return "\""
        + k
        + "\":\"" 
        + v 
        + "\"";
    }
    
    @GET
    @Path("ui")
    @Produces({ MediaType.TEXT_HTML })
    public InputStream getFrontPage() {
        try {
            return this.getClass().getResourceAsStream("/index.html");
        } catch (Exception e) {
            throw new RuntimeException("Exception returning index.html", e);
        }
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiGet() {
        long t = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        sb.append ("{");
        sb.append (kv("time", Long.toString(t)));
        sb.append (",");
        sb.append (kv("path", "/get"));
        sb.append (",");
        sb.append (kv("version", "1.0"));
        sb.append ("}");
        return Response.ok(sb.toString()).build();
    }

    @GET
    @Path("headers")
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

    @GET
    @Produces({ MediaType.TEXT_HTML })
    public InputStream getIndex() {
        try {
            return this.getClass().getResourceAsStream("/root.html");
        } catch (Exception e) {
            throw new RuntimeException("Exception returning index.html", e);
        }
    }
}
