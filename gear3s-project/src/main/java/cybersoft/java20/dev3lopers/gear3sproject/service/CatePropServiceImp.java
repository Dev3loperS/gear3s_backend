package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdDescDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.CategoryProperty;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductDesc;
import cybersoft.java20.dev3lopers.gear3sproject.repository.CatePropRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.CatePropService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatePropServiceImp implements CatePropService {
    @Autowired
    CatePropRepository catePropRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public List<CatePropDTO> readProdFilterListByCateId(int categoryId) {
        List<CatePropDTO> catePropDtoList = new ArrayList<>();
        try {
            List<CategoryProperty> catePropList = catePropRepository.findAllByCategoryId(categoryId);
            for (CategoryProperty cateProp:catePropList) {
                CatePropDTO catePropDTO = new CatePropDTO();

                catePropDTO.setId(cateProp.getId());
                catePropDTO.setName(cateProp.getName());
                List<ProductDesc> prodDescList = new ArrayList<>(cateProp.getListProdDesc());

                List<ProdDescDTO> proDescDtoList = new ArrayList<>();
                for (ProductDesc prodDesc: prodDescList) {
                    ProdDescDTO prodDescDTO = new ProdDescDTO();
                    prodDescDTO.setId(prodDesc.getId());
                    prodDescDTO.setDesc(prodDesc.getDescription());
                    proDescDtoList.add(prodDescDTO);
                }
                catePropDTO.setProdDescDtoList(proDescDtoList);

                catePropDtoList.add(catePropDTO);
            }
            LOGGER.info("Read product filter list by property by category with Id '{}' successfully",categoryId);
            return catePropDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read product filter list by property by category with Id '{}' : {}",categoryId,e.getMessage());
            return null;
        }
    }
}
