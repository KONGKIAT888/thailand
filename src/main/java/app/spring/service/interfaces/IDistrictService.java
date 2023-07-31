package app.spring.service.interfaces;

import app.spring.request.DistrictRequest;
import app.spring.response.DistrictResponse;
import app.spring.response.PaginationResponse;

public interface IDistrictService {

    DistrictRequest create(DistrictRequest districtRequest);

    DistrictRequest update(DistrictRequest districtRequest, long id);

    void deleteById(long id);

    PaginationResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Boolean all);

    DistrictResponse getByNameOrNameEnglish(String name, String nameEnglish);

}
