package app.spring.service.implement.authentication;

import app.spring.entity.authentication.User;
import app.spring.payload.authentication.UserDto;
import app.spring.repository.authentication.UserRepository;
import app.spring.response.PaginationResponse;
import app.spring.service.interfaces.authentication.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaginationResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> pages = userRepository.findAll(pageable);
        List<User> lists = pages.getContent();
        List<UserDto> content = lists.stream().map(page -> mapToDTO(page)).collect(Collectors.toList());

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(content);
        paginationResponse.setPageNo(pages.getNumber());
        paginationResponse.setPageSize(pages.getSize());
        paginationResponse.setTotalElements(pages.getTotalElements());
        paginationResponse.setTotalPages(pages.getTotalPages());
        paginationResponse.setLast(pages.isLast());
        return paginationResponse;
    }

    private UserDto mapToDTO(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

}
