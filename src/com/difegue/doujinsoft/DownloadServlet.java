package com.difegue.doujinsoft;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// obtains ServletContext
		ServletContext application = getServletConfig().getServletContext();	
		String dataDir = application.getInitParameter("dataDirectory");
		
		
		if (request.getParameterMap().containsKey("type") && request.getParameterMap().containsKey("id")) 
		{
			
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			
			String filePath = "";
			
			switch (type) {
				case "game":
					filePath = dataDir+"/mio/game/"+id+".mio";
					break;
				case "record":
					filePath = dataDir+"/mio/record/"+id+".mio";
					break;
				case "manga":
					filePath = dataDir+"/mio/manga/"+id+".mio";
					break;
				default:
					filePath = null;
					break;
			}
			
			if (filePath != null) {
				
				File downloadFile = new File(filePath);
				FileInputStream inStream = new FileInputStream(downloadFile);
				
				// gets MIME type of the file
				String mimeType = "application/octet-stream";
				// Set response
				response.setContentType(mimeType);
				response.setContentLength((int) downloadFile.length());
				
				// forces download
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
				response.setHeader(headerKey, headerValue);
				
				// obtains response's output stream
				OutputStream outStream = response.getOutputStream();
				
				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				
				while ((bytesRead = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				
				inStream.close();
				outStream.close();
				
			}
			
		}	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
