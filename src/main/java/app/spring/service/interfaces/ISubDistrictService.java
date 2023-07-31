package app.spring.service.interfaces;

import app.spring.request.SubDistrictRequest;
import app.spring.response.PaginationResponse;
import app.spring.response.SubDistrictResponse;

public interface ISubDistrictService {

    SubDistrictRequest create(SubDistrictRequest subDistrictRequest);

    SubDistrictRequest update(SubDistrictRequest subDistrictRequest, long id);

    void deleteById(long id);

    PaginationResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Boolean all);

    SubDistrictResponse getByNameOrNameEnglish(String name, String nameEnglish);

}
