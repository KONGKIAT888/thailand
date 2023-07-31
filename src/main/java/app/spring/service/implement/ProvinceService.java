package app.spring.service.implement;

import app.spring.entity.Province;
import app.spring.exception.APIException;
import app.spring.exception.ResourceNotFoundException;
import app.spring.repository.ProvinceRepository;
import app.spring.request.ProvinceRequest;
import app.spring.response.PaginationResponse;
import app.spring.response.ProvinceResponse;
import app.spring.service.interfaces.IProvinceService;
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
public class ProvinceService implements IProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ModelMapper modelMapper;

    public ProvinceService(ProvinceRepository provinceRepository, ModelMapper modelMapper) {
        this.provinceRepository = provinceRepository;
        this.modelMapper = modelMapper;
    }

    private ProvinceRequest mapToRequest(Province province) {
        return modelMapper.map(province, ProvinceRequest.class);
    }

    private ProvinceResponse mapToResponse(Province province) {
        return modelMapper.map(province, ProvinceResponse.class);
    }

    private Province mapToEntity(ProvinceRequest provinceRequest) {
        return modelMapper.map(provinceRequest, Province.class);
    }

    @Override
    public ProvinceRequest create(ProvinceRequest provinceRequest) {
        Province province = mapToEntity(provinceRequest);
        Province newData = provinceRepository.save(province);
        return mapToRequest(newData);
    }

    @Override
    public ProvinceRequest update(ProvinceRequest provinceRequest, long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));

        province.setName(provinceRequest.getName());
        province.setNameEnglish(provinceRequest.getNameEnglish());
        province.setCode(provinceRequest.getCode());
        province.setCodeEnglish(provinceRequest.getCodeEnglish());
        province.setRegion(provinceRequest.getRegion());
        province.setRegionEnglish(provinceRequest.getRegionEnglish());
        Province updateData = provinceRepository.save(province);
        return mapToRequest(updateData);
    }

    @Override
    public void deleteById(long id) {
        Province province = provinceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        provinceRepository.delete(province);
    }

    @Override
    public PaginationResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Boolean all) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(pageNo, pageSize, sort);

        Page<Province> pages = provinceRepository.findAll(pageable);
        List<Province> lists = pages.getContent();

        List<ProvinceResponse> content = lists.stream()
                .filter(province -> keyword == null ||
                        province.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getNameEnglish().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getCode().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getCodeEnglish().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getRegion().toLowerCase().contains(keyword.toLowerCase()) ||
                        province.getRegionEnglish().toLowerCase().contains(keyword.toLowerCase()))
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
    public ProvinceResponse getByNameOrNameEnglish(String name, String nameEnglish) {
        Province province = (Province) provinceRepository.findByNameIgnoreCaseOrNameEnglishIgnoreCase(name, nameEnglish)
                .orElseThrow(() -> new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "Province Not Found!"));
        return mapToResponse(province);
    }

}
