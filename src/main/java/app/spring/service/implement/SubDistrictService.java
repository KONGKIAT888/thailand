package app.spring.service.implement;

import app.spring.entity.District;
import app.spring.entity.SubDistrict;
import app.spring.exception.APIException;
import app.spring.exception.ResourceNotFoundException;
import app.spring.repository.SubDistrictRepository;
import app.spring.request.SubDistrictRequest;
import app.spring.response.DistrictResponse;
import app.spring.response.PaginationResponse;
import app.spring.response.SubDistrictResponse;
import app.spring.service.interfaces.ISubDistrictService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubDistrictService implements ISubDistrictService {

    private final SubDistrictRepository subDistrictRepository;
    private final ModelMapper modelMapper;

    public SubDistrictService(SubDistrictRepository subDistrictRepository,
                              ModelMapper modelMapper) {
        this.subDistrictRepository = subDistrictRepository;
        this.modelMapper = modelMapper;
    }

    private SubDistrictRequest mapToRequest(SubDistrict subDistrict) {
        return modelMapper.map(subDistrict, SubDistrictRequest.class);
    }

    private SubDistrictResponse mapToResponse(SubDistrict subDistrict) {
        return modelMapper.map(subDistrict, SubDistrictResponse.class);
    }

    private SubDistrict mapToEntity(SubDistrictRequest subDistrictRequest) {
        return modelMapper.map(subDistrictRequest, SubDistrict.class);
    }

    @Override
    public SubDistrictRequest create(SubDistrictRequest subDistrictRequest) {
        SubDistrict subDistrict = mapToEntity(subDistrictRequest);
        SubDistrict newData = subDistrictRepository.save(subDistrict);
        return mapToRequest(newData);
    }

    @Override
    public SubDistrictRequest update(SubDistrictRequest subDistrictRequest, long id) {
        SubDistrict subDistrict = subDistrictRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        subDistrict.setName(subDistrictRequest.getName());
        subDistrict.setNameEnglish(subDistrictRequest.getNameEnglish());
        SubDistrict updateData = subDistrictRepository.save(subDistrict);
        return mapToRequest(updateData);
    }

    @Override
    public void deleteById(long id) {
        SubDistrict subDistrict = subDistrictRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sub District", "id", id));
        subDistrictRepository.delete(subDistrict);
    }

    @Override
    public PaginationResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Boolean all) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(pageNo, pageSize, sort);

        Page<SubDistrict> pages = subDistrictRepository.findAll(pageable);
        List<SubDistrict> lists = pages.getContent();

        List<SubDistrictResponse> content = lists.stream()
                .filter(province -> keyword == null ||
                        province.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getNameEnglish().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(content);
        paginationResponse.setPageNo(pages.getNumber());
        paginationResponse.setPageSize(all ? content.size() : pages.getSize());
        paginationResponse.setTotalElements(keyword == null ? pages.getTotalElements() : content.size());
        paginationResponse.setTotalPages(pages.getTotalPages());
        paginationResponse.setLast(pages.isLast());
        return paginationResponse;
    }

    @Override
    public SubDistrictResponse getByNameOrNameEnglish(String name, String nameEnglish) {
        SubDistrict subDistrict = (SubDistrict) subDistrictRepository.findByNameIgnoreCaseOrNameEnglishIgnoreCase(name, nameEnglish)
                .orElseThrow(() -> new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "District Not Found!"));
        return mapToResponse(subDistrict);
    }
}
