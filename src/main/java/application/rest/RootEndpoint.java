package application.rest;

import java.io.InputStream;
import java.net.InetAddress;

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
    static int count=0;
    static String hostName = gethostname();
    
    static String gethostname() { 
        try  { 
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception ex)  { 
            return "Unknown Host";
        }
    }
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
        StringBuffer sb = new StringBuffer();
        sb.append ("{");
        sb.append (kv("time", Long.toString(System.currentTimeMillis())));
        sb.append (",");
        sb.append (kv("count", Integer.toString(count++)));
        sb.append (",");
        sb.append (kv("hostname", hostName));
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
        response += comma + kv("time", Long.toString(System.currentTimeMillis()));
        response += comma + kv("count", Integer.toString(count++));
        response += comma + kv("hostname", hostName);
        response += comma + kv("version", "1.0");
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
