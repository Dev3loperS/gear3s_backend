package cybersoft.java20.dev3lopers.gear3sproject.service.imp;


import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.FilterCatePropDTO;

import java.util.List;

public interface CatePropService {
    boolean createCateProp(CatePropCreateDTO catePropCreateDTO);
    List<CatePropDTO> readAllCateProp();
    CatePropDTO readCatePropById(int catePropId);
    boolean updateCateProp(CatePropCreateDTO catePropCreateDTO);
    boolean deleteCatePropById(int catePropId);

    List<FilterCatePropDTO> readProdFilterListByCateId(int categoryId);
    List<CatePropCreateDTO> readAllCatePropByCateId(int categoryId);

}
