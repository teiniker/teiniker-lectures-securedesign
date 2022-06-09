package org.se.lab;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
	private Map<String, String> fileNameTable = new ConcurrentHashMap<>(); // Concurrent Map

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 1024 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init()
	{
		// Get the file location where it would be stored.
		filePath = getServletContext().getInitParameter("file-upload"); // see web.xml
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
			generateNoFileUpload(out, "");
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(filePath));

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
					String fileName = item.getName();

					// Verify File Type
					if(isValidFileType(fileName))
					{
						// Filename -> UUID
						String storageName = UUID.randomUUID().toString();
						fileNameTable.put(fileName, storageName);
						out.println("Uploaded File: " + fileName + " stored as: " + storageName + "<br>");
						LOG.info(fileNameTable);

						// Write the file
						file = new File(filePath, storageName);
						item.write(file);
					}
					else
					{
						out.println("Invalid file type! <br>");
					}
				}
			}

			out.println("</body>");
			out.println("</html>");
		} 
		catch (Exception ex)
		{
			LOG.error("Can't handle request!", ex);
			generateNoFileUpload(out, ex.getMessage());
		}
		finally
		{
			out.close();
		}
	}

	private void generateNoFileUpload(java.io.PrintWriter out, String message)
	{
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet upload</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>No file uploaded: " + message +  " </p>");
		out.println("</body>");
		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException
	{
		throw new ServletException("GET method used with "
				+ getClass().getName() + ": POST method required.");
	}


	private boolean isValidFileType(String filename)
	{
		if(filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".gif"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}