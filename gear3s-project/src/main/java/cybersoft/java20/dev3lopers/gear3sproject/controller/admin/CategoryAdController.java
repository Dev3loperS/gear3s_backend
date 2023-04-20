package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.CategoryDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryAdController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getAllCategory ()
    {
        BasicResponse basicResponse = new BasicResponse( );
        basicResponse.setMessage("true");
        basicResponse.setData(categoryService.getAllCategory());
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity <?>insertCategory (@RequestParam String name )
    {
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setData(categoryService.insertCategory(name));
        basicResponse.setMessage("Insert successfully new category : "+name);
        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @PostMapping("/findById")
    public ResponseEntity <?>findCategoryById (@RequestParam int id  )
    {
        BasicResponse basicResponse = new BasicResponse();
        CategoryDTO categoryDTO  =categoryService.findById(id);
        if (categoryDTO !=null)
        {
            basicResponse.setData(categoryDTO);
            basicResponse.setMessage("true");
            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }else
        {
            basicResponse.setData(categoryDTO);
            basicResponse.setMessage("false");
            return new ResponseEntity<>(basicResponse,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/findByName")
    public ResponseEntity <?>findCategoryByName(@RequestParam String name   )
    {
        BasicResponse basicResponse = new BasicResponse();
        List<CategoryDTO> categoryDTO  = categoryService.findByName(name);
        if (categoryDTO !=null)
        {
            basicResponse.setData(categoryDTO);
            basicResponse.setMessage("true");
            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }else
        {
            basicResponse.setData(categoryDTO);
            basicResponse.setMessage("false");
            return new ResponseEntity<>(basicResponse,HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/update/{id}")
    public ResponseEntity <?>updateCategory (@PathVariable int id ,@RequestParam String name  )
    {
        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = categoryService.updateCategory(id,name);
        if (isSuccess)
        {
            basicResponse.setData(true );
            basicResponse.setMessage("update succesfully");
            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }else
        {
            basicResponse.setMessage("id not existed , insert new category successfully");
            basicResponse.setData(true );
            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }


    }

    @PostMapping("/deleteById")
    public ResponseEntity <?>deleteCategory (@RequestParam int id )
    {
        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = categoryService.deleteById(id);
        if (isSuccess)
        {
            basicResponse.setData(true );
            basicResponse.setMessage("Delete successfully id : "+id);
            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }else
        {
            basicResponse.setMessage("Delete unsuccessfully id : "+id);
            basicResponse.setData(false );
            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }

    }
}
