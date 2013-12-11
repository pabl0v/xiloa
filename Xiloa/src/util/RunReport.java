package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class RunReport
 */
@WebServlet("/viewFile")
public class RunReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RunReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName = request.getParameter("file");    	
    	String formato  = request.getParameter("formato");
    	File   f 	    = (File)request.getSession().getAttribute("file");
    	response.setContentType(formato);
    	//uncomment the following line if you want to have a save window to be popuped up    	
    	response.setHeader("Content-Disposition", "attachment;filename=" + fileName);  	
    	int read = 0;
    	byte[] bytes = new byte[1024];
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        fileInputStream = new FileInputStream(f);
        outputStream = response.getOutputStream();

        while((read = fileInputStream.read(bytes)) != -1){
        	outputStream.write(bytes,0,read);
        }
        request.getSession().setAttribute("file",null);
        outputStream.flush();
        outputStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
