package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.SexDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Sex;
import cybersoft.java20.dev3lopers.gear3sproject.repository.SexRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.SexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SexServiceImp implements SexService {
    @Autowired
    SexRepository sexRepository;

    @Override
    public List<SexDTO> readAllSex() {
        List<SexDTO> sexDTOList = new ArrayList<>();
        List<Sex> sexList = sexRepository.findAll();

        for (Sex sex : sexList) {
            SexDTO sexDTO = new SexDTO();
            sexDTO.setId(sex.getId());
            sexDTO.setName(sex.getName());

            sexDTOList.add(sexDTO);
        }

        return sexDTOList;
    }
}
