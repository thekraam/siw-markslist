package it.uniroma3.siwml.upload;

import java.nio.file.Path;
import java.nio.file.Paths;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration

public class MvcConfig implements WebMvcConfigurer {
 
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	if(!registry.hasMappingForPattern("/static/**")) {
    		registry.addResourceHandler("/static/**")
    				.addResourceLocations("/static/");
    	}
    	if(!registry.hasMappingForPattern("/immagini-inserzioni/**")) {
    		registry.addResourceHandler("/immagini-inserzioni/**")
    				.addResourceLocations("/immagini-inserzioni/");
    	}
        exposeDirectory("static", registry);
        
        exposeDirectory("immagini-inserzioni", registry);
    }
     
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
         
        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
         
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
}