package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.PropDescCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PropDescDTO;

import java.util.List;

public interface PropDescService {
    boolean createPropDesc(PropDescCreateDTO propDescCreateDTO);
    List<PropDescDTO> readAllPropDesc();
    PropDescDTO readPropDescById(int propDescId);
    boolean updatePropDesc(PropDescCreateDTO propDescCreateDTO);
    boolean deletePropDescById(int propDescId);
}
