package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.CategoryDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Category;
import cybersoft.java20.dev3lopers.gear3sproject.repository.CategoryRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryImp implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> list = new ArrayList<>();
        for (Category category : categoryRepository.getAllCategory()
        ) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            list.add(categoryDTO);
        }
        return list;
    }

    @Override
    public boolean insertCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        if (category.getName().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public CategoryDTO findById(int id) {
        Category category =null;
        category= categoryRepository.findById(id);
        CategoryDTO categoryDTO = new CategoryDTO();
        if (category !=null){

            categoryDTO.setName(category.getName());
            categoryDTO.setId(category.getId());
            return categoryDTO;
        }else
        {
           return null ;
        }


    }

    @Override
    public CategoryDTO findByName(String name) {
        Category category = null;
        category= categoryRepository.findByName(name);
        CategoryDTO categoryDTO = new CategoryDTO();
        if (category != null)
        {
            categoryDTO.setName(category.getName());
            categoryDTO.setId(category.getId());
            return categoryDTO;
        }
      else
        {
            return null ;
        }

    }

    @Override
    public boolean updateCategory(int id, CategoryDTO newCategory) {

        //boolean isSuccess = deleteById(id);
        Category result = new Category();
        Category oldCategory = null ;
        oldCategory = categoryRepository.findById(id);
        if (oldCategory!=null )
        {
            oldCategory.setName(newCategory.getName());
            categoryRepository.save(oldCategory);
            return true;

        }else
        {
            result.setId(id);
            result.setName(newCategory.getName());
            categoryRepository.save(result);
            return false;
        }





    }

    @Override
    public boolean deleteById(int id) {
        Category category = null;
        category = categoryRepository.findById(id);
        if (category != null) {
            categoryRepository.delete(category);
            return true;
        } else {
            return false;
        }

    }
}
