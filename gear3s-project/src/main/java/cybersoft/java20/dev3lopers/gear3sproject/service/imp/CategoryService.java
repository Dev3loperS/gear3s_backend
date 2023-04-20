package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategory ();

    boolean insertCategory (String name ) ;
    CategoryDTO findById(int id ) ;
    List<CategoryDTO> findByName(String name  ) ;
    boolean updateCategory (int id ,String name );
    boolean deleteById(int id ) ;

}
