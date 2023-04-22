package cybersoft.java20.dev3lopers.gear3sproject.controller;

import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.FileStorageServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

    /*@PostMapping("/upload")
    public ResponseEntity<?> uploadImage (MultipartFile file )
    {
        BasicResponse basicResponse= new BasicResponse();
        basicResponse.setData(fileStorageService.saveFile(file,""));

        return new ResponseEntity<>(basicResponse, HttpStatus.OK) ;
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<?> download ( @PathVariable String fileName )
    {
        System.out.println("Entered download");
        Resource resource = fileStorageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }*/



}
