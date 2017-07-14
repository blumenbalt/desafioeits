package projeto.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UsuarioFile
{
	/*-------------------------------------------------------------------
	 *                          ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Autowired
	private HttpServletRequest request;
	
	/*-------------------------------------------------------------------
	 *                          BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @param baseFolder
	 * @param file
	 * @return
	 */
	public String write(String baseFolder, MultipartFile file)
	{
		try 
		{
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			return path;
			
		} 
		catch (IllegalStateException | IOException e) 
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param response
	 * @param id
	 * @param path
	 * @throws IOException
	 */
	public void read(HttpServletResponse response, Long id, String path) throws IOException
	{
		String filePath = "/home/eits02/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/projeto/usuario-files/" + path;

		File file = new File(filePath);
		if (!file.exists())
	    {
	        throw new FileNotFoundException("O equipamento não possui manual!");
	    }
		
	    InputStream in = new FileInputStream(file);
	    
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    FileCopyUtils.copy(in, response.getOutputStream());
		
	}
}