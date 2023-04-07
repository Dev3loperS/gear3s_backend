package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStorageService {
    boolean saveFile (MultipartFile file ) ;
    Resource load (String file );
}
