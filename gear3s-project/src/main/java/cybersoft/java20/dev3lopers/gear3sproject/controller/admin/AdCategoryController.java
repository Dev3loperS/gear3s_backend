//package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;
//
//import cybersoft.java20.dev3lopers.gear3sproject.dto.CategoryDTO;
//import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
//import cybersoft.java20.dev3lopers.gear3sproject.service.imp.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/category")
//public class AdCategoryController {
//    @Autowired
//    CategoryService categoryService;
//
//    @GetMapping("")
//    public ResponseEntity<?> getAllCategory ()
//    {
//        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
//    }
//
////    @PostMapping("/add")
////    public ResponseEntity <?>insertCategory (@RequestParam String name )
////    {
////        BasicResponse basicResponse = new BasicResponse();
////
////        basicResponse.setData(categoryService.insertCategory(name));
////        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
////    }
//
//    @PostMapping("/findById")
//    public ResponseEntity <?>findCategoryById (@RequestParam int id  )
//    {
//        BasicResponse basicResponse = new BasicResponse();
//        CategoryDTO categoryDTO  =categoryService.findById(id);
//        if (categoryDTO !=null)
//        {
//            basicResponse.setData(categoryDTO);
//            basicResponse.setMessage("true");
//            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
//        }else
//        {
//            basicResponse.setData(categoryDTO);
//            basicResponse.setMessage("false");
//            return new ResponseEntity<>(basicResponse,HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("/findByName")
//    public ResponseEntity <?>findCategoryByName(@RequestParam String name   )
//    {
//        BasicResponse basicResponse = new BasicResponse();
//        CategoryDTO categoryDTO  = categoryService.findByName(name);
//
//        if (categoryDTO !=null)
//        {
//            basicResponse.setData(categoryDTO);
//            basicResponse.setMessage("true");
//            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
//        }else
//        {
//            basicResponse.setData(categoryDTO);
//            basicResponse.setMessage("false");
//            return new ResponseEntity<>(basicResponse,HttpStatus.NOT_FOUND);
//        }
//
//
//    }
//
//    @PostMapping("/update/{id}")
//    public ResponseEntity <?>updateCategory (@PathVariable int id , @RequestBody CategoryDTO category )
//    {
//        BasicResponse basicResponse = new BasicResponse();
//        boolean isSuccess = categoryService.updateCategory(id,category);
//        if (isSuccess)
//        {
//            basicResponse.setData(true );
//            basicResponse.setMessage("update succesfully");
//            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
//        }else
//        {
//            basicResponse.setMessage("id not existed , insert successfully");
//            basicResponse.setData(true );
//            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
//        }
//
//
//    }
//
//    @PostMapping("/deleteById")
//    public ResponseEntity <?>deleteCategory (@RequestParam int id )
//    {
//        BasicResponse basicResponse = new BasicResponse();
//
//        basicResponse.setData(categoryService.deleteById(id));
//
//        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
//    }
//}
