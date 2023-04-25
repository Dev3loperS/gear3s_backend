package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdPropCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdPropDTO;

import java.util.List;

public interface ProdPropService {
    boolean createProdProp(ProdPropCreateDTO prodPropCreateDTO);
    List<ProdPropDTO> readAllProdProp();
    ProdPropDTO readProdPropById(int prodPropId);
    boolean updateProdProp(ProdPropCreateDTO prodPropCreateDTO);
    boolean deleteProdPropById(int prodPropId);
}
