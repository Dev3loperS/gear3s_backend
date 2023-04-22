package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public interface FileStorageService {
    //boolean saveFile (MultipartFile file, String folder) ;
    //Resource load (String file);

    boolean uploadAvatar(int userId, MultipartFile file);
    boolean uploadProdImage(int categoryId, int productId, MultipartFile[] file);
    List<String> readAllProdImage(int categoryId, int productId);
    boolean deleteFile(String filePath);
}
