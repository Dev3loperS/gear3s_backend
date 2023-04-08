package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public interface FileStorageService {
    boolean saveFile (MultipartFile file, String folder) ;
    Resource load (String file );
    boolean deleteFile (String avatarPath);
}
