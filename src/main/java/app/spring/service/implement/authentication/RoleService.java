package app.spring.service.implement.authentication;

import app.spring.entity.authentication.Role;
import app.spring.payload.authentication.RoleDto;
import app.spring.repository.authentication.RoleRepository;
import app.spring.response.PaginationResponse;
import app.spring.service.interfaces.authentication.IRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(RoleRepository roleRepository,
                       ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaginationResponse getAllRoles(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Role> pages = roleRepository.findAll(pageable);
        List<Role> lists = pages.getContent();
        List<RoleDto> content = lists.stream().map(page -> mapToDTO(page)).collect(Collectors.toList());

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(content);
        paginationResponse.setPageNo(pages.getNumber());
        paginationResponse.setPageSize(pages.getSize());
        paginationResponse.setTotalElements(pages.getTotalElements());
        paginationResponse.setTotalPages(pages.getTotalPages());
        paginationResponse.setLast(pages.isLast());
        return paginationResponse;
    }

    private RoleDto mapToDTO(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    private Role mapToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

}
