package org.se.lab;

import java.io.File;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class UploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(UploadServlet.class);
		
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 200 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init()
	{
		// Get the file location where it would be stored.
//		filePath = getServletContext().getInitParameter("file-upload");
		filePath = getServletContext().getRealPath("/"); //!!! Don't do that
		LOG.info("Save files to: " + filePath);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException
	{
		LOG.info("POST request: " + request.getRequestURL());
		
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);

		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		if (!isMultipart)
		{
			generateNoFileUpload(out);
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("/home/student/tmp/"));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try
		{
			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);

			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");

			for (FileItem item : fileItems)
			{
				// form fields are ignored
				if (!item.isFormField())
				{
					// Get the uploaded file parameters
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String contentType = item.getContentType();
					boolean isInMemory = item.isInMemory();
					long sizeInBytes = item.getSize();

					out.println("Uploaded File:<br>");
					out.println("field name    = " + fieldName + "<br>");
					out.println("file name     = " + fileName + "<br>");
					out.println("content type  = " + contentType + "<br>");
					out.println("isInMemory    = " + isInMemory + "<br>");
					out.println("size in bytes = " + sizeInBytes + "<br>");

					// Write the file
					file = new File(filePath, fileName);  // !!! validate parameter fileName
					item.write(file);
					out.println("File saved to    : " + file.getAbsolutePath() + "<br>");
				} 
				else
				{
					out.println("Form field:<br>");
					String name = item.getFieldName();
					String value = item.getString();
					out.println("field name    = " + name + "<br>");
					out.println("field value   = " + value + "<br>");
				}
			}

			out.println("</body>");
			out.println("</html>");
		} 
		catch (Exception ex)
		{
			LOG.error("Can't handle request!", ex);
		}
	}

	private void generateNoFileUpload(java.io.PrintWriter out)
	{
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet upload</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>No file uploaded </p>");
		out.println("</body>");
		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException
	{

		throw new ServletException("GET method used with "
				+ getClass().getName() + ": POST method required.");
	}
}