package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.AdCategoryDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<AdCategoryDTO> getAllCategory ();

    boolean insertCategory (String name ) ;
    AdCategoryDTO findById(int id ) ;
    List<AdCategoryDTO> findByName(String name  ) ;
    boolean updateCategory (int id ,String name );
    boolean deleteById(int id ) ;

}
