package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.model.CategoryModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.ImagesModel;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageServiceImp implements FileStorageService {
    @Value("${uploads.path}")
    private String path;

    //private Path root ;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    /*public void init (String folderPath)
    {
        try {
            root = Paths.get(path+folderPath);
            if (!Files.exists(root))
            {
                Files.createDirectories(root);
            }

        } catch (IOException e) {
            System.out.println("Error in creating new directory : "+e.getMessage());
        }
    }*/

    /*@Override
    public boolean saveFile(MultipartFile file,String folder) {
        init(folder);
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

    }*/

    /*@Override
    public Resource load(String filename) {
        init(ImagesModel.AVATAR.getValue());
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
    }*/

    @Override
    public boolean uploadAvatar(int userId, MultipartFile file) {
        try{
            Path avatarRoot = Paths.get(path+ImagesModel.AVATAR.getValue());
            if (!Files.exists(avatarRoot)){
                Files.createDirectories(avatarRoot);
            }
            Path avatarPath = Paths.get(path+ImagesModel.AVATAR.getValue()+userId+".png");
            if(!Files.exists(avatarPath)){
                Files.copy(file.getInputStream(),avatarPath.resolve("")
                        , StandardCopyOption.REPLACE_EXISTING);
            }
            LOGGER.info("Uploaded avatar for user with Id '{}' successfully",userId);
            return true;
        } catch (Exception e ){
            LOGGER.error("Failed to upload avatar for user with Id '{}' : {}",userId,e.getMessage());
            return false;
        }
    }

    @Override
    public boolean uploadProdImage(int categoryId, int productId, MultipartFile[] file) {
        try{
            String path = getImagePath(categoryId);
            if(!"".equals(path)){
                Path imageRoot = Paths.get(path);
                if (!Files.exists(imageRoot)){
                    Files.createDirectories(imageRoot);
                }
                for(int i=1,j=0;i<20;i++){
                    if(j>=file.length)  break;
                    Path imagePath = Paths.get(path+productId+"-"+i+".png");
                    if(!Files.exists(imagePath)){
                        Files.copy(file[j].getInputStream(),imagePath.resolve("")
                                , StandardCopyOption.REPLACE_EXISTING);
                        j++;
                    }
                }
                LOGGER.info("Uploaded image for product with Id '{}' successfully",productId);
                return true;
            }
            LOGGER.error("Failed to get product image path with categoryId '{}'",categoryId);
        } catch (Exception e ){
            LOGGER.error("Failed to upload image for product with Id '{}' : {}",productId,e.getMessage());
        }
        return false;
    }

    @Override
    public List<String> readAllProdImage(int categoryId, int productId) {
        List<String> imageList = new ArrayList<>();

        for (int i=1;i<20;i++){
            Path imagePath = Paths.get(getImagePath(categoryId)+productId+"-"+i+".png");
            if(Files.exists(imagePath)){
                imageList.add(imagePath.toString());
            }
        }
        return imageList;
    }

    @Override
    public boolean deleteFile(String filePath){
        try {
            Path path = Paths.get(filePath);
            if(!Files.exists(path)){
                LOGGER.error("This file '{}' does not exist",filePath);
                return false;
            }
            Files.deleteIfExists(Paths.get(filePath));
            LOGGER.info("Deleted this file '{}' successfully",filePath);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete this file '{}' : ",e.getMessage());
            return false;
        }
    }

    private String getImagePath (int categoryId){
        if (categoryId == CategoryModel.KEYBOARD.getValue()){
            return path+ImagesModel.KEYBOARD.getValue();
        } else if(categoryId == CategoryModel.MOUSE.getValue()){
            return path+ImagesModel.MOUSE.getValue();
        } else if(categoryId == CategoryModel.MONITOR.getValue()){
            return path+ImagesModel.MONITOR.getValue();
        } else if(categoryId == CategoryModel.HEADSET.getValue()){
            return path+ImagesModel.HEADSET.getValue();
        } else if(categoryId == CategoryModel.LAPTOP.getValue()){
            return path+ImagesModel.LAPTOP.getValue();
        } else if(categoryId == CategoryModel.SPEAKER.getValue()){
            return path+ImagesModel.SPEAKER.getValue();
        }  else {
            return "";
        }
    }

}
