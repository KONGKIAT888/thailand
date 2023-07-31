package app.spring.service.implement;

import app.spring.entity.District;
import app.spring.entity.Province;
import app.spring.exception.APIException;
import app.spring.exception.ResourceNotFoundException;
import app.spring.repository.DistrictRepository;
import app.spring.request.DistrictRequest;
import app.spring.response.DistrictResponse;
import app.spring.response.PaginationResponse;
import app.spring.service.interfaces.IDistrictService;
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
public class DistrictService implements IDistrictService {

    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    public DistrictService(DistrictRepository districtRepository,
                           ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
    }

    private DistrictRequest mapToRequest(District district) {
        return modelMapper.map(district, DistrictRequest.class);
    }

    private DistrictResponse mapToResponse(District district) {
        return modelMapper.map(district, DistrictResponse.class);
    }

    private District mapToEntity(DistrictRequest districtRequest) {
        return modelMapper.map(districtRequest, District.class);
    }

    @Override
    public DistrictRequest create(DistrictRequest districtRequest) {
        District district = mapToEntity(districtRequest);
        District newData = districtRepository.save(district);
        return mapToRequest(newData);
    }

    @Override
    public DistrictRequest update(DistrictRequest districtRequest, long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));

        district.setName(districtRequest.getName());
        district.setNameEnglish(districtRequest.getNameEnglish());
        district.setZipcode(districtRequest.getZipcode());
        Province province = new Province();
        province.setId(districtRequest.getProvinceId());
        district.setProvince(province);
        District updateData = districtRepository.save(district);
        return mapToRequest(updateData);
    }

    @Override
    public void deleteById(long id) {
        District district = districtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("District", "id", id));
        districtRepository.delete(district);
    }

    @Override
    public PaginationResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Boolean all) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(pageNo, pageSize, sort);

        Page<District> pages = districtRepository.findAll(pageable);
        List<District> lists = pages.getContent();

        List<DistrictResponse> content = lists.stream()
                .filter(province -> keyword == null ||
                        province.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getNameEnglish().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getZipcode().toString().toLowerCase().contains(keyword.toLowerCase()))
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
    public DistrictResponse getByNameOrNameEnglish(String name, String nameEnglish) {
        District district = (District) districtRepository.findByNameIgnoreCaseOrNameEnglishIgnoreCase(name, nameEnglish)
                .orElseThrow(() -> new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "District Not Found!"));
        return mapToResponse(district);
    }

}
