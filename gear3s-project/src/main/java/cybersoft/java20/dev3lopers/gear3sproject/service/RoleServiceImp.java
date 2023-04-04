package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.RoleDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.repository.RoleRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAllRole() {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        List<Roles> roleList = roleRepository.findAll();

        for (Roles role:roleList) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            roleDTO.setDesc(role.getDescription());

            roleDTOList.add(roleDTO);
        }

        return roleDTOList;
    }
}
