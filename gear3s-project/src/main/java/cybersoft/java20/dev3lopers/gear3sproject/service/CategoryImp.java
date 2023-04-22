package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.AdCategoryDTO;
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
    public List<AdCategoryDTO> getAllCategory() {
        List<AdCategoryDTO> list = new ArrayList<>();
        try {
            for (Category category : categoryRepository.getAllCategory()
            ) {
                AdCategoryDTO categoryDTO = new AdCategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                list.add(categoryDTO);
            }
        } catch (Exception e) {
            System.out.println("Error in categoryImp : " + e.getMessage());

        }

        return list;
    }

    @Override
    public boolean insertCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);

        return true ;
    }

    @Override
    public AdCategoryDTO findById(int id) {
        Category category = null;
        category = categoryRepository.findById(id);
        AdCategoryDTO categoryDTO = new AdCategoryDTO();
        if (category != null) {

            categoryDTO.setName(category.getName());
            categoryDTO.setId(category.getId());
            return categoryDTO;
        } else {
            return null;
        }


    }

    @Override
    public List<AdCategoryDTO > findByName(String name) {
        List<Category >categories= categoryRepository.findByNameLike(name);
        List<AdCategoryDTO >categoryDTOS= new ArrayList<>();

        if (categories != null) {
            for (Category category :categories
                 ) {
                AdCategoryDTO categoryDTO = new AdCategoryDTO() ;
                categoryDTO.setName(category.getName());
                categoryDTO.setId(category.getId());
                categoryDTOS.add(categoryDTO);
            }

            return categoryDTOS;
        } else {
            return null;
        }

    }

    @Override
    public boolean updateCategory(int id, String name ) {

        //boolean isSuccess = deleteById(id);
        Category result = new Category();
        Category oldCategory = null;
        oldCategory = categoryRepository.findById(id);
        if (oldCategory != null) {
            oldCategory.setName(name);
            categoryRepository.save(oldCategory);
            return true;

        } else {
            insertCategory(name);
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
