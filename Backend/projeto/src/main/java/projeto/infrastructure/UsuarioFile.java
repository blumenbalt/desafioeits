package projeto.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

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
	
	
	/*-------------------------------------------------------------------
	 *                          BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param baseFolder
	 * @param file
	 * @return
	 */
	public String write(MultipartFile file, Long id)
	{
		try 
		{
			String filePath = "/home/eits02/Área de Trabalho/Desafio eits/usuario-files/";
			String path = filePath +  file.getOriginalFilename();
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
		String filePath = "/home/eits02/Área de Trabalho/Desafio eits/usuario-files/" + path;

		File file = new File(filePath);
		if (!file.exists())
	    {
	        throw new FileNotFoundException("o usuário não possui contrato");
	    }
		
	    InputStream in = new FileInputStream(file);
	    
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    FileCopyUtils.copy(in, response.getOutputStream());
		
	}
}