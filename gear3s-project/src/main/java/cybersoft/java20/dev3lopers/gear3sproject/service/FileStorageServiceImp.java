package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.service.imp.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImp implements FileStorageService {
    @Value("${uploads.path}")
    private String path;

    private Path root ;

    public void init ()
    {
        try {
            root = Paths.get(path);
            if (!Files.exists(root))
            {
                Files.createDirectories(root);
            }

        } catch (IOException e) {
            System.out.println("Error in creating new directory : "+e.getMessage());
        }
    }

    @Override
    public boolean saveFile(MultipartFile file) {
        init();
        try
        {
            Files.copy(file.getInputStream(),root.resolve(file.getOriginalFilename())
                                                , StandardCopyOption.REPLACE_EXISTING);
            return true ;
        }catch (Exception e )
        {
            System.out.println("Error in saving file : "+e.getMessage());
            return false;
        }

    }

    @Override
    public Resource load(String filename) {
        init();
        System.out.println("File name is : "+filename);
        try {
            System.out.println(root);
            Path file = root.resolve(filename);
            System.out.println(file );
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }



}
