package cybersoft.java20.dev3lopers.gear3sproject.service.imp;


import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropDTO;

import java.util.List;

public interface CatePropService {
    List<CatePropDTO> readProdFilterListByCateId(int categoryId);
}
