package cybersoft.java20.dev3lopers.gear3sproject.controller;

import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
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
    FileStorageService fileStorageService;

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
    @PostMapping("/product/add")
    public ResponseEntity<?> addProductImage(@RequestParam int categoryId, @RequestParam int productId,
                                             @RequestParam("files") MultipartFile[] prodImages){
        if(fileStorageService.uploadProdImage(categoryId,productId,prodImages)){
            return new ResponseEntity<>(
                    new BasicResponse("Add product image successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add product image", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/list/{categoryId}/{productId}")
    public ResponseEntity<?> getAllProductImage(@PathVariable int categoryId, @PathVariable int productId){
        List<String> imageList = fileStorageService.readAllProdImage(categoryId,productId);
        if (imageList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product image list successful", imageList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product image list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestParam String filePath){
        if (fileStorageService.deleteFile(filePath)) {
            return new ResponseEntity<>(new BasicResponse("Deleted file successful",true),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Failed to delete file", false),HttpStatus.BAD_REQUEST);
        }
    }


}
