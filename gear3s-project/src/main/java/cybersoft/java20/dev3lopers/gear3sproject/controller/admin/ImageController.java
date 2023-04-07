package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage (MultipartFile file )
    {
        BasicResponse basicResponse= new BasicResponse();
        basicResponse.setData(fileStorageService.saveFile(file));

        return new ResponseEntity<>(basicResponse, HttpStatus.OK) ;
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<?> download ( @PathVariable String fileName )
    {
        System.out.println("Entered download");
        Resource resource = fileStorageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
