/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.campusnav.junaid;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.campusnav.junaid.Dao;
import com.json.generators.JSONGenerator;
import com.json.generators.JsonGeneratorFactory;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;
import com.mysql.jdbc.BufferRow;
import java.io.*;
import java.nio.Buffer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.salman.map.*;
import tests.FileToResourceReader;
//import sun.org.mozilla.javascript.internal.json.JsonParser;
//import sun.org.mozilla.javascript.internal.json.JsonParser;
/**
 *
 * @author Junaid
 */
@WebServlet(name = "Server", urlPatterns = {"/Server"})
public class Server extends HttpServlet {
    
    PrintWriter out; 
    MapController mapController;
    
    
    void getRouteCoordinates(String src, String dest , ServletContext context){
  
        ArrayList<String> route =  mapController.getDirections(src, dest);

        out.print( route.get(0) + route.get(1) + route.get(2));
    }
    
    void getCurrentPosition( String wifiData) throws IOException{
        
        
        
//        InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/resources/"+"file_discrete.csv");
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
//        
//       out.print( wifiData);
//       
        double coordinate[] = mapController.getCurrentLocation(wifiData);
        if (coordinate != null && coordinate[0]!= 0 && coordinate[1]!= 0){
            out.print( coordinate[0]+","+coordinate[1] );
        }
        else {
            out.print("null");
        }
    }
        

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //response.setContentType("text/html;charset=UTF-8");
        
        response.setContentType("text");
        out = response.getWriter();
        mapController = new MapController(this.getServletContext());

        
        if(request.getParameter("action").toString().equals("getRoute")){
            getRouteCoordinates( request.getParameter("src"),request.getParameter("dest") ,this.getServletContext() );             
        }else if( request.getParameter("action").toString().equals("getCurrentPosition") ){
            getCurrentPosition( request.getParameter("wifiData"));
        }


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}




                 
//        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Server</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet Server at " + request.getContextPath() + "</h1>");
//            
//            Connection con = new Dao().openConnection();
//            
//            /*
//             * 
//             */
//            
//                Map data=new HashMap();
//                
//                //Properties prop=new Properties();
//                
////                prop.setProperty("1", "1");
////                prop.setProperty("2", "2");
////                prop.setProperty("3", "3");
////                
//                data.put("name", "Rajesh Putta");
//                data.put("age", 28);
//                data.put("city", new StringBuilder("hyderabad"));
//                data.put("array", new int[]{234,555,777,888});
//                data.put("chararray", new String[]{"c","b","a"});
//                data.put("doublearray", new double[]{234,555,777,888});
//                data.put("floatarray", new byte[]{1,2,34,35});
//                
//                JsonGeneratorFactory factory=JsonGeneratorFactory.getInstance();
//                JSONGenerator generator=factory.newJsonGenerator();
//                String json=generator.generateJson(data);
//            
//            
//            
//            
//            
//            
//            
//            
//            
//            
//            
//            //
//            
//            if (con != null){
//                
//                
//                
//                JsonParserFactory factoryParse=JsonParserFactory.getInstance();
//                JSONParser parser=factoryParse.newJsonParser();
//
//                //initialize parser with the json validation configuration file stream
//                //parser.initialize(xml_stream));
//
//                //configure parser for validating
//                //parser.setValidating(true);
//
//                //parse input json string with validating parser instance
//                Map jsonData=parser.parseJson(json);
//
//                List al=(List) jsonData.get("root");
//                String fName[]=(String[]) ((Map)al.get(0)).get("chararray");
//                //String lName=((Map)al.get(0)).get("lastName");
//                
//                out.println("<p>Servlet Server at " + fName + "</p>");
//                
//                
//
//            
//            }
//            else{
//                out.println("<h1>Servlet Server at " + "con failed"+ "</h1>");
//            
//            }
//            
//            out.println("</body>");
//            out.println("</html>");
//        } finally {            
//            out.close();
//        }